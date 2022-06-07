package com.shenxian.netty.server.handler;

import com.shenxian.netty.message.GroupChatRequestMessage;
import com.shenxian.netty.message.GroupChatResponseMessage;
import com.shenxian.netty.server.session.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * @author: shenxian
 * @date: 2022/6/7 15:08
 */
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage message) throws Exception {
        String groupName = message.getGroupName();
        String from = message.getFrom();
        String content = message.getContent();

        List<Channel> channels = GroupSessionFactory.getGroupSession().getMembersChannel(groupName);
        for (Channel channel : channels) {
            channel.writeAndFlush(new GroupChatResponseMessage(from, content));
        }
    }
}
