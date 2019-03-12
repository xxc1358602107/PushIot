package xiexc.app.com;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.app.xxcpush.event.PushIotMsgIm;
import com.app.xxcpush.init.PushIot;


public class MainActivity extends AppCompatActivity {

    private EditText mEtMsg = null;
    private ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtMsg = findViewById(R.id.et_msg);
    }


    public void send(View view) {
        String msg = mEtMsg.getText().toString().trim();
        sendMsg(msg);
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

}
