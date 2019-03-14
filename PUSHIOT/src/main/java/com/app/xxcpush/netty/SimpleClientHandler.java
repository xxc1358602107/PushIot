package com.app.xxcpush.netty;

import com.app.xxcpush.api.HttpApis;
import com.app.xxcpush.entity.MsgInfo;
import com.app.xxcpush.entity.PushIotInfo;
import com.app.xxcpush.error.PushIotError;
import com.app.xxcpush.event.PushIotIm;
import com.app.xxcpush.event.PushIotMsgIm;
import com.app.xxcpush.utils.GsonUtil;
import com.orhanobut.hawk.Hawk;

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


    public static SimpleClientHandler getSimpleClientHandler() {
        return handler;
    }

    public SimpleClientHandler(InetSocketAddress socketAddress, PushIotIm iotIm) {
        handler = this;
        this.socketAddress = socketAddress;
        this.iotIm = iotIm;
        this.iotIm.initConnect();
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
        ctx.close();
//        reconnection();
        iotIm.exitConnect(cause);
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
        String appKey = Hawk.get("appKey", "");
        String masterSecret = Hawk.get("masterSecret", "");
        String alias = Hawk.get("alias", "");
        //连接成功发送初始化信息
        String initMsg = GsonUtil.strToJson(new MsgInfo(MsgInfo.SY_MSG_CODE, MsgInfo.SY_MSG_CODE_MG, GsonUtil.strToJson(new PushIotInfo(appKey, masterSecret, alias))));
        sendMsg(initMsg, null);
        iotIm.succeedConnect();
    }


    /**
     * 读取消息
     *
     * @param msg
     * @return
     */
    public Object readMsg(Object msg) {
        Object str = "";
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        try {
            result.readBytes(result1);
            str = new String(result1, "GBK");
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
            new PushIotError(PushIotError.MSG_ERROR_CODE, "消息发送失败，连接异常002！");
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
            if (iotMsgIm != null)
                iotMsgIm.sendFinish(false);
            if (iotMsgIm != null)
                iotMsgIm.onError(e);
            new PushIotError(PushIotError.MSG_ERROR_CODE, "消息发送失败，连接异常003！");
            return;
        }
    }


    /**
     * 重新连接
     */
    private void reconnection() {
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

}
