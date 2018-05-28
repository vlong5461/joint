package com.he.joint.utils;

/**
 * Created by luo_haogui on 2017/4/27.
 */

public class JniUtil {
    public static native String getSec();//.so中的方法名

    static{
        System.loadLibrary("rruu");//加载.so
    }
}
