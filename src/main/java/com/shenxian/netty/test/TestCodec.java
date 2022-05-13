package com.shenxian.netty.test;

import com.shenxian.netty.message.LoginRequestMessage;
import com.shenxian.netty.protocol.MessageCodec;
import com.shenxian.netty.protocol.MessageCodecSharable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author: shenxian
 * @date: 2022/5/13 10:14
 */
public class TestCodec {
    public static void main(String[] args) throws Exception {
        LoggingHandler loggingHandler = new LoggingHandler();
        MessageCodecSharable messageCodecSharable = new MessageCodecSharable();
        EmbeddedChannel channel = new EmbeddedChannel(
                loggingHandler,
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                messageCodecSharable);

        // encode
        LoginRequestMessage message = new LoginRequestMessage("Zhang San", "123");
        channel.writeOutbound(message);

        // decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);

        // 入站
        ByteBuf s1 = buf.slice(0, 100);
        ByteBuf s2 = buf.slice(100, buf.readableBytes() - 100);
        s1.retain();
        channel.writeInbound(s1);
        channel.writeInbound(s2);
    }
}
