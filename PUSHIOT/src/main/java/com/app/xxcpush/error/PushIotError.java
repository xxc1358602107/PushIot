package com.app.xxcpush.error;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/8 0008 18:02
 * Describe 自定义错误代码
 */
public enum PushIotError implements PushIotExceptionInfo {

    APP_KEY_ERROR(10001, "APPKEY为空"),
    APP_ALIAS_ERROR(10002, "别名为空"),
    APP_MSG_ERROR_1(10003, "消息发送失败，连接异常！1"),
    APP_MSG_ERROR_2(10004, "消息发送失败，连接异常！2"),
    APP_MSG_ERROR_3(10005, "消息发送失败，连接异常！3"),
    APP_BREAK_ERROR(10006, "断开连接！"),
    APP_KEY_ERROR_1(10007, "APPKEY有误！"),
    APP_BACKAGE_ERROR(10008, "包名不存在，请前往平台添加该应用！"),;

    public int code;
    public String message;

    PushIotError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
