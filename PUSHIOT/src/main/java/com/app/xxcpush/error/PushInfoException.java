package com.app.xxcpush.error;

import android.util.Log;

import com.app.xxcpush.init.PushIot;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/15 0015 11:31
 * Describe
 */
public class PushInfoException extends RuntimeException {

    private PushIotExceptionInfo pushIotExceptionInfo;

    public PushInfoException(PushIotExceptionInfo pushIotExceptionInfo) {
        this.pushIotExceptionInfo = pushIotExceptionInfo;
        Log.e(PushIot.TAG, "======================Exception异常提示======================");
        Log.e(PushIot.TAG, pushIotExceptionInfo.getCode() + "(" + pushIotExceptionInfo.getMessage() + ")");
        Log.e(PushIot.TAG, "====================================================");
    }

    public PushIotExceptionInfo getPushInfoException() {
        return pushIotExceptionInfo;
    }
}
