package com.hl.netty._14NIO;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NioServer {

    private static Map<String,SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        //服务器端的socket
        ServerSocket serverSocket  = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        //将channel注册时selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            try{
                selector.select();//返回的是selector所关注的事件(兴趣集合)
                Set<SelectionKey> selectionKeys = selector.selectedKeys();//之前所注册的所有keys
                for (SelectionKey selectionKey : selectionKeys) {
                    //一开始只有客户端发起的连接

                    //跟客户端进行交互的
                    SocketChannel clientChannel;
                    try {
                        if (selectionKey.isAcceptable()){
                            //获取到当前事件发生再哪个通道channel上
                            //因为一开始只有ServerSocketChannel注册到selector,所以selectionKey对应的channel一定是ServerSocketChannel
                            ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
                            clientChannel = serverChannel.accept(); //服务器跟客户端建立起连接
                            clientChannel.configureBlocking(false);
                            clientChannel.register(selector, selectionKey.OP_READ);//关注的是数据的读取

                            String key = "【"+UUID.randomUUID().toString()+"】";
                            clientMap.put(key, clientChannel);

                        }else if (selectionKey.isReadable()){
                            //因为上面只对clientChannel注册了读请求
                            clientChannel = (SocketChannel)selectionKey.channel();

                            ByteBuffer readBuffer = ByteBuffer.allocate(36);
                            int count = clientChannel.read(readBuffer);

                            if (count > 0){
                                readBuffer.flip();
                                Charset charset = Charset.forName("UTF-8");

                                String receiveMessage = String.valueOf(charset.decode(readBuffer).array());
                                System.out.println(clientChannel+":"+receiveMessage);

                                String sendKey = null;
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    if (clientChannel==entry.getValue()){
                                        sendKey=entry.getKey(); //拿到发送者的UUID
                                        break;
                                    }
                                }

                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    SocketChannel value = entry.getValue();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(512);
                                    writeBuffer.put((sendKey+":"+receiveMessage).getBytes());

                                    writeBuffer.flip();
                                    value.write(writeBuffer);
                                }
                            }
                        }
                    }catch (Exception e){

                    }
                };
                selectionKeys.clear();

            }catch (Exception e){
            }
        }
    }
}
