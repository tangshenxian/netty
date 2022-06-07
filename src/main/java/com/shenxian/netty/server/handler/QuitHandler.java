package com.shenxian.netty.server.handler;

import com.shenxian.netty.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: shenxian
 * @date: 2022/6/7 15:34
 */
@Slf4j
@ChannelHandler.Sharable
public class QuitHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当链接断开时触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 断开channel
        SessionFactory.getSession().unbind(ctx.channel());
        log.debug("{}已经断开", ctx.channel());
    }

    /**
     * 捕捉异常时触发
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 断开channel
        SessionFactory.getSession().unbind(ctx.channel());
        log.debug("{}已经异常断开，异常原因：{}", ctx.channel(), cause.getMessage());
    }
}
