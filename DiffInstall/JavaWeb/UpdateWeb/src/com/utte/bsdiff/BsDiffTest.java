package com.utte.bsdiff;

import com.utte.bsdiff.ConstantWin;

public class BsDiffTest {
    public static void main(String[] args){
        //得到差分包
        BsDiff.diff(ConstantWin.OLD_APK_PATH,ConstantWin.NEW_APK_PATH,ConstantWin.PATCH_PATH);
    }
}
