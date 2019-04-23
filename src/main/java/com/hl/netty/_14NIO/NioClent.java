package com.hl.netty._14NIO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioClent {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            socketChannel.connect(new InetSocketAddress(8899));

            while (true) {
                selector.select();//如果服务端返回了

                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                for (SelectionKey selectionKey : selectionKeys) {
                    if (selectionKey.isConnectable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        if (client.isConnectionPending()) {
                            //连接真正建立好了
                            client.finishConnect();

                            ByteBuffer writeBuffer = ByteBuffer.allocate(512);
                            writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());

                            writeBuffer.flip();
                            client.write(writeBuffer);

                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            executorService.submit(() -> {
                                while (true) {
                                    try {
                                        writeBuffer.clear();

                                        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                                        String message = bufferedReader.readLine();
                                        writeBuffer.put(message.getBytes());

                                        writeBuffer.flip();
                                        client.write(writeBuffer);
                                    } catch (Exception e) {

                                    }
                                }
                            });
                        }
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(512);
                        int count = client.read(readBuffer);

                        if (count > 0) {
                            String receiveMessage = new String(readBuffer.array(),0,count);
                            System.out.println(count + ":" + receiveMessage);
                        }
                    }
                }
                selectionKeys.clear();
            }
        } catch (Exception e) {
        }
    }
}
