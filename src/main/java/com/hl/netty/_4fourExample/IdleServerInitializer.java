package com.hl.netty._4fourExample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class IdleServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * channel创建后就调用此方法进行初始化
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //空闲状态检测 handler非常类似拦截器过滤器(责任链模式)
        pipeline.addLast(new IdleStateHandler(5,7,10, TimeUnit.SECONDS));

        pipeline.addLast(new IdleServerHandler());
    }
}
