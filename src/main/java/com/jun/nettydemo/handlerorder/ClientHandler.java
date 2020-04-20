package com.jun.nettydemo.handlerorder;

import com.sun.org.apache.xpath.internal.operations.String;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    public int i = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf buf = Unpooled.buffer(Client.SIZE);
        for (int i = 0; i < buf.capacity(); i++) {
            buf.writeByte((byte) i);
        }

        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(buf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
