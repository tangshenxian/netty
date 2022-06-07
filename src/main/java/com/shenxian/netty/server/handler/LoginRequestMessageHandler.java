package com.shenxian.netty.server.handler;

import com.shenxian.netty.message.LoginRequestMessage;
import com.shenxian.netty.message.LoginResponseMessage;
import com.shenxian.netty.server.service.UserServiceFactory;
import com.shenxian.netty.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: shenxian
 * @date: 2022/6/7 14:53
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage message) throws Exception {
        String username = message.getUsername();
        String password = message.getPassword();
        boolean login = UserServiceFactory.getUserService().login(username, password);
        LoginResponseMessage responseMessage;
        if (login) {
            // 登录成功加入会话管理器
            SessionFactory.getSession().bind(ctx.channel(), username);
            responseMessage = new LoginResponseMessage(true, "登录成功");
        } else {
            responseMessage = new LoginResponseMessage(false, "登录失败。用户名或密码错误");
        }
        ctx.writeAndFlush(responseMessage);
    }
}
