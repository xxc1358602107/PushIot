package xiexc.app.com;

import android.app.Application;
import android.content.Context;

import com.app.xxcpush.event.PushIotIm;
import com.app.xxcpush.init.PushIot;
import com.app.xxcpush.utils.PushIotUtils;
import com.orhanobut.logger.Logger;

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

        PushIot.getInstance().initPushIot(this, "35fcc64616654e2bbeb2cac7532c25d7", new PushIotIm() {

            //初始化SDK开始连接
            @Override
            public void initConnect() {
            }

            //连接成功
            @Override
            public void succeedConnect() {
                PushIotUtils.showToast("连接成功");
            }

            @Override
            public void listenMsg(Object msg) {
                EventBus.getDefault().post(msg.toString());
            }

            //连接断开
            @Override
            public void exitConnect(Throwable cause) {
                //重连机制

            }

            //连接超时
            @Override
            public void timeoutConnect() {

            }
        });
        PushIot.getInstance().setPushIotAlias("xxc1");//设置别名
        PushIot.getInstance().setLogDebug(true);//设置是否显示日志
    }
}
