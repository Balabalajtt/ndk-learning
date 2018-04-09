package com.utte.android.update;

import android.os.Environment;

import java.io.File;

/**
 * Created by 江婷婷 on 2018/3/19.
 */

public class Constants {
    public static final String PATCH_FILE = "apk.patch";

    public static final String URL_PATCH_DOWNLOAD = "http://127.0.0.1:8080/App_Update_Web/patchfile/" + PATCH_FILE;

    public static final String PACKAGE_NAME = "com.utte.android.update";

    public static final String SD_CARD = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static final String NEW_APK_NAME = "dn_apk_new.apk";

    public static final String NEW_APK_PATH = SD_CARD + File.separator + "dn_apk_new.apk";

    public static final String PATCH_FILE_PATH = SD_CARD + PATCH_FILE;

}
