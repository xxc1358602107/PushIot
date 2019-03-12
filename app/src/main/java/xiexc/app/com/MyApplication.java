package xiexc.app.com;

import android.app.Application;

import com.app.xxcpush.event.PushIotIm;
import com.app.xxcpush.init.PushIot;


/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/6 0006 15:23
 * Describe
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PushIot.getInstance().initPushIot(this,"35fcc64616654e2bbeb2cac7532c25d7", "7f6c71f9c6dc42d8b11512c68936e6b5", new PushIotIm() {
            @Override
            public void initConnect() {

            }

            @Override
            public void succeedConnect() {

            }

            @Override
            public void listenMsg(Object msg) {

            }

            @Override
            public void exitConnect(Throwable cause) {

            }
        });
        PushIot.getInstance().setPushIotAlias("11");
        PushIot.getInstance().setLogDebug(true);
    }
}
