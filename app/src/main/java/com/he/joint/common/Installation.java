package com.he.joint.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

public class Installation {
    private static String sID = null;
    private static final String filePath = "/storage/sdcard0";
    private static final String filePath1 = "/storage/sdcard1";
    private static final String fPath = "/system";
    private static final String INSTALLATION = "/INSTALLATION";

    public synchronized static String getUUid() {
        if (sID == null) {  
        	String path = Environment.getExternalStorageDirectory().getPath();
        	makeRootDirectory(path+fPath);
        	if(!fileIsExists(path+fPath)){
        		path = filePath1;
        		makeRootDirectory(path+fPath);
        	}
            File installation = new File(path+fPath+INSTALLATION);
            try {
                if (!installation.exists()){
                	String uuid = UUID.randomUUID().toString();
                	saveToSDCard(path+fPath+INSTALLATION,uuid);
                }
                sID = readFromSD(path+fPath+INSTALLATION);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    public static void makeRootDirectory(String filePath) {
    	File file = null;
    	try {
    	      file = new File(filePath);
    	      if (!file.exists()) {
    	          boolean flag =  file.mkdir();
    	          System.out.println("XXXXXXXXXXXX " + flag);
    	      }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    //写入到SDCard的操作
    // save infomation in the SDCard
	public static boolean saveToSDCard(String fileName, String content) {

		// judge weather the SDCard exits,and can be read and written
//		if (!Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)) {
//			return false;
//		}

		FileOutputStream fileOutputStream = null;
		File file = new File(fileName);
		try {
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(content.getBytes());
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {

				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

//下面是读取位于SDCard根目录下文件的操作方法
// read the file in the SDCard
	public static String readFromSD(String fileName) {
		FileInputStream fileInputStream = null;
		File file = new File(fileName);
		try {
			fileInputStream = new FileInputStream(file);
			int len = 0;
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
			while ((len = fileInputStream.read(buffer)) != -1) {
				byteArrayInputStream.write(buffer, 0, len);
			}
			String string = new String(byteArrayInputStream.toByteArray());
			return string;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static boolean fileIsExists(String patch){
		 try{
			 File f=new File(patch);
			 if(!f.exists()){
				 return false;
			 }
		 }catch (Exception e) {
			 // TODO: handle exception
			 return false;
		 }
		 return true;
	}
	
	public static String getPhoneChannel(Context context){
		String channel="";
		ApplicationInfo appInfo = null;
		try {
			appInfo = context.getPackageManager()
			        .getApplicationInfo(context.getPackageName(),
			PackageManager.GET_META_DATA);
			if(appInfo!=null){
				 channel=appInfo.metaData.getString("UMENG_CHANNEL");
			 }
			return channel;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return channel;
		}	 
	}
}
