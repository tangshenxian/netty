package com.shenxian.netty.server.handler;

import com.shenxian.netty.message.ChatRequestMessage;
import com.shenxian.netty.message.ChatResponseMessage;
import com.shenxian.netty.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: shenxian
 * @date: 2022/6/7 14:55
 */
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage message) throws Exception {
        String to = message.getTo();
        Channel channel = SessionFactory.getSession().getChannel(to);
        // 对方在线
        if (channel != null) {
            channel.writeAndFlush(new ChatResponseMessage(message.getFrom(), message.getContent()));
        } else {
            ctx.channel().writeAndFlush(new ChatResponseMessage(false, "对方用户不存在或不在线"));
        }
    }
}
