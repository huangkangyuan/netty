package com.hl.netty._12grpcStreamBoth;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcStreamServerBoth {

    private Server server;

    private void start() throws IOException {
        this.server  = ServerBuilder.forPort(8899).addService(new StudentServiceImpl()).build().start();
        System.out.println("Server started");

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("关闭jvm");
            GrpcStreamServerBoth.this.stop();
        }));

        System.out.println("执行到这里");
    }

    private void stop(){
        if(null!=this.server){
            this.server.shutdown();
        }
    }

    private void awaitTermination() throws InterruptedException {
        if(null!=this.server){
            this.server.awaitTermination();//一直等待
//            this.server.awaitTermination(3000, TimeUnit.MILLISECONDS);//一直等待
        }
    }


    public static void main(String[] args) throws Exception {
        GrpcStreamServerBoth server = new GrpcStreamServerBoth();
        server.start();
        server.awaitTermination(); //如果不加的话 服务一启动就会停止掉
    }
}
