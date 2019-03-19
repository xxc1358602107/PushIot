package com.app.xxcpush.netty;

import android.os.Handler;
import android.os.Message;

import com.app.xxcpush.entity.GetMsgInfo;
import com.app.xxcpush.entity.PushIotInfo;
import com.app.xxcpush.entity.PushMsgInfo;
import com.app.xxcpush.error.PushInfoException;
import com.app.xxcpush.error.PushIotError;
import com.app.xxcpush.event.PushIotIm;
import com.app.xxcpush.event.PushIotMsgIm;
import com.app.xxcpush.init.PushIot;
import com.app.xxcpush.utils.GsonUtil;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    private static SimpleClientHandler handler;
    private ChannelHandlerContext ctx;
    private InetSocketAddress socketAddress;
    private PushIotIm iotIm;
    private boolean isConnect = false;

//    /**
//     * 超时计时
//     */
//    Handler timeHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (!isConnect) {
//                iotIm.timeoutConnect();
//                if (ctx != null)
//                    ctx.close();
//                Logger.v("SDK连接超时...开始自动尝试连接...");
//                PushIot.getInstance().sdkInit();//尝试重连SDK
//            }
//        }
//    };

    public static SimpleClientHandler getSimpleClientHandler() {
        return handler;
    }

    public SimpleClientHandler(InetSocketAddress socketAddress, final PushIotIm iotIm) {
        handler = this;
        this.socketAddress = socketAddress;
        this.iotIm = iotIm;
        this.iotIm.initConnect();
        isConnect = false;
        Logger.v("SDK初始化...");
//        timeHandler.sendEmptyMessageDelayed(0, 3000);
    }


    /**
     * 读取到消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.ctx = ctx;
        iotIm.listenMsg(readMsg(msg));
    }

    /**
     * 连接异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        this.ctx = ctx;
        cause.printStackTrace();
        isConnect = false;
        ctx.close();
        isConnect = false;
        iotIm.exitConnect(cause);
        Logger.e("SDK连接异常...");
        PushIot.getInstance().sdkInit();
    }


    /**
     * 连接成功
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        isConnect = true;
        String appKey = Hawk.get("appKey", "");
        String alias = Hawk.get("alias", "");
        //连接成功发送初始化信息
        String initMsg = GsonUtil.strToJson(new PushMsgInfo(PushMsgInfo.SY_MSG_CODE, PushMsgInfo.SY_MSG_CODE_MG, GsonUtil.strToJson(new PushIotInfo(appKey, alias))));
        sendMsg(initMsg, null);
        isConnect = true;
        iotIm.succeedConnect();
        Logger.i("SDK连接成功...");
    }


    /**
     * 读取消息
     *
     * @param msg
     * @return
     */
    public Object readMsg(Object msg) {
        String str = "";
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        try {
            result.readBytes(result1);
            //TODO 本地Windows调试GBK
//            str = new String(result1, "GBK");
            str = new String(result1, "UTF-8");
            //解析消息，有异常断开连接
            GetMsgInfo info = GsonUtil.jsonToClass(str);
            if (info != null) {
                if (info.getType() == GetMsgInfo.US_PACKAGE_CODE) {
                    ctx.close();
                    new PushInfoException(PushIotError.APP_BREAK_ERROR);
                    new PushInfoException(PushIotError.APP_BACKAGE_ERROR);
                } else if (info.getType() == GetMsgInfo.US_KEY_CODE) {
                    ctx.close();
                    new PushInfoException(PushIotError.APP_BREAK_ERROR);
                    new PushInfoException(PushIotError.APP_KEY_ERROR_1);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 发送消息
     *
     * @param msg
     * @param iotMsgIm
     */
    public void sendMsg(String msg, PushIotMsgIm iotMsgIm) {
        if (ctx == null) {
            new PushInfoException(PushIotError.APP_MSG_ERROR_2);
            return;
        }
        try {
            if (iotMsgIm != null)
                iotMsgIm.sendMsg();
            ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
            encoded.writeBytes(msg.getBytes());
            ctx.write(encoded);
            ctx.flush();
//            iotMsgIm.sendProgress(0,0);
            if (iotMsgIm != null)
                iotMsgIm.sendFinish(true);
        } catch (Exception e) {
            if (iotMsgIm != null) {
                iotMsgIm.sendFinish(false);
                iotMsgIm.onError(e);
                new PushInfoException(PushIotError.APP_MSG_ERROR_3);
                return;
            }
        }
    }

}
