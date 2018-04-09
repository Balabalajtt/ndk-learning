package com.utte.androidupdate;

/**
 * Created by 江婷婷 on 2018/4/9.
 */

public class BsPatch {

    static {
        System.loadLibrary("native-lib");
    }

    public static native int bspatch(String oldApk, String newApk, String patch);

}
