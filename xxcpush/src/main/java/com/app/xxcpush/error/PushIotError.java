package com.app.xxcpush.error;

import android.util.Log;

import com.app.xxcpush.init.PushIot;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/8 0008 18:02
 * Describe 自定义错误代码
 */
public class PushIotError extends Throwable {

    public static String KEY_SECRET_ERROR_CODE = "00001";
    public static String ALIAS_ERROR_CODE = "00002";
    public static String MSG_ERROR_CODE = "00003";

    /**
     * @param code    code
     * @param message 描述信息
     */
    public PushIotError(String code, String message) {
        Log.e(PushIot.TAG, code + "--->" + message);
//        try {
//            throw new PushIotError(code, message);
//        } catch (PushIotError pushIotError) {
//            pushIotError.printStackTrace();
//        } finally {
//            Log.e(TAG, message + "--->" + code);
//        }
    }
}
