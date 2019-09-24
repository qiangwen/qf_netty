package com.qf.netty.app;

import com.qf.netty.handler.ServerHandler;
import com.qf.netty.util.BeanContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.LineEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author: wenqiang
 * @date: 2019-09-17 15:16
 */
public class NettyServer extends Thread {

    private int port;

    public NettyServer(int port){
        this.port = port;
    }


    @Override
    public void run() {
        //构造线程池，线程大小默认是cpu核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LineBasedFrameDecoder(1024))
                                    .addLast(new StringDecoder())
//                                    .addLast(new LineEncoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new IdleStateHandler(60,30,0))
                                    .addLast(new ServerHandler());
                        }
                    })
//                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .option(ChannelOption.SO_BACKLOG,128);

            ChannelFuture f = b.bind(port).sync();
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("服务器启动成功......");
                }
            });
            f.channel().closeFuture().sync();
            System.out.println("服务器关闭成功.......");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
