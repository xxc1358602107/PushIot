package com.app.xxcpush.entity;

import com.app.xxcpush.utils.AppUtils;
import com.app.xxcpush.utils.PushIotGetAddress;

import java.io.Serializable;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/8 0008 18:49
 * Describe
 */
public class PushIotInfo implements Serializable {

    private String appKey = "";
    private String alias = "";
    private String packageName = "";
    private int versionCode = -1;
    private String versionName = "";
    private String address;
    private String iotType = "ANDROID_APP";

    public PushIotInfo(String appKey, String alias) {
        this.appKey = appKey;
        this.alias = alias;
        this.packageName = AppUtils.getAppProcessName();
        this.versionCode = AppUtils.getVersionCode();
        this.versionName = AppUtils.getVersionName();
        this.address = PushIotGetAddress.getAddress();
    }
}
