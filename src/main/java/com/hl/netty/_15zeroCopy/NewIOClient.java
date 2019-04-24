package com.hl.netty._15zeroCopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8899));
        socketChannel.configureBlocking(true); //尽可能读取更多的字节

        String fileName = "D:\\java\\消息队列.md";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();

        //将fileChannel中的字节传到socketChannel中
        // (零拷贝) Many operating systems can transfer bytes directly from the filesystem cache
        // to the target channel without actually copying them.
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println(System.currentTimeMillis()-startTime);
        fileChannel.close();

    }
}
