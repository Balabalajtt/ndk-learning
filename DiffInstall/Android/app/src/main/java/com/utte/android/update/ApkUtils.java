package com.utte.android.update;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by 江婷婷 on 2018/3/19.
 */

public class ApkUtils {
    public static boolean isInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return installed;
    }

    /**
     * 获取已安装Apk文件的源Apk文件
     * 如：/data/app/my.apk
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getSourceApkPath(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName))
            return null;

        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(packageName, 0);
            return appInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 安装Apk
     *
     * @param context
     * @param apkPath
     */
    public static void installApk(Context context, String apkPath) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkPath),
                "application/vnd.android.package-archive");

        context.startActivity(intent);
    }

    /**
     * 获取本身APK的路径 * * @param context * @return
     */
    public static String getOriginalApk(Context context) {
        return context.getApplicationInfo().sourceDir;
    }

    /**
     * 安装指定apk
     * @param context
     * @param apk
     */
    public static void install(Context context, String apk) {
        Intent intent = new Intent();
        intent.setDataAndType(Uri.fromFile(new File(apk)), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
