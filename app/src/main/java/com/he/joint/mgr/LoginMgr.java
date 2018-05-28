package com.he.joint.mgr;

import android.content.Context;

import com.he.joint.bean.UserInfoBean;
import com.he.joint.common.Consts;
import com.he.joint.common.PreferenceHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoginMgr {
	
	private static LoginMgr instance;
	
	private UserInfoBean userInfo;
	private String filePath;
	
	public static LoginMgr shareInstance() {
		if (instance == null) {
			instance = new LoginMgr();
		}
		return instance;
	}
	
	public boolean getLoginStatus() {
		if (userInfo != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void logout() {
		userInfo = null;

		File file = new File(filePath);
		if (file.exists()) {
			try {
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setUserInfo(UserInfoBean bean) {
		userInfo = bean;
		
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(filePath, false));
			out.writeObject(bean);  
			out.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		 
	}
	
	public void initData(Context ctx) {
		filePath = ctx.getFilesDir().getParent() + "/UserInfo.data";
		
		File file = new File(filePath);
		if (file.exists()) {
			ObjectInputStream in = null;
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(filePath);
				int length = fis.available();
				if (length > 0) {
					in = new ObjectInputStream(new FileInputStream(filePath));
					UserInfoBean user = (UserInfoBean) in.readObject();
					userInfo = user;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public UserInfoBean getUserInfo() {
		return userInfo;
	}
	
	public String getUserId() {
		if (userInfo != null) {
			return userInfo.uid;
		}
		return "";
	}
	
	public void saveTempUserId(int userid) {
		PreferenceHelper.saveToSharedPreferences(Consts.Temp_User_ID, userid);
	}
	
	private int getTempUserId() {
		int id = (Integer) PreferenceHelper.getFromSharedPreferences(Consts.Temp_User_ID,
				Integer.class.getName());
		return id;
	}
}
