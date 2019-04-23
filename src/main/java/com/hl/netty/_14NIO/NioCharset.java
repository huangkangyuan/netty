package com.hl.netty._14NIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.RandomAccess;
import java.util.SortedMap;

public class NioCharset {

    public static void main(String[] args) throws Exception {
        String inputFile = "Nio_IN.txt";
        String outputFile = "Nio_OUT.txt";

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile,"r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile,"rw");

        long length = new File(inputFile).length();

        FileChannel inputChannel = inputRandomAccessFile.getChannel();
        FileChannel outputChannel = outputRandomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = inputChannel.map(FileChannel.MapMode.READ_ONLY, 0, length);
        System.out.println(mappedByteBuffer);


//        SortedMap<String, Charset> stringCharsetSortedMap = Charset.availableCharsets();
//        for (String s : stringCharsetSortedMap.keySet()) {
//            System.out.println(s);
//        }

        Charset charset = Charset.forName("utf-8");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        CharBuffer charBuffer = decoder.decode(mappedByteBuffer);//把字节数组转成字符串
        ByteBuffer byteBuffer = encoder.encode(charBuffer);  //把字符串转成字节数组

        outputChannel.write(byteBuffer);

        inputRandomAccessFile.close();
        outputRandomAccessFile.close();

    }
}
