package test.com.jpmorgan.onlinetest.Nettry;

import com.jpmorgan.onlinetest.Nettry.JPMSalesClient;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.jpmorgan.onlinetest.NewConstant.*;

/** 
* JPMSalesClient Tester.
* 
* @author SebastianX
* @since <pre>02/17/2018</pre> 
* @version 1.0 
*/ 
public class JPMSalesClientTest extends TestCase {
public JPMSalesClientTest(String name) {
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
* Method: connect(int port, String host) 
* 
*/ 
public void testConnect() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: main(String[] args) 
* 
*/ 
public void testMain() throws Exception {

    //mock a server
    new Thread(() -> {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                                      @Override
                                      protected void initChannel(SocketChannel ch) throws Exception {
                                          ch.pipeline().addLast(new StringDecoder());
                                          ch.pipeline().addLast(new StringEncoder());
                                          ch.pipeline().addLast(new MockServerHandler());
                                      }
                                  }
                    )
                    .option(ChannelOption.SO_BACKLOG, INTBUFSIZE)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(INTPORT).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }).start();

    //leave server some time to start
    synchronized (this) {
        wait(2000);
    }

    new Thread(() -> {
        try {
            Method method = JPMSalesClient.class.getMethod("main",String[].class);
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

    //let Junit wait for a while to see what thread will do
    synchronized (this) {
        wait(30000);
    }
}



public static Test suite() { 
return new TestSuite(JPMSalesClientTest.class);
} 
}


class MockServerHandler extends ChannelHandlerAdapter {

    private  int i = 0;
    private  int j = 0;
    private  int x = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Connnction active OK ");
        ctx.writeAndFlush(STROK);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String receiveMsg = (String) msg;
        System.out.println("server get: " + receiveMsg);
        //business here, but in test just do nothing and discard the msg

        i++;
        j++;
        x++;
        System.out.println("processing ..."+x);

        if (i == INTA) {
            System.out.println("\n\t*** logging every 10 sales ");
            i = 0;
        }

        if (j == INTB) {
            ctx.writeAndFlush(STRPAUSE);
            System.out.println("\n\t@@@ logging every 50 sales ");
            j = 0;
        }

        if (x == INTX) {
            ctx.writeAndFlush(STRBYE);
            System.out.println("\n\tXXX finishing job ... ");
            return;
            //System.exit(0);
        }

        ctx.writeAndFlush(STROK);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}