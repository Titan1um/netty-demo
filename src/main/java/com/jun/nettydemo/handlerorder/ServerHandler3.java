package com.jun.nettydemo.handlerorder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import jdk.nashorn.internal.objects.annotations.Constructor;

@ChannelHandler.Sharable
public class ServerHandler3 extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Other注册事件");
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Other取消注册事件");
        ctx.fireChannelUnregistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Other有新客户端连接接入。。。" + ctx.channel().remoteAddress());
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Other失去连接");
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Other读客户端传入数据=" + in.toString(CharsetUtil.UTF_8));
        final ByteBuf byteBuf = Unpooled.copiedBuffer("Other channelRead Netty rocks!", CharsetUtil.UTF_8);
        ChannelPipeline pipeline = ctx.pipeline();
        ctx.writeAndFlush(byteBuf);
        ctx.fireChannelRead(msg);
        //ReferenceCountUtil.release(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        //注意，直接ctx.write和pipeline.write的执行顺序并不同哦
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Other执行成功=" + future.isSuccess());
                }
            }
        });
        final ByteBuf byteBuf = Unpooled.copiedBuffer("Other channelReadComplete Netty rocks!", CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                } else {

                }
//                ReferenceCountUtil.release(byteBuf);//?????????????????
            }
        });
        ctx.fireChannelReadComplete();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("Other  userEventTriggered");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Other  channelWritabilityChanged");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}