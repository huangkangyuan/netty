package com.hl.netty._11grpcRequestStream;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;

public class GrpcStreamClient {

    public static void main(String[] args) throws InterruptedException {

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8899)
                                                            .usePlaintext().build();

        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);

        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(managedChannel);

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

        System.out.println("=============================");

        StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                value.getStudentResponseList().forEach(studentResponse -> {
                    System.out.println("name="+studentResponse.getName());
                    System.out.println("name="+studentResponse.getAge());
                    System.out.println("name="+studentResponse.getCity());
                    System.out.println("******************************");
                });
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("client onCompleted");
            }
        };

        //只要客户端是以流式的形式请求,都是异步的
        StreamObserver<StudentRequest> studentRequestStreamObserver = stub.getStudentsWrapperByAge(studentResponseListStreamObserver);
        //客户端发送数据到服务端,因为是异步执行的 所以当客户端发送完数据后就退出了
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(29).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(31).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(32).build());
        studentRequestStreamObserver.onCompleted();

        Thread.sleep(3000);
    }
}
