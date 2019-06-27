package com.hl.netty._15zeroCopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class OldIOClient {

    public static void main(String[] args) throws IOException {

        Socket socket  = new Socket("localhost",8899);

        String fileName = "D:\\java\\消息队列.md";

        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[1024];
        long readCount;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while((readCount = inputStream.read(buffer)) >=0 ){
            total += readCount;
            dataOutputStream.write(buffer);
        }

        System.out.println("传送的字节数"+total+",时间："+(System.currentTimeMillis()-startTime));

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
