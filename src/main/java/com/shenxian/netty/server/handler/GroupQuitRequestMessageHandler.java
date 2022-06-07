package com.shenxian.netty.server.handler;

import com.shenxian.netty.message.GroupQuitRequestMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: shenxian
 * @date: 2022/6/7 15:10
 */
@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupQuitRequestMessage groupQuitRequestMessage) throws Exception {

    }
}
