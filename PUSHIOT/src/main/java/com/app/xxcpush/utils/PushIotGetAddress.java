package com.app.xxcpush.utils;

import com.app.xxcpush.entity.AddressIPInfo;
import com.google.gson.Gson;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/15 0015 10:31
 * Describe
 */
public class PushIotGetAddress {

    /**
     * 获取地址
     */
    public static String getAddress() {
        String address = "";
        String json = PushIotNetUtil.post("https://api.ttt.sh/ip/qqwry/", null);
        if (json != null && !json.equals("")) {
            AddressIPInfo addressIPInfo = new Gson().fromJson(json, AddressIPInfo.class);
            if (addressIPInfo != null) {
                address = addressIPInfo.getAddress();
            }
        }
        return address;
    }
}
