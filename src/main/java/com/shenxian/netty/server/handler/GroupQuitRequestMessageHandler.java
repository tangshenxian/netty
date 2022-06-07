package com.shenxian.netty.server.handler;

import com.shenxian.netty.message.GroupQuitRequestMessage;
import com.shenxian.netty.message.GroupQuitResponseMessage;
import com.shenxian.netty.server.session.Group;
import com.shenxian.netty.server.session.GroupSessionFactory;
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
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage message) throws Exception {
        Group group = GroupSessionFactory.getGroupSession().removeMember(message.getGroupName(), message.getUsername());
        if (group != null) {
            ctx.writeAndFlush(new GroupQuitResponseMessage(true, group.getName()) + "退出成功");
        } else {
            ctx.writeAndFlush(new GroupQuitResponseMessage(false, message.getGroupName() + "群聊不存在"));
        }
    }
}
