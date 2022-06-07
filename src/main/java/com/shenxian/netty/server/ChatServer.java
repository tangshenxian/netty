package com.shenxian.netty.server;

import com.shenxian.netty.protocol.MessageCodecSharable;
import com.shenxian.netty.protocol.ProtocolFrameDecoder;
import com.shenxian.netty.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: shenxian
 * @date: 2022/5/13 14:29
 */
@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler();
        MessageCodecSharable messageCodec = new MessageCodecSharable();
        LoginRequestMessageHandler loginHandler = new LoginRequestMessageHandler();
        ChatRequestMessageHandler chatHandler = new ChatRequestMessageHandler();
        GroupCreateRequestMessageHandler groupCreateHandler = new GroupCreateRequestMessageHandler();
        GroupJoinRequestMessageHandler groupJoinHandler = new GroupJoinRequestMessageHandler();
        GroupChatRequestMessageHandler groupChatHandler = new GroupChatRequestMessageHandler();
        GroupMembersRequestMessageHandler groupMembersHandler = new GroupMembersRequestMessageHandler();
        GroupQuitRequestMessageHandler groupQuitHandler = new GroupQuitRequestMessageHandler();
        QuitHandler quitHandler = new QuitHandler();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProtocolFrameDecoder());
                    ch.pipeline().addLast(loggingHandler);
                    ch.pipeline().addLast(messageCodec);
                    ch.pipeline().addLast(loginHandler);
                    ch.pipeline().addLast(chatHandler);
                    ch.pipeline().addLast(groupCreateHandler);
                    ch.pipeline().addLast(groupChatHandler);
                    ch.pipeline().addLast(groupJoinHandler);
                    ch.pipeline().addLast(groupMembersHandler);
                    ch.pipeline().addLast(groupQuitHandler);
                    ch.pipeline().addLast(quitHandler);
                }
            });
            Channel channel = serverBootstrap.bind("localhost", 8080).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("server error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

}
