package com.shenxian.netty.server.handler;

import com.shenxian.netty.message.GroupJoinRequestMessage;
import com.shenxian.netty.message.GroupJoinResponseMessage;
import com.shenxian.netty.server.session.Group;
import com.shenxian.netty.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: shenxian
 * @date: 2022/6/7 15:09
 */
@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage message) throws Exception {
        Group group = GroupSessionFactory.getGroupSession().joinMember(message.getGroupName(), message.getUsername());
        if (group != null) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, message.getGroupName() + "群加入成功"));
        } else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, message.getGroupName() + "群不存在"));
        }
    }
}
