package com.utte.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.utte.android.update.ApkUtils;
import com.utte.android.update.BsPatch;
import com.utte.android.update.Constants;

import java.io.File;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_STORAGE = 0x01;
    private NumberFormat numberFormat;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(MainActivity.this, "正在进行省流量更新", Toast.LENGTH_SHORT).show();
                    ApkUtils.installApk(MainActivity.this, Constants.NEW_APK_PATH);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        numberFormat = NumberFormat.getPercentInstance();
//        numberFormat.setMinimumFractionDigits(2);
//
//        checkSDCardPermission();

        Button button = findViewById(R.id.start_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patch();
            }
        });

    }

    public void patch() {
        String oldApk = ApkUtils.getOriginalApk(this);
        String newApk = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "newApk.apk";
        String patchFile = "/storage/sdcard0/p.patch";
        BsPatch.patch(oldApk, patchFile, newApk);
        ApkUtils.install(this, newApk);
    }


    /*
    String oldfile = ApkUtils.getSourceApkPath(MainActivity.this, getPackageName());
     * String newfile = Constants.NEW_APK_PATH;
     * String patchfile = Constants.SD_CARD + File.separator + Constants.PATCH_FILE;
     */
//    public void start(View view) {
////        String oldApk = ApkUtils.getOriginalApk(this);
//        String oldApk = ApkUtils.getSourceApkPath(MainActivity.this, getPackageName());
//        String patchFile = Constants.SD_CARD + File.separator + Constants.PATCH_FILE;
//        String newApk = Constants.NEW_APK_PATH;
//        new BsPatch();
//        BsPatch.patch(oldApk, patchFile, newApk);
//        ApkUtils.install(this, newApk);
//
//    }

    /**
     * 检查SD卡权限
     */
    protected void checkSDCardPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
        } else {
            fileDownload();
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_PERMISSION_STORAGE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //获取权限
//                fileDownload();
//            } else {
//                Toast.makeText(getApplicationContext(), "权限被禁止，无法下载文件！", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    /**
     * 网络请求下载文件
     *
     * 从URL_PATCH_DOWNLOAD
     * 到Constants.SD_CARD + Constants.PATCH_FILE
     *
     * String oldfile = ApkUtils.getSourceApkPath(MainActivity.this, getPackageName());
     * String newfile = Constants.NEW_APK_PATH;
     * String patchfile = Constants.SD_CARD + File.separator + Constants.PATCH_FILE;
     * BsPatch.patch(oldfile, newfile, patchfile);
     * mHandler.sendEmptyMessage(0);
     *
     */
    public void fileDownload() {

    }

}
