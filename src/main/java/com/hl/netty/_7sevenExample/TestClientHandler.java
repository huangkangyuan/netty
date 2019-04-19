package com.hl.netty._7sevenExample;

import com.hl.netty._6protobuf.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Random;


public class TestClientHandler extends SimpleChannelInboundHandler<DataInfo.MyMessage> {

    private static final Logger logger = LoggerFactory.getLogger(TestClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //0 1 2
        int randomInt = new Random().nextInt(3);
        DataInfo.MyMessage myMessage = null;

        if (0 == randomInt) {
            myMessage = DataInfo.MyMessage.newBuilder()
                    .setDataType(DataInfo.MyMessage.DataType.StudentType)
                    .setStudent(DataInfo.Student.newBuilder()
                            .setName("张三")
                            .setAddress("广州")
                            .setAge(22).build())
                    .build();

        } else if (1 == randomInt) {
            myMessage = DataInfo.MyMessage.newBuilder()
                    .setDataType(DataInfo.MyMessage.DataType.DogType)
                    .setDog(DataInfo.Dog.newBuilder()
                            .setName("狗")
                            .setAge(23).build())
                    .build();
        } else {
            myMessage = DataInfo.MyMessage.newBuilder()
                    .setDataType(DataInfo.MyMessage.DataType.CatType)
                    .setCat(DataInfo.Cat.newBuilder()
                            .setName("猫")
                            .setAge(24).build())
                    .build();
        }
        ctx.channel().writeAndFlush(myMessage);  //发送给服务端
    }
}
