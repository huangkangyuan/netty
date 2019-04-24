package com.hl.netty._15zeroCopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class OldIOClient {

    public static void main(String[] args) throws IOException {

        Socket socket  = new Socket("localhost",8899);

        String fileName = "E:/solr/solr-8.0.0.zip";

        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;

        long start = System.currentTimeMillis();

        while((readCount=inputStream.read(buffer))>=0){
            total += readCount;
            dataOutputStream.write(buffer);
            System.out.println(readCount);
        }

        System.out.println("传送的字节数"+total+",时间："+(System.currentTimeMillis()-start));

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
