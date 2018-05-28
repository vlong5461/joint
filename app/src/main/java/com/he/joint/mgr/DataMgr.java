package com.he.joint.mgr;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.he.joint.R;
import com.he.joint.common.Consts;
import com.he.joint.common.OpenUDID;
import com.he.joint.common.PreferenceHelper;
import com.he.joint.common.URLHelper;
import com.he.joint.utils.FileUtils;
import com.he.joint.utils.ToastUtils;
import com.nostra13.universalimageloader.cache.disc.impl.BaseDiscCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.third.grant.PermissionsManager;
import com.third.grant.PermissionsResultAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataMgr {

	private static DataMgr instance = null;
	public Context context = null;
	public static float screenDensity;
	public static int screenHeight;
	public static int screenWidth;
	public static List<Activity> activityList = null;
	public static int networkType;
	public static DisplayImageOptions options, options_round;
	public static String MY_IMAGE_PATH;
	public static boolean isActive;
	public static String channel;
	public static String versionName = ""; 
	public static String outPhone="";
	public static boolean isInitXiaoneng;
	public static boolean isInitXinge;
	public static String msgcontent="";
	public static int messagecount=0;
	public static int travel_count=0;  //出行通知条数
	public static String travel_notice_flag="0"; //有新凭证标记	0-没有，1-有
	public static boolean isShowTravel =false; //凭证是否显示小红点
	public static boolean isRefreshTravel =false; //凭证是否需要刷新
	public static boolean isZhuangtai=false;
	public static boolean isChuxing=false;
	public static String Bind_Phone="";
	public static String XiaoNeng_ID="";
	public static String XiaoNeng_Name="";
	public static List<Map<String, Object>> settinginfolist;

	public static String cacheDataDir;

	public static boolean needScrollToPintForTravelList = true;


	private DataMgr() {

	}

	public static DataMgr getInstance() {
		if (instance == null) {
			instance = new DataMgr();
			activityList = new ArrayList<Activity>();
			settinginfolist = new ArrayList<Map<String,Object>>();
		}
		return instance;
	}

	public void initData(Context context) {
		this.context = context;

		isActive = true;
		isInitXiaoneng =false;
		isInitXinge=false;
		
		initParameter();
		initPreference();

		initImageLoader();
		FileUtils.initData(context);
		getNetWorkType(context);
		channel=getPhoneChannel(context);
		versionName=getAppVersionName(context);
	}

	public static boolean checkPermission(Context context, String permission) {
    boolean result = false;
    if (Build.VERSION.SDK_INT >= 23) {
        try {
            Class clazz = Class.forName("android.content.Context");
            Method method = clazz.getMethod("checkSelfPermission", String.class);
            int rest = (Integer) method.invoke(context, permission);
            if (rest == PackageManager.PERMISSION_GRANTED) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            result = false;
        }
    } else {
        PackageManager pm = context.getPackageManager();
        if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            result = true;
        }
    }
    return result;
}
public static String getDeviceInfo(Context context) {
    try {
        org.json.JSONObject json = new org.json.JSONObject();
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = null;
        if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            device_id = tm.getDeviceId();
        }
        String mac = null;
        FileReader fstream = null;
        try {
            fstream = new FileReader("/sys/class/net/wlan0/address");
        } catch (FileNotFoundException e) {
            fstream = new FileReader("/sys/class/net/eth0/address");
        }
        BufferedReader in = null;
        if (fstream != null) {
            try {
                in = new BufferedReader(fstream, 1024);
                mac = in.readLine();
            } catch (IOException e) {
            } finally {
                if (fstream != null) {
                    try {
                        fstream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        json.put("mac", mac);
        if (TextUtils.isEmpty(device_id)) {
            device_id = mac;
        }
        if (TextUtils.isEmpty(device_id)) {
            device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
        }
        json.put("device_id", device_id);
        return json.toString();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

	private void initParameter() {
//		Resources resources = context.getResources();
//		DisplayMetrics dm = resources.getDisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		screenDensity = dm.density;
		screenHeight = dm.heightPixels;
		screenWidth = dm.widthPixels;
	}

	public void initScreenParameter(Context ctx) {

		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		screenDensity = dm.density;
		screenHeight = dm.heightPixels;
		screenWidth = dm.widthPixels;

	}

	private void initPreference() {
		PreferenceHelper.getSharedPreferences(context);
		PreferenceHelper.getEditor(context);
	}

	private void initImageLoader() {
		MY_IMAGE_PATH = context.getFilesDir().getParent() + "/myImage/";
		File file = new File(MY_IMAGE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPoolSize(10)
				.threadPriority(Thread.NORM_PRIORITY).denyCacheImageMultipleSizesInMemory()
				.diskCacheSize(500 * 1024 * 1024).diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCache(new UnlimitedDiscCache(file)).tasksProcessingOrder(QueueProcessingType.LIFO)
				.memoryCache(new UsingFreqLimitedMemoryCache(20*1024*1024))
				.imageDownloader(new BaseImageDownloader(context, 15 * 1000, 30 * 1000)).build();
		ImageLoader.getInstance().init(config);
		options = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).bitmapConfig(Bitmap.Config.RGB_565)
				.showImageOnLoading(R.drawable.moren2)
				.showImageForEmptyUri(R.drawable.moren2)
				.showImageOnFail(R.drawable.moren2)
				.resetViewBeforeLoading(true).displayer(new SimpleBitmapDisplayer()).build();
		options_round = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).bitmapConfig(Bitmap.Config.RGB_565)
				.showImageOnLoading(R.drawable.moren2)
				.showImageForEmptyUri(R.drawable.moren2)
				.showImageOnFail(R.drawable.moren2)
				.resetViewBeforeLoading(true).displayer(new RoundedBitmapDisplayer(dip2px(5))).build();
	}

	public static boolean checkImageOnDisk(String url) {
		File file = new File(MY_IMAGE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		BaseDiscCache cache = new BaseDiscCache(file) {
			@Override
			protected File getFile(String imageUri) {
				return super.getFile(imageUri);
			}
		};
		File image = DiskCacheUtils.findInCache(url, cache);
		return image != null ? true : false;
	}

	public void clearCache() {
		ImageLoader.getInstance().clearDiskCache();
		File file = new File(FileUtils.FeedbackImagePath);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				tempList[i].delete();
			}
		}

		File file2 = new File(FileUtils.fileRoot);
		File[] tempList2 = file.listFiles();
		for (int i = 0; i < tempList2.length; i++) {
			if (tempList2[i].isFile()) {
				tempList2[i].delete();
			}
		}
	}

	public String getCacheSize() throws Exception{
		long size = 0;
		File file = new File(MY_IMAGE_PATH);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				size += getFileSize(tempList[i]);
			}
		}
		File file1 = new File(FileUtils.FeedbackImagePath);
		File[] tempList1 = file1.listFiles();
		for (int i = 0; i < tempList1.length; i++) {
			if (tempList1[i].isFile()) {
				size += getFileSize(tempList1[i]);
			}
		}
		if(size>0){
			return FormetFileSize(size);
		}else{
			return "";
		}
	}
	
	private static long getFileSize(File file) throws Exception {
		  long size = 0;
		  if (file.exists()) {
			  FileInputStream fis = null;
			  fis = new FileInputStream(file);
			  size = fis.available();
			  fis.close();
		  } else {
			  file.createNewFile();
		  }
		  return size;
	}
	 private static String FormetFileSize(long fileS) {
		  DecimalFormat df = new DecimalFormat("#.00");
		  String fileSizeString = "";
		  String wrongSize = "0B";
		  if (fileS == 0) {
		   return wrongSize;
		  }
		  if (fileS < 1024) {
		   fileSizeString = df.format((double) fileS) + "B";
		  } else if (fileS < 1048576) {
		   fileSizeString = df.format((double) fileS / 1024) + "KB";
		  } else if (fileS < 1073741824) {
		   fileSizeString = df.format((double) fileS / 1048576) + "MB";
		  } else {
		   fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		  }
		  return fileSizeString;
	}
	 
	public static boolean isNetworkAvailable(Context mContext) {
		boolean flag = false;
		ConnectivityManager cm = (ConnectivityManager) mContext.getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			flag = false;
		} else {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						flag = true;
						break;
					}
				}
			}
		}
		//MyLog.i("======= network is available : " + flag);
		return flag;
	}

	@SuppressLint("DefaultLocale")
	public static int getNetWorkType(Context mContext) {
		int type = Consts.NetworkType.NetworkType_Non;
		ConnectivityManager cm = (ConnectivityManager) mContext.getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						if (info[i].getTypeName().toUpperCase().equals("WIFI")) {
							type = Consts.NetworkType.NetworkType_Wifi;
							break;
						} else {
							type = Consts.NetworkType.NetworkType_2G3G;
						}
					}
				}
			}
		}
		networkType = type;
		return type;
	}

	public String getUUID() {

//		return Installation.getUUid();
		String uid = OpenUDID.getOpenUDID();
		 return uid;
	}
	
	public static int dip2px(float dipValue) {
		return (int) (dipValue * screenDensity + 0.5f);
	}

	public static int px2dip(float pxValue) {
		return (int) (pxValue / screenDensity + 0.5f);
	}

	// 获取当前版本号
	private String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			versionName = packageInfo.versionName;
			if (TextUtils.isEmpty(versionName)) {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}
	
	private String getPhoneChannel(Context context){
		String channel="";
		ApplicationInfo appInfo = null;
		try {
			appInfo = context.getPackageManager()
			        .getApplicationInfo(context.getPackageName(),
			PackageManager.GET_META_DATA);
			if(appInfo!=null){
				 channel=appInfo.metaData.getString("UMENG_CHANNEL");
				 if(channel!=null){
					 channel=channel.substring(1);
				 }
			 }
			return channel;
		} catch (PackageManager.NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return channel;
		}

	}
	
	public static void setPermissionWriteExternalStorage(Context mContext){
		boolean hasPermiss = PermissionsManager.getInstance().hasPermission((Activity) mContext,
				Manifest.permission.WRITE_EXTERNAL_STORAGE);
		if (!hasPermiss) {
			ToastUtils.show(mContext, "请打开应用的存储权限（设置->应用->任游->权限）");
		}
		PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult((Activity) mContext,
				new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, new PermissionsResultAction() {
					@Override
					public void onGranted() {
						// TODO Auto-generated method stub
					}

					@Override
					public void onDenied(String permission) {
						// TODO Auto-generated method stub
					}
				});
	}
	public static void setPermissionAccessCoarseLocation(Context mContext){
		boolean hasPermiss = PermissionsManager.getInstance().hasPermission((Activity) mContext,
				Manifest.permission.ACCESS_COARSE_LOCATION);
		if (!hasPermiss) {
			ToastUtils.show(mContext, "请打开应用的位置权限（设置->应用->任游->权限）");
		}
		PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult((Activity) mContext,
				new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, new PermissionsResultAction() {
					@Override
					public void onGranted() {
						// TODO Auto-generated method stub
					}

					@Override
					public void onDenied(String permission) {
						// TODO Auto-generated method stub
					}
				});
	}

	public boolean CheckLocationCorrect(String location){
		boolean isCorrect = false;
		String[] arrayLocation= location.split(";");
		for(int i=0;i<arrayLocation.length;i++) {
			String[] loc = arrayLocation[i].split("[,|，]");
			double aLat, aLong;
			if (loc != null && loc.length == 2) {
				try {
					aLat = Double.valueOf(loc[0]);
					aLong = Double.valueOf(loc[1]);
				} catch (Exception e) {
					aLat = 200;
					aLong = 200;
				}
				if (Math.abs(aLat) <= 90 && Math.abs(aLong) <= 180) {
					isCorrect = true;
				}
			}
		}
		return isCorrect;
	}
	public static boolean isEmail(String email){
		//String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		String str="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		boolean is = m.matches();
		return is;
	}
	public static Map<String, String> getEventLabelMap() {
		Map<String,String> map = new HashMap<>();
		String selectedCountryName = (String) PreferenceHelper.getFromSharedPreferences(Consts.Selected_Country_Name, String.class.getName());
		if (selectedCountryName == null) {
			selectedCountryName = "";
		}
		map.put("CountryName", selectedCountryName);
		map.put("userId", "");
		map.put("deviceId", DataMgr.getInstance().getUUID());
		return map;
	}
	public static Map<String, String> getEventLabelMap(String title) {
		Map<String,String> map = new HashMap<>();
		String selectedCountryName = (String) PreferenceHelper.getFromSharedPreferences(Consts.Selected_Country_Name, String.class.getName());
		if (selectedCountryName == null) {
			selectedCountryName = "";
		}
		map.put("CountryName", selectedCountryName);
		map.put("userId","");
		map.put("deviceId", DataMgr.getInstance().getUUID());
		map.put("Title", title);
		return map;
	}

}
