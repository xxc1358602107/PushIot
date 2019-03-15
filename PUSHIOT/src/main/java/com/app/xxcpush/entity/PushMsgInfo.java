package com.app.xxcpush.entity;

import com.app.xxcpush.utils.FacilityUtil;
import com.app.xxcpush.utils.PushIotDateUtil;
import com.app.xxcpush.utils.PushIotUtils;
import com.orhanobut.hawk.Hawk;

import java.io.Serializable;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/11 0011 17:23
 * Describe
 */
public class PushMsgInfo implements Serializable {

    /**
     * 用户自定义消息
     */
    public static final int US_MSG_CODE = 0;
    public static final String US_MSG_CODE_MG = "自定义消息";

    /**
     * 上线标识
     */
    public static final int SY_MSG_CODE = 1;
    public static final String SY_MSG_CODE_MG = "上线标识";


    /**
     * 消息类别
     */
    private int type;

    /**
     * 消息类别描述
     */
    private String typeStr;

    /**
     * 消息ID
     */
    private String msgId = "";

    /**
     * 消息时间
     */
    private String msgTime;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 用户别名
     */

    private String alias;


    /**
     * 设备IMEI
     */
    private String imei;


    /**
     * 设备MAC地址
     */
    private String mac;


    public PushMsgInfo(int type, String typeStr, String msgContent) {
        this.type = type;
        this.typeStr = typeStr;
        this.msgContent = msgContent;
        this.msgId = PushIotUtils.get32UUID();
        this.msgTime = PushIotDateUtil.getTime();
        this.alias = Hawk.get("alias", "");
        this.imei = FacilityUtil.getIMEI();
        this.mac = FacilityUtil.getMac();
    }
}
