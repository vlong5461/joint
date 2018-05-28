package com.he.joint.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapHelper {

	private static int calculateInSampleSize(String filePath, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final float heightRatio = (float) height / (float) reqHeight;
			final float widthRatio = (float) width / (float) reqWidth;

			inSampleSize = heightRatio > widthRatio ? Math.round(heightRatio) : Math.round(widthRatio);
		}

		return inSampleSize;
	}

	private static Bitmap decodeBitmapFromFile(String filePath, int inSampleSize) {
		final BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = false;
		opt.inSampleSize = inSampleSize;

		Bitmap bm = null;
		OutOfMemoryError outOfMemoryError = null;

		try {
			bm = BitmapFactory.decodeFile(filePath, opt);
		} catch (OutOfMemoryError e) {
			outOfMemoryError = e;
			e.printStackTrace();
		} finally {
			if (outOfMemoryError != null || bm == null) {
				return null;
			}
		}

		return bm;
	}

	/**
	 * 解析指定大小的
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap decodeBitmap(String path, int width, int height) {
		int inSampleSize = calculateInSampleSize(path, width, height);

		return decodeBitmapFromFile(path, inSampleSize);
	}
	

}
