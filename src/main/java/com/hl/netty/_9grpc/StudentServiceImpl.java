package com.hl.netty._9grpc;

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
}
