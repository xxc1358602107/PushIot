package com.app.xxcpush.entity;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/15 0015 10:36
 * Describe
 */
public class AddressIPInfo {

    /**
     * code : 200
     * ip : 116.22.209.24
     * address : 广东省广州市白云区 电信
     * date : 2019-03-15 10:20:18
     */

    private int code;
    private String ip;
    private String address;
    private String date;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
