package com.hl.netty._4fourExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


public class IdleServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(IdleServerHandler.class);


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //处理完后转发到下一个handler进行处理
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

            String eventType = null;

            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                case WRITER_IDLE:
                    eventType = "写空闲";
                case ALL_IDLE:
                    eventType = "读写空闲";
            }

            System.out.println(ctx.channel().remoteAddress()+"超时事件="+eventType);
            ctx.channel().close();
        }
        super.userEventTriggered(ctx, evt);
    }
}
