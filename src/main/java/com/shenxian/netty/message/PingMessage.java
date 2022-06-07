package com.shenxian.netty.message;

/**
 * @author: shenxian
 * @date: 2022/6/7 16:05
 */
public class PingMessage extends Message {
    @Override
    public int getMessageType() {
        return PingMessage;
    }
}
