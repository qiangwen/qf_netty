package com.qf.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: wenqiang
 * @date: 2019-09-17 15:17
 */
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger replyCnt = new AtomicInteger(0);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server receive msg : " + msg);
        ctx.writeAndFlush("server reply,num:" + replyCnt.incrementAndGet() + "\n");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        super.userEventTriggered(ctx, evt);
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(IdleState.READER_IDLE == event.state()){
                ctx.close();
            } else if(IdleState.WRITER_IDLE == event.state()){
                System.out.println("write idle");
            }else{
                System.out.println("all idle");
            }

        }
        ctx.fireUserEventTriggered(evt);
    }
}
