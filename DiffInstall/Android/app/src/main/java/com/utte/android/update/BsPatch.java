package com.utte.android.update;

/**
 * Created by 江婷婷 on 2018/3/19.
 */

public class BsPatch {
    public native static void patch(String oldfile,String newfile,String patchfile);

    static {
        System.loadLibrary("bspatch");
    }
}
