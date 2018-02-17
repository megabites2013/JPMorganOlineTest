package com.jpmorgan.onlinetest.Nettry;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Random;

import static com.jpmorgan.onlinetest.NewConstant.*;

public class JPMSalesClient {

    public void connect(int port, String host) throws Exception {
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
                            ch.pipeline().addLast(new SalesClientHandler());
                        }
                    });

            ChannelFuture f = b.connect(host, port).sync();

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new JPMSalesClient().connect(INTPORT, STRHOST);
    }
}


class SalesClientHandler extends ChannelHandlerAdapter {

    private int i = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Connnction is ready ");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("server replay ... " + body);

        if (body.equalsIgnoreCase(STRBYE)) {
            System.out.println("server exit");
            System.exit(0);
        } else if (body.equalsIgnoreCase(STRPAUSE)) {
            System.out.println("server pause");
        } else if (body.equalsIgnoreCase(STROK)) {
            String s = prepareClientMsg();
            ctx.writeAndFlush(s);
            System.out.println("Senting :(" + i + "), " + s);
            i++;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * Dirty creates a Client sales record in json format, randomly
     * because not allow to use extra json library
     * @return a sales record in json string
     */
    private String prepareClientMsg() {
        int typidx = new Random().nextInt(TYPES.size());
        int prodidx = new Random().nextInt(PRODUCTS.size());
        int validx = new Random().nextInt(VALUES.size());
        int adjidx = new Random().nextInt(ADJUSTS.size());
        return "{" +
                "\"msgtype\":\"" + TYPES.get(typidx) + "\"," +
                "\"pname\":\"" + PRODUCTS.get(prodidx) + "\"," +
                "\"price\":" + VALUES.get(validx) + "," +
                "\"volume\":" + (typidx == 1 ? (new Random().nextInt(10) + 1) : 1) + "," +
                "\"adjust\":\"" + (typidx != 2 ? "null" : ADJUSTS.get(adjidx) + "|" + (new Random().nextInt(3) + 1)) +
                "\"}";   // Json fields - Type:Produst:price:volume:adjustment
    }
}
