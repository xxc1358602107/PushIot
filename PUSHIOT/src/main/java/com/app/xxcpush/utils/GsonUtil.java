package com.app.xxcpush.utils;

import com.app.xxcpush.entity.GetMsgInfo;
import com.app.xxcpush.init.PushIot;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/11 0011 17:41
 * Describe
 */
public class GsonUtil {

    /**
     * 字符串转json
     *
     * @param object
     * @return
     */
    public static String strToJson(Object object) {
        return PushIot.gson.toJson(object);
    }

    /**
     * 获取消息内容
     *
     * @param json
     * @return
     */
    public static GetMsgInfo jsonToClass(String json) {
        GetMsgInfo getMsgInfo = PushIot.gson.fromJson(json, GetMsgInfo.class);
        return getMsgInfo;
    }

}
