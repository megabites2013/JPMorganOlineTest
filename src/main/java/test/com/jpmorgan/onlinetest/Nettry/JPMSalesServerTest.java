package test.com.jpmorgan.onlinetest.Nettry;

import com.jpmorgan.onlinetest.Nettry.JPMSalesServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import static com.jpmorgan.onlinetest.NewConstant.*;

/** 
* JPMSalesServer Tester.
* 
* @author SebastianX
* @since <pre>02/17/2018</pre> 
* @version 1.0 
*/ 
public class JPMSalesServerTest extends TestCase {
public JPMSalesServerTest(String name) {
super(name); 
} 

public void setUp() throws Exception { 
super.setUp(); 
} 

public void tearDown() throws Exception { 
super.tearDown(); 
} 

/** 
* 
* Method: bind(int port) 
* 
*/ 
public void testBind() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: main(String[] args) 
* 
*/ 
public void testMain() throws Exception {

    new Thread(() -> {
        try {
            Method method = JPMSalesServer.class.getMethod("main",String[].class);
            method.setAccessible(true);
            method.invoke(null, (Object) new String[] {  });

        } catch(NoSuchMethodException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        } catch(InvocationTargetException e) {
            e.printStackTrace();
        }
    }).start();

    synchronized (this) {
        wait(2000);
    }

    new Thread(() -> {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new MockClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(STRHOST, INTPORT).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }).start();

    //let Junit wait for a while to see what thread will do
    synchronized (this) {
        wait(30000);
    }
}



public static Test suite() { 
return new TestSuite(JPMSalesServerTest.class);
} 
}

class MockClientHandler extends ChannelHandlerAdapter {

    private ByteBuf cMessage = null;
    private int i = 0;


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Connnction is ready ");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {

        String body =  (String)msg;
        System.out.println("server replay ... "+body);
        if(body.equalsIgnoreCase(STRBYE)) {
            System.out.println("server exit");
            System.exit(0);
        } else if(body.equalsIgnoreCase(STRPAUSE)) {
            System.out.println("server pause");
        }else if(body.equalsIgnoreCase(STROK)) {
            String s = prepareClientMsg();
            ctx.writeAndFlush(s);
            System.out.println("Senting :(" +i+"), "+s);
            i++;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        cause.printStackTrace();
        ctx.close();
    }

    public String prepareClientMsg()  {
        int typidx=new Random().nextInt(TYPES.size());
        int prodidx=new Random().nextInt(PRODUCTS.size());
        int validx=new Random().nextInt(VALUES.size());
        int adjidx=new Random().nextInt(ADJUSTS.size());
        return  "{" +
                "\"msgtype\":\"" + TYPES.get(typidx) + "\"," +
                "\"pname\":\"" + PRODUCTS.get(prodidx) + "\"," +
                "\"price\":" + VALUES.get(validx) + "," +
                "\"volume\":" + (typidx==1?  (new Random().nextInt(10)+1):1) + "," +
                "\"adjust\":\"" + (typidx!=2?  "null": ADJUSTS.get(adjidx) +"|"+(new Random().nextInt(3)+1)) +
                "\"}" ;
    }
}

