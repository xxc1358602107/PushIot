package com.app.xxcpush.init;

import android.content.Context;

import com.app.xxcpush.api.HttpApis;
import com.app.xxcpush.entity.MsgInfo;
import com.app.xxcpush.error.PushIotError;
import com.app.xxcpush.event.PushIotIm;
import com.app.xxcpush.event.PushIotMsgIm;
import com.app.xxcpush.netty.SimpleClient;
import com.app.xxcpush.netty.SimpleClientHandler;
import com.app.xxcpush.utils.GsonUtil;
import com.app.xxcpush.utils.PushIotUtils;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/8 0008 17:39
 * Describe
 */
public class PushIot {

    public static String TAG = "PushIot";
    private static PushIot pushIot;
    private String alias = null;
    public static Gson gson;
    public static Context mContext;


    /**
     * 单例模式
     *
     * @return
     */
    public static PushIot getInstance() {
        if (pushIot == null) {
            pushIot = new PushIot();
        }
        return pushIot;
    }

    /**
     * 初始化连接服务器
     *
     * @param appKey
     * @param masterSecret
     */
    public void initPushIot(Context context, String appKey, String masterSecret, final PushIotIm iotIm) {
        this.mContext = context;
        Hawk.init(context).build();
        gson = new Gson();
        Hawk.put("appKey", appKey);
        Hawk.put("masterSecret", masterSecret);
        Hawk.put("alias", "");
        setLogDebug(true);
        if (PushIotUtils.isEmpty(appKey) || PushIotUtils.isEmpty(masterSecret)) {
            new PushIotError(PushIotError.KEY_SECRET_ERROR_CODE, "appKey或masterSecret为空");
            return;
        }


        //启动一个线程连接socket服务
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    new SimpleClient().connect(HttpApis.HOST, HttpApis.PORT, iotIm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 设置别名(不能为空)
     *
     * @param alias
     */
    public void setPushIotAlias(String alias) {
        this.alias = alias;
        if (PushIotUtils.isEmpty(alias)) {
            new PushIotError(PushIotError.ALIAS_ERROR_CODE, "别名为空");
            return;
        }
        Hawk.put("alias", alias);


    }


    /**
     * 发送消息
     *
     * @param msg
     * @param iotMsgIm
     */
    public void sendMsg(String msg, PushIotMsgIm iotMsgIm) {
        SimpleClientHandler handler = SimpleClientHandler.getSimpleClientHandler();
        if (handler == null) {
            new PushIotError(PushIotError.MSG_ERROR_CODE, "消息发送失败，连接异常001！");
            return;
        }
        SimpleClientHandler.getSimpleClientHandler().sendMsg(GsonUtil.strToJson(new MsgInfo(MsgInfo.US_MSG_CODE, MsgInfo.US_MSG_CODE_MG, msg)), iotMsgIm);
    }


    /**
     * 清除别名
     */
    public void clearPushIotAlias() {
        this.alias = "";
        Hawk.put("alias", alias);
    }

    //初始化上报数据
    private void initSerialSend() {
        //数据实例：{"alias":"","appKey":"35fcc64616654e2bbeb2cac7532c25d7","masterSecret":"7f6c71f9c6dc42d8b11512c68936e6b5","msgId":"a5de2c46bc79404b9cd263db49914505"}
//        SocketClient.writeBuff(PushIotUtils.str2Str(new Gson().toJson(new PushIotInfo(appKey, masterSecret, alias, PushIotUtils.get32UUID()))));
    }


    /**
     * 日志输出 调试
     *
     * @param isDebug 默认调试模式
     */
    public void setLogDebug(boolean isDebug) {
        Logger.init(TAG)
                .methodCount(3)
                .logLevel(isDebug ? LogLevel.FULL : LogLevel.NONE)
                .methodOffset(2);
    }
}