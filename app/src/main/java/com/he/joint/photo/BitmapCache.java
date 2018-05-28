package com.he.joint.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.he.joint.R;
import com.he.joint.mgr.ThreadPoolManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;


public class BitmapCache  {

	public Handler h = new Handler();
	public final String TAG = getClass().getSimpleName();
	private HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
	
	
	public void put(String path, Bitmap bmp) {
		if (!TextUtils.isEmpty(path) && bmp != null) {
			imageCache.put(path, new SoftReference<Bitmap>(bmp));
		}
	}

	public void displayBmp(final Context mContext,final ImageView iv, final String thumbPath,
						   final String sourcePath, final ImageCallback callback) {
		if (TextUtils.isEmpty(thumbPath) && TextUtils.isEmpty(sourcePath)) {
			Log.e(TAG, "no paths pass in");
			return;
		}

		final String path;
		final boolean isThumbPath;
		if (!TextUtils.isEmpty(thumbPath)) {
			path = thumbPath;
			isThumbPath = true;
		} else if (!TextUtils.isEmpty(sourcePath)) {
			path = sourcePath;
			isThumbPath = false;
		} else {
			// iv.setImageBitmap(null);
			return;
		}

		if (imageCache.containsKey(path)) {
			SoftReference<Bitmap> reference = imageCache.get(path);
			Bitmap bmp = reference.get();
			if (bmp != null) {
				if (callback != null) {
					callback.imageLoad(iv, bmp, sourcePath);
				}
				iv.setImageBitmap(bmp);
				Log.d(TAG, "hit cache");
				return;
			}
		}
		iv.setImageBitmap(null);

		ThreadPoolManager.getInstance().execute(new Runnable() {

			Bitmap thumb;

			@Override
			public void run() {
				try {
					if (isThumbPath) {
						thumb = BitmapFactory.decodeFile(thumbPath);
//						thumb = revitionImageSize(thumbPath);
						if (thumb == null) {
							thumb = revitionImageSize(sourcePath);
						}
					} else {
//						MyLog.e("-------------------------------------------" + sourcePath);
						thumb = revitionImageSize(sourcePath);
					}
				} catch (Exception e) {

				}
				if (thumb == null) {
					thumb = BitmapFactory.decodeResource(
							mContext.getApplicationContext().getResources(),
							R.drawable.pinglun_tianjia);
				}
				//Log.e(TAG, "-------thumb------"+thumb);
				put(path, thumb);

				if (callback != null) {
					h.post(new Runnable() {
						@Override
						public void run() {
							callback.imageLoad(iv, thumb, sourcePath);
						}
					});
				}
			}
		});
//		new Thread() {
//			Bitmap thumb;
//
//			public void run() {
//
//				try {
//
//					if (isThumbPath) {
//						thumb = BitmapFactory.decodeFile(thumbPath);
////						thumb = revitionImageSize(thumbPath);
//						if (thumb == null) {
//							thumb = revitionImageSize(sourcePath);
//						}
//					} else {
//						MyLog.e("-------------------------------------------" + sourcePath);
//						thumb = revitionImageSize(sourcePath);
//					}
//				} catch (Exception e) {
//
//				}
//				if (thumb == null) {
//					thumb = BitmapFactory.decodeResource(
//							RRUUApp.getInstance().getResources(),
//							R.drawable.pinglun_tianjia);
//				}
//				Log.e(TAG, "-------thumb------"+thumb);
//				put(path, thumb);
//
//				if (callback != null) {
//					h.post(new Runnable() {
//						@Override
//						public void run() {
//							callback.imageLoad(iv, thumb, sourcePath);
//						}
//					});
//				}
//			}
//		}.start();

	}

	public Bitmap revitionImageSize(String path) throws IOException {
		FileInputStream in = new FileInputStream(
				path);
		//InputStream in = RRUUApp.getInstance().getApplicationContext().getContentResolver().openInputStream(Uri.parse("content:/"+path));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 256)
					&& (options.outHeight >> i <= 256)) {
//				in = new BufferedInputStream(
//						new FileInputStream(new File(path)));
				in = new FileInputStream(path);
				//in = RRUUApp.getInstance().getApplicationContext().getContentResolver().openInputStream(Uri.parse("content:/"+path));
//				MyLog.e("------------------------------------ " + i);
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				options.inPreferredConfig = Bitmap.Config.RGB_565;
				options.inPurgeable = true;
//				MyLog.e("--------------------1---------------- " + System.currentTimeMillis());
						bitmap = BitmapFactory.decodeStream(in, null, options);
//				MyLog.e("--------------------2---------------- " + System.currentTimeMillis());
				break;
			}
			i += 1;
		}
		return bitmap;
	}

	public interface ImageCallback {
		public void imageLoad(ImageView imageView, Bitmap bitmap,
				Object... params);
	}
}
