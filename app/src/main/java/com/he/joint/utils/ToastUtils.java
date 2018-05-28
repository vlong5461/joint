package com.he.joint.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	
	private static final int Default_Time = 1500;
	
	public static void show(Context ctx, String msg) {
		show(ctx, msg, Default_Time);
	}
	
	public static void show(Context ctx, String msg, int duration) {
		if (msg != null && msg.length() > 0) {
                Toast toast=Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
		}
	}

}
