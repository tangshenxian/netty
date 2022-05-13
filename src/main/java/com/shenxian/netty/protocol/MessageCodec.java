package com.shenxian.netty.protocol;

import com.shenxian.netty.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @author: shenxian
 * @date: 2022/5/13 9:41
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf out) throws Exception {
        // 1. 4个字节的魔术
        out.writeBytes(new byte[]{1, 2, 3, 4});
        // 2. 1个字节的版本
        out.writeByte(1);
        // 3. 1个字节的序列化方式 0-jdk 1-json
        out.writeByte(0);
        // 4. 1个字节的指令类型
        out.writeByte(message.getMessageType());
        // 5. 4个字节的请求序号
        out.writeInt(message.getSequenceId());
        // 无意义，对齐填充
        out.writeByte(0xff);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(message);
        byte[] bytes = bos.toByteArray();

        // 6. 四个字节的长度
        out.writeInt(bytes.length);
        // 7. 获取内容的字节数组
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializerType = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message message = (Message) ois.readObject();
        log.debug("{}, {}, {}, {}, {}, {}", magicNum, version, serializerType, messageType, sequenceId, length);
        log.debug("{}", message);
        list.add(message);
    }
}
