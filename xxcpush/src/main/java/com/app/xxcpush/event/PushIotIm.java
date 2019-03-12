package com.app.xxcpush.event;

import io.netty.buffer.ByteBuf;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2018/11/21 0021 14:27
 * Describe 事件监听
 */
public interface PushIotIm {

    /**
     * 开始初始化
     */
    void initConnect();

    /**
     * 连接成功
     */
    void succeedConnect();

    /**
     * 消息接收监听
     *
     * @param msg
     */
    void listenMsg(Object msg);

    /**
     * 断开连接
     *
     * @param cause
     */
    void exitConnect(Throwable cause);
}
