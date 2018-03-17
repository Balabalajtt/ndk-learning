package com.utte.fileseparatedemo;

/**
 * Created by 江婷婷 on 2018/3/17.
 */

public class NDKFileUtils {

    public native void diff(String path, String path_pattern, int count);

    public native void patch(String path_pattern, int count, String path);

    public NDKFileUtils() {
        System.loadLibrary("native-lib");
    }

}
