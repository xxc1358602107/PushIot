package xiexc.app.com;

import android.app.Application;
import android.content.Context;

import com.app.xxcpush.event.PushIotIm;
import com.app.xxcpush.init.PushIot;
import com.app.xxcpush.utils.PushIotUtils;

import org.greenrobot.eventbus.EventBus;


/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/6 0006 15:23
 * Describe
 */
public class MyApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initPushSdk();
    }


    private void initPushSdk() {
        PushIot.getInstance().initPushIot(this, "35fcc64616654e2bbeb2cac7532c25d7", "7f6c71f9c6dc42d8b11512c68936e6b5", new PushIotIm() {
            @Override
            public void initConnect() {

            }

            @Override
            public void succeedConnect() {
                PushIotUtils.showToast("连接成功");
            }

            @Override
            public void listenMsg(Object msg) {
                EventBus.getDefault().post(msg.toString());
            }

            @Override
            public void exitConnect(Throwable cause) {
                //重连机制
                if (PushIotUtils.isNetworkConnected())
                    initPushSdk();
            }
        });
        PushIot.getInstance().setPushIotAlias("12");
        PushIot.getInstance().setLogDebug(true);
    }
}
