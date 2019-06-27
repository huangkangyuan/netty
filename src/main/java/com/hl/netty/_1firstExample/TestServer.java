package com.hl.netty._1firstExample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {

    public static void main(String[] args) throws InterruptedException {

        //不断的接收客户端的连接(不对连接进行处理)
        EventLoopGroup bossGroup = new NioEventLoopGroup();//底层死循环
        //对连接进行处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();//底层死循环

        try {
            //Netty简化服务端启动的类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer()); //真正处理的类

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();  //阻塞等待
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();   //优雅关闭
            workerGroup.shutdownGracefully();
        }
    }
}
