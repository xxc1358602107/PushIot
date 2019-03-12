package com.app.xxcpush.entity;

import java.io.Serializable;
import java.util.UUID;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/8 0008 18:49
 * Describe
 */
public class PushIotInfo implements Serializable {

    private String appKey = "";
    private String masterSecret = "";
    private String alias = "";

    public PushIotInfo(String appKey, String masterSecret, String alias) {
        this.appKey = appKey;
        this.masterSecret = masterSecret;
        this.alias = alias;
    }
}
