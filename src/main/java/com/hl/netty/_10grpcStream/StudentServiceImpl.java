package com.hl.netty._10grpcStream;

import io.grpc.stub.StreamObserver;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    /**
     *
     * @param request
     * @param responseObserver  返回结果给户端的一个对象
     */
    @Override
    public void getRealNameByUserName(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接收到客户端的信息"+request.getUsername());

        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三").build());//返回给客户端
        responseObserver.onCompleted(); //告诉客户端onNext()方法已经执行完了
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("接收到客户端的信息"+request.getAge());
        //以流式的形式返回数据给客户端
        responseObserver.onNext(StudentResponse.newBuilder().setName("张三").setAge(20).setCity("北京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("李四").setAge(21).setCity("上海").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("王五").setAge(22).setCity("广州").build());
        responseObserver.onCompleted(); //告诉客户端onNext()方法已经执行完了
    }
}
