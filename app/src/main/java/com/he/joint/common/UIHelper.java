package com.he.joint.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class UIHelper {
    
    public static void startActivity(Context fromClass, Class<?> toClass) {
        Intent intent = new Intent();
        intent.setClass(fromClass, toClass);
        fromClass.startActivity(intent);
    }
    
    public static void startActivity(Context fromClass, Class<?> toClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(fromClass, toClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        fromClass.startActivity(intent);
        bundle = null;
    }
    
    public static void startActivityForResult(Context fromClass, Class<?> toClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(fromClass, toClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        ((Activity)fromClass).startActivityForResult(intent, requestCode);
        bundle = null;
    }
    

}
