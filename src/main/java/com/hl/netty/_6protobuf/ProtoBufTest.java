package com.hl.netty._6protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {


        DataInfo.Student student = DataInfo.Student.newBuilder()
                                            .setName("hky")
                                            .setAge(21)
                                            .setAddress("广州")
                                            .build();

        byte[] byteArray = student.toByteArray();

        DataInfo.Student studentFromByteArray = DataInfo.Student.parseFrom(byteArray);
        System.out.println(student.toString());//汉字会转义

    }
}
