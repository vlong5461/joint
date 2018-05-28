package com.he.joint.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.provider.MediaStore;
import android.widget.ListView;
import android.widget.ScrollView;

import com.he.joint.utils.FileUtils;

public class ScreenShot {/**
	 * 截取scrollview的屏幕
	 * @param scrollView
	 * @return
	 */
	public static Bitmap getBitmapByView(ScrollView scrollView) {
		int h = 0;
		Bitmap bitmap = null;
		// 获取scrollview实际高度
		for (int i = 0; i < scrollView.getChildCount(); i++) {
			h += scrollView.getChildAt(i).getHeight();
			scrollView.getChildAt(i).setBackgroundColor(
					Color.parseColor("#ffffff"));
		}
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
				Bitmap.Config.RGB_565);
		final Canvas canvas = new Canvas(bitmap);
		scrollView.draw(canvas);
		return bitmap;
	}

	/**
     *  截图listview
     * **/
    public static Bitmap getListViewBitmap(ListView listView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取listView实际高度
        for (int i = 0; i < listView.getChildCount(); i++) {
            h += listView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(listView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        listView.draw(canvas);
 
        return bitmap;
    }
	/**
	 * 压缩图片
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
		while (baos.toByteArray().length / 1024 > 300) {
			// 重置baos
			baos.reset();
			// 这里压缩options%，把压缩后的数据存放到baos中
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			// 每次都减少10
			options -= 10;
		}
		// 把压缩后的数据baos存放到ByteArrayInputStream中
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		// 把ByteArrayInputStream数据生成图片
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		
		if (image != null && !image.isRecycled()) {
			image.recycle();
		}
		
		return bitmap;
	}

/**
	 * 保存到sdcard
	 * @param b
	 * @return
	 */
	public static String savePic(Context context,Bitmap b) {
		String path=FileUtils.getDiskFilesDir(context).getPath();
		File outfile = new File(path+"/image");
		// 如果文件不存在，则创建一个新文件
		if (!outfile.isDirectory()) {
			try {
				outfile.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String fname = outfile + "/" + "pic.png";
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fname);
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
			}
			if (b != null && !b.isRecycled()) {
				b.recycle();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fname;
	}
	
	public static String savePicToPhoto(Context context,Bitmap bitmap){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
				Locale.getDefault());
		String title=sdf.format(new Date());
		String url = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, title, "description");
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
		return url;
	}
	
	public static String savePicToPhotoForMIUI(Context context,Bitmap bitmap){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
				Locale.getDefault());
		String fileName=sdf.format(new Date()) + ".png";
		String path= FileUtils.getStorageDirectoryDir(context).getPath();
		File outfile = new File(path+"/cer");
		if (!outfile.isDirectory()) {
			try {
				outfile.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String fname = outfile + "/" + fileName;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fname);
			if (null != fos) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
			}
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return fname;
	}
	
	
}
