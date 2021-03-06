package com.app.xxcpush.init;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.app.xxcpush.api.HttpApis;
import com.app.xxcpush.entity.PushMsgInfo;
import com.app.xxcpush.error.PushInfoException;
import com.app.xxcpush.error.PushIotError;
import com.app.xxcpush.event.PushIotIm;
import com.app.xxcpush.event.PushIotMsgIm;
import com.app.xxcpush.netty.SimpleClient;
import com.app.xxcpush.netty.SimpleClientHandler;
import com.app.xxcpush.receiver.NetworkReceiver;
import com.app.xxcpush.utils.GsonUtil;
import com.app.xxcpush.utils.PushIotUtils;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import es.dmoral.toasty.MyToast;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/8 0008 17:39
 * Describe
 */
public class PushIot implements NetworkReceiver.NetEvent {

    public static String TAG = "PushIot";
    private static PushIot pushIot;
    private String alias = null;
    public static Gson gson;
    public static NetworkReceiver.NetEvent event;
    public static Context mContext;
    private static PushIotIm iotIm;


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
     */
    public void initPushIot(Context context, String appKey, final PushIotIm iotIm) {
        this.mContext = context;
        this.iotIm = iotIm;
        Hawk.init(context).build();
        gson = new Gson();
        Hawk.put("appKey", appKey);
        Hawk.put("alias", "");
        setLogDebug(true);
        MyToast.init(PushIot.mContext, false, true);
        if (PushIotUtils.isEmpty(appKey)) {
            new PushInfoException(PushIotError.APP_KEY_ERROR);
            return;
        }
        event = this;
        receiverInit();

    }

    /**
     * SDK初始化连接
     */
    public synchronized void sdkInit() {
        if (!PushIotUtils.isNetworkConnected()) {
            Logger.e("当前网络无连接，请检查设备网络连接状态...");
            return;
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    //启动一个线程连接SDK服务
                    new SimpleClient().connect(HttpApis.HOST, HttpApis.PORT, iotIm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 网络状态监听广播注册
     */
    private void receiverInit() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        PushIot.mContext.registerReceiver(new NetworkReceiver(), filter);
    }

    /**
     * 设置别名
     *
     * @param alias
     */
    public void setPushIotAlias(String alias) {
        this.alias = alias;
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
            new PushInfoException(PushIotError.APP_MSG_ERROR_1);
            return;
        }
        SimpleClientHandler.getSimpleClientHandler().sendMsg(GsonUtil.strToJson(new PushMsgInfo(PushMsgInfo.US_MSG_CODE, PushMsgInfo.US_MSG_CODE_MG, msg)), iotMsgIm);
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
        //数据实例：{"alias":"","appKey":"35fcc64616654e2bbeb2cac7532c25d7","msgId":"a5de2c46bc79404b9cd263db49914505"}
//        SocketClient.writeBuff(PushIotUtils.str2Str(new Gson().toJson(new PushIotInfo(appKey,  alias, PushIotUtils.get32UUID()))));
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


    /**
     * 网络状态发生变化
     *
     * @param
     */
    @Override
    public void onNetChange() {
        if (PushIotUtils.isFast())
            return;
        sdkInit();
    }
}
