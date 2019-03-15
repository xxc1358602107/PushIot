package com.app.xxcpush.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.app.xxcpush.init.PushIot;
import com.orhanobut.hawk.Hawk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/11 0011 17:51
 * Describe
 */
public class FacilityUtil {

    private static String imei;
    private static String mac;

    /**
     * 获取设备IMEI
     *
     * @return
     */
    public static String getIMEI() {
        if (PushIotUtils.isEmpty(imei)) {
            TelephonyManager telephonyManager = (TelephonyManager) PushIot.mContext.getSystemService(Context.TELEPHONY_SERVICE);
            Class clazz = telephonyManager.getClass();
            Method getImei = null;
            String imei = "";
            try {
                getImei = clazz.getDeclaredMethod("getImei", int.class);//(int slotId)
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                imei = (String) getImei.invoke(telephonyManager, 0);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(imei)) {
                String saveImei = Hawk.get("IMEI");
                if (TextUtils.isEmpty(saveImei)) {
                    imei = Settings.Secure.getString(PushIot.mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                } else {
                    imei = saveImei;
                }
            } else {
                Hawk.put("IMEI", imei);
            }
            return imei;
        } else {
            return imei;
        }
    }


    /**
     * 获取MAC地址
     *
     * @return
     */
    public static String getMacAddress() {
        String mac = "02:00:00:00:00:00";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(PushIot.mContext);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware();
        }
        return mac;
    }


    /**
     * Android  6.0 之前（不包括6.0）
     * 必须的权限  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     *
     * @param context
     * @return
     */
    private static String getMacDefault(Context context) {
        String mac = "02:00:00:00:00:00";
        if (context == null) {
            return mac;
        }

        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
        }
        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }


    /**
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET" />
     *
     * @return
     */
    private static String getMacFromHardware() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }


}
