package com.hl.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class NettyServer {

    public static void main(String[] args) {
        //不断的接收客户端的连接(不对连接进行处理)
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //对连接进行处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        

    }
}
