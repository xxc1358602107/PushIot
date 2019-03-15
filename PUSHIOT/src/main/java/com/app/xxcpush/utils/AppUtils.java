package com.app.xxcpush.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.app.xxcpush.init.PushIot;

import java.util.List;

/**
 * @Copyright 广州市数商云网络科技有限公司
 * @Author XXC
 * @Date 2019/3/15 0015 09:41
 * Describe
 */
public class AppUtils {

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName() {
        try {
            PackageManager packageManager = PushIot.mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    PushIot.mContext.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return PushIot.mContext.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName() {
        try {
            PackageManager packageManager = PushIot.mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    PushIot.mContext.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode() {
        try {
            PackageManager packageManager = PushIot.mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    PushIot.mContext.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName() {
        try {
            PackageManager packageManager = PushIot.mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    PushIot.mContext.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取图标 bitmap
     */
    public static synchronized Bitmap getBitmap() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = PushIot.mContext.getApplicationContext()
                    .getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                    PushIot.mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap bm = bd.getBitmap();
        return bm;
    }


    /**
     * 获取当前应用程序的包名
     *
     * @return 返回包名
     */
    public static String getAppProcessName() {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) PushIot.mContext.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }
}
