package com.hl.netty._10grpcStream;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class GrpcClient {

    public static void main(String[] args) {

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8899)
                                                            .usePlaintext().build();

        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);
        MyResponse myResponse = blockingStub.getRealNameByUserName(MyRequest.newBuilder().setUsername("zhangsan").build());
        System.out.println("服务器返回:"+myResponse.getRealname());

        System.out.println("=============================");

        Iterator<StudentResponse> iterator = blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(20).build());
        while (iterator.hasNext()){
            StudentResponse studentResponse = iterator.next();
            int age = studentResponse.getAge();
            String city = studentResponse.getCity();
            String name = studentResponse.getName();
            System.out.println("服务器返回:"+name+"_"+age+"_"+city);
        }
    }
}
