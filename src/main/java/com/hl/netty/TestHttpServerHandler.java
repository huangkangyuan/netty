package com.hl.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;


public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TestHttpServerHandler.class);
    //读取客户端发过来的请求，并且响应给客户端
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        logger.info("客户端请求进来...");
        //将内容发送给客户端
        ByteBuf content = Unpooled.copiedBuffer("Hello world", CharsetUtil.UTF_8);

        //http响应
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

        //返回给客户端
        ctx.writeAndFlush(response);
    }
}
