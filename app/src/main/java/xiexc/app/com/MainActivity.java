package xiexc.app.com;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.xxcpush.event.PushIotMsgIm;
import com.app.xxcpush.init.PushIot;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity extends AppCompatActivity {

    private EditText mEtMsg = null;
    private TextView mTvMsg = null;
    private ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        mEtMsg = findViewById(R.id.et_msg);
        mTvMsg = findViewById(R.id.tv_msg);
        mTvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
    }


    public void send(View view) {
        String msg = mEtMsg.getText().toString().trim();
        sendMsg(msg);
    }

    @Subscribe
    public void getMsg(Object msg) {
        mTvMsg.append(msg.toString() + "\n");
        int offset = mTvMsg.getLineCount() * mTvMsg.getLineHeight();
        if (offset > mTvMsg.getHeight()) {
            mTvMsg.scrollTo(0, offset - mTvMsg.getHeight());
        }
    }


    private void sendMsg(String msg) {
        PushIot.getInstance().sendMsg(msg, new PushIotMsgIm() {
            @Override
            public void sendMsg() {
                showDialog();
            }

            @Override
            public void sendProgress(int max, int progress) {

            }

            @Override
            public void sendFinish(boolean success) {
                dissDialog();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    /**
     * 显示弹窗
     */
    private void showDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setTitle("提示");
            dialog.setMessage("消息发送中，请稍后...");
        }
        dialog.show();
    }

    /**
     * 关闭弹窗
     */
    private void dissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);

    }
}
