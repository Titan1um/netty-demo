package com.jun.nettydemo.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //dump
//        System.out.println(1);
//        ((ByteBuf) msg).release();
        try {
//            System.out.println(msg);
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }

            //read
            ByteBuf in = (ByteBuf) msg;
////            while (in.isReadable()) {
////                System.out.println((char) in.readByte());
////                System.out.flush();
////            }
//
            System.out.println(in.toString(CharsetUtil.US_ASCII));
            System.out.flush();
        } finally {
//            ReferenceCountUtil.release((ByteBuf) msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
