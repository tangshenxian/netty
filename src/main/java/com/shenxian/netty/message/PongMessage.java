package com.shenxian.netty.message;

/**
 * @author: shenxian
 * @date: 2022/6/7 16:06
 */
public class PongMessage extends Message {
    @Override
    public int getMessageType() {
        return PongMessage;
    }
}
