package com.jpmorgan.onlinetest.Nettry;

import com.jpmorgan.onlinetest.SalesRecord;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jpmorgan.onlinetest.NewConstant.*;

public class JPMSalesServer {

    public void bind(int port) throws Exception {
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
                                          ch.pipeline().addLast(new SalesServerHandler());
                                      }
                                  }
                    )
                    .option(ChannelOption.SO_BACKLOG, INTBUFSIZE)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new JPMSalesServer().bind(INTPORT);
    }
}


class SalesServerHandler extends ChannelHandlerAdapter {
    private static List<String> msgStorage = new ArrayList<>();
    private static List<SalesRecord> msgProcess = new ArrayList<>();
    private static Map<String, List<SalesRecord>> mapDB = new HashMap();
    private static Map<String, List<SalesRecord>> mapAdjust = new HashMap();

    private static int i = 0;
    private static int j = 0;
    private static int x = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Connnction active OK ");
        ctx.writeAndFlush(STROK);  //the client is waiting for this "OK" to begin sending data
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String receiveMsg = (String) msg;
        System.out.println("server get: " + receiveMsg);
        //business starts here
        if (receiveMsg != null && receiveMsg.startsWith("{\"msgtype\"")) {
            i++;
            j++;
            x++;
            System.out.println("processing ..." + x);

            SalesRecord pRed = processMessage(receiveMsg);
            updateMapDB(pRed);
            processAdjustment(pRed);

            if (i == INTA) {
                logCalcRecords();
                i = 0;
            }
            if (j == INTB) {
                ctx.writeAndFlush(STRPAUSE);
                logAdjustments();
                j = 0;
            }
            if (x == INTX) {
                ctx.writeAndFlush(STRBYE);
                System.exit(0);
            }
        }
        //in this demo, even whatever crap the server sent, let it continue
        ctx.writeAndFlush(STROK);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private void logAdjustments() {
        System.out.println("\n\t@@@ logging every 50 sales ");

        for (Map.Entry entry : mapAdjust.entrySet()) {
            String pName = entry.getKey().toString();
            List<SalesRecord> list = (List) entry.getValue();
            String allAdjust = list.stream().map(SalesRecord::getAdjust).reduce("[", (l, r) -> l + (l.equals("[") ? "" : ", ") + r) + "]";
            System.out.println("\t@@@ Adjustment: " + pName + ",\tAdjusts: " + allAdjust);
        }
    }

    private void logCalcRecords() {
        System.out.println("\n\t*** logging every 10 sales ");

        for (Map.Entry entry : mapDB.entrySet()) {
            String pName = entry.getKey().toString();
            List<SalesRecord> list = (List) entry.getValue();
            double sum = list.stream().collect(Collectors.summarizingDouble((SalesRecord p) -> p.getcost())).getSum();
            System.out.println("\t*** Summary: " + pName + ",\tSUM: " + new DecimalFormat("0.00").format(sum));
        }
    }

    private void processAdjustment(SalesRecord pRed) {
        String adjust = pRed.getAdjust();

        if (adjust != null && adjust.length() > 0 && !adjust.contains("null")) {
            updateMapAdjust(pRed);
            String[] split = adjust.split("\\|");
            String action = split[0];
            double howmuch = Double.parseDouble(split[1]);

            String pname_Key = pRed.getPname();
            Stream<SalesRecord> stream = mapDB.get(pname_Key).stream();
            switch (action) {
                case ADD:
                    stream.forEach(record -> record.setPrice(record.getPrice() + howmuch));
                    break;
                case SUB:
                    stream.forEach(record -> record.setPrice(record.getPrice() - howmuch));
                    break;
                case MULTI:
                    stream.forEach(record -> record.setPrice(record.getPrice() * howmuch));
                    break;
                default:
                    break;
            }
        }
    }

    private void updateMapAdjust(SalesRecord pRed) {
        String pnameKey = pRed.getPname();
        if (mapAdjust.containsKey(pnameKey))
            mapAdjust.get(pnameKey).add(pRed);
        else {
            List<SalesRecord> lst = new ArrayList<>();
            lst.add(pRed);
            mapAdjust.put(pnameKey, lst);
        }
    }

    private void updateMapDB(SalesRecord pRed) {
        String pnameKey = pRed.getPname();
        if (mapDB.containsKey(pnameKey))
            mapDB.get(pnameKey).add(pRed);
        else {
            List<SalesRecord> lst = new ArrayList<>();
            lst.add(pRed);
            mapDB.put(pnameKey, lst);
        }
    }

    private SalesRecord processMessage(String receiveMsg) {
        msgStorage.add(receiveMsg);
        SalesRecord record = getSalesRecordFromMsg(receiveMsg);
        msgProcess.add(record);
        return record;
    }

    /**
     * turn json string into a pojo in a dirty way, because not allow to use extra json library
     *
     * @param receiveMsg
     * @return SalesRecord object
     */
    private SalesRecord getSalesRecordFromMsg(String receiveMsg) {
        SalesRecord r = new SalesRecord();
        String[] fields = receiveMsg.split(",");
        for (String f : fields) {
            String substring = f.substring(f.indexOf(":") + 1);
            String substring2 = substring.replaceAll("\"", "");
            String substring3 = substring2.replaceAll("}", "");
            if (f.contains(MSGTYPE)) {
                r.setMsgtype(substring3);
            }
            if (f.contains(PNAME)) {
                r.setPname(substring3);
            }
            if (f.contains(PRICE)) {
                Double price = Double.valueOf(substring);
                r.setPrice(price);
            }
            if (f.contains(VOLUME)) {
                r.setVolume(Double.valueOf(substring));
            }
            if (f.contains(ADJUST)) {
                r.setAdjust(substring3);
            }
        }
        return r;
    }
}
