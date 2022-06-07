package com.shenxian.netty.server.handler;

import com.shenxian.netty.message.GroupMembersRequestMessage;
import com.shenxian.netty.message.GroupMembersResponseMessage;
import com.shenxian.netty.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: shenxian
 * @date: 2022/6/7 15:09
 */
@ChannelHandler.Sharable
public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestMessage message) throws Exception {
        ctx.writeAndFlush(new GroupMembersResponseMessage(GroupSessionFactory.getGroupSession().getMembers(message.getGroupName())));
    }
}
