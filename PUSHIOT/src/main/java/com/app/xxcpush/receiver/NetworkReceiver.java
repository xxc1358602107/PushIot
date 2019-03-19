package com.app.xxcpush.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.app.xxcpush.init.PushIot;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/18 0018 16:43
 * Describe
 */
public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            PushIot.event.onNetChange();
        }
    }

    // 网络状态变化
    public interface NetEvent {
        void onNetChange();
    }
}
