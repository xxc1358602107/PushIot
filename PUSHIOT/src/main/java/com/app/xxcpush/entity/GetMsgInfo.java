package com.app.xxcpush.entity;

import java.io.Serializable;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/11 0011 17:23
 * Describe
 */
public class GetMsgInfo implements Serializable {

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
     * 包名不存在
     */
    public static final int US_PACKAGE_CODE = 3;
    public static final String US_PACKAGE_MG = "包名不存在，请前往平台添加该应用！";

    /**
     * KEY有误
     */
    public static final int US_KEY_CODE = 4;
    public static final String US_KEY_MG = "APPKEY有误！";


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


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
