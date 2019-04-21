package com.hl.netty._11grpcRequestStream;

import io.grpc.stub.StreamObserver;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    /**
     * @param request
     * @param responseObserver 返回结果给户端的一个对象
     */
    @Override
    public void getRealNameByUserName(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接收到客户端的信息" + request.getUsername());

        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三").build());//返回给客户端
        responseObserver.onCompleted(); //告诉客户端onNext()方法已经执行完了
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("接收到客户端的信息" + request.getAge());
        //以流式的形式返回数据给客户端
        responseObserver.onNext(StudentResponse.newBuilder().setName("张三").setAge(20).setCity("北京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("李四").setAge(21).setCity("上海").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("王五").setAge(22).setCity("广州").build());
        responseObserver.onCompleted(); //告诉客户端onNext()方法已经执行完了
    }

    /**
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<StudentRequest> getStudentsWrapperByAge(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {

            @Override
            public void onNext(StudentRequest value) {
                System.out.println("onNext:" + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Throwable:" + t);
            }

            //当客户端以流式的形式发送完所有的数据之后返回給客戶端
            @Override
            public void onCompleted() {
                StudentResponse studentResponse = StudentResponse.newBuilder().setName("张三").setAge(26).setCity("增城").build();
                StudentResponse studentResponse2 = StudentResponse.newBuilder().setName("李四").setAge(22).setCity("白云").build();
                StudentResponseList studentResponseList = StudentResponseList.newBuilder()
                                .addStudentResponse(studentResponse)
                                .addStudentResponse(studentResponse2)
                        .build();
                responseObserver.onNext(studentResponseList);
                responseObserver.onCompleted();
            }
        };
    }
}
