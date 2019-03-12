package com.app.xxcpush.event;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/11 0011 16:13
 * Describe
 */
public interface PushIotMsgIm {

    /**
     * 开始发送
     */
    void sendMsg();

    /**
     * 发送进度
     *
     * @param max
     * @param progress
     */
    void sendProgress(int max, int progress);

    /**
     * 发送完成
     *
     * @param success
     */
    void sendFinish(boolean success);

    /**
     * 发送错误
     *
     * @param e
     */
    void onError(Exception e);
}
