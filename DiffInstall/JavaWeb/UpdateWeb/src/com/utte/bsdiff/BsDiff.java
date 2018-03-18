package com.utte.bsdiff;

public class BsDiff {
    public native static void diff(String oldFilePath, String newFilePath, String patchFile);

    static {
        System.loadLibrary("bsdiff");
    }

}
