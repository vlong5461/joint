package com.he.joint.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

public class FileUtils {
	
	public static String FeedbackImagePath;

	public static String LocalPlaySelectCacheDataPath;
	public static String DelicacySelectCacheDataPath;
	public static String TrafficSelectCacheDataPath;
	public static String TourismSelectCacheDataPath;
	public static String TransferSelectCacheDataPath;
	public static String RentalSelectCacheDataPath;

	public static String fileRoot;
	
	public static void initData(Context context) {
		fileRoot = context.getFilesDir().getParent() + "/";
		FeedbackImagePath = context.getFilesDir().getParent() + "/feedbackImage/";
		File file = new File(FeedbackImagePath);
		if (!file.exists()) {
			file.mkdirs();
		}

		LocalPlaySelectCacheDataPath = context.getFilesDir().getParent() + "/LocalPlaySelectCacheData.data";
		DelicacySelectCacheDataPath = context.getFilesDir().getParent() + "/DelicacySelectCacheData.data";
		TrafficSelectCacheDataPath = context.getFilesDir().getParent() + "/TrafficSelectCacheData.data";
		TourismSelectCacheDataPath = context.getFilesDir().getParent() + "/TourismSelectCacheData.data";
		TransferSelectCacheDataPath = context.getFilesDir().getParent() + "/TransferSelectCacheData.data";
        RentalSelectCacheDataPath = context.getFilesDir().getParent() + "/RentalSelectCacheData.data";
	}
	
	public static File getDiskCacheDir(Context context) {  
	      File cachePath = null;  
	      if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	              || !Environment.isExternalStorageRemovable()) {  
	          cachePath = context.getApplicationContext().getExternalCacheDir();  
	      } else {  
	          cachePath = context.getApplicationContext().getCacheDir();  
	      }
	      if(cachePath==null){
	    	  cachePath = getDiskFilesDir(context);
	      }
	      if(cachePath==null){
	    	  cachePath =getStorageDirectoryDir(context);
	      }
	      return cachePath;  
	}  
	
	public static File getDiskFilesDir(Context context) {  
	      File cachePath = null;  
	      if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	              || !Environment.isExternalStorageRemovable()) {  
	          cachePath = context.getApplicationContext().getExternalFilesDir(null);  
	      } else {  
	          cachePath = context.getApplicationContext().getFilesDir();  
	      }  
	      if(cachePath==null){
	    	  cachePath =getStorageDirectoryDir(context);
	      }
	      return cachePath;  
	}  
	
	public static File getStorageDirectoryDir(Context context){
		File cachePath = null;  
		 if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	              || !Environment.isExternalStorageRemovable()) {
			 cachePath=Environment.getExternalStorageDirectory();
		 }else{
			 cachePath = context.getApplicationContext().getFilesDir(); 
		 }
		return cachePath; 
	}
	public static void copyFile(Uri uri, String toPath, Context ctx) {
		try {
			InputStream inputStream = ctx.getContentResolver().openInputStream(uri);
			BufferedInputStream inBuff = new BufferedInputStream(inputStream);
			BufferedOutputStream outBuff = null;
			try {
				outBuff = new BufferedOutputStream(new FileOutputStream(toPath));

				byte[] b = new byte[1024];
				int len;
				while ((len = inBuff.read(b)) != -1) {
					outBuff.write(b, 0, len);
				}

				outBuff.flush();
			} catch (Exception e) {

				e.printStackTrace();
			} finally {

				if (inBuff != null) {
					inBuff.close();
				}
				if (outBuff != null) {
					outBuff.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void delFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        file.exists();
    }

	public static void deleteAllFiles(File root) { 
        File files[] = root.listFiles();  
        if (files != null)  
            for (File f : files) {  
                if (f.isDirectory()) {
                    deleteAllFiles(f);  
                    try {  
                        f.delete();  
                    } catch (Exception e) {  
                    }  
                } else {  
                    if (f.exists()) {
                        deleteAllFiles(f);  
                        try {  
                            f.delete();  
                        } catch (Exception e) {  
                        }  
                    }  
                }  
            }  
    }  
}
