package com.he.joint.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.widget.TextView;

//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.entity.BufferedHttpEntity;
//import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MyImageGetter implements ImageGetter {

	private Context context;
	private TextView tv;
	private String content;

	public MyImageGetter(Context context, TextView tv) {
		this.context = context;
		this.tv = tv;
	}
	
	public MyImageGetter(Context context, TextView tv, String content) {
		this.context = context;
		this.tv = tv;
		this.content = content;
	}

	@Override
	public Drawable getDrawable(String source) {

		String sdcardPath = Environment.getExternalStorageDirectory()
				.toString()+"/temp_image"; // 获取SDCARD的路径
		if (!new File(sdcardPath).exists()) {
			new File(sdcardPath).mkdirs();
		}
		// 获取图片后缀名
		String[] ss = source.split("\\.");
		String ext = ss[ss.length - 1];

		// 最终图片保持的地址
		String savePath = sdcardPath + "/" + context.getPackageName() + "/"
				+ source + "." + ext;

		File file = new File(savePath);
		if (file.exists()) {
			// 如果文件已经存在，直接返回
			Drawable drawable = Drawable.createFromPath(savePath);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			return drawable;
		}

		// 不存在文件时返回默认图片，并异步加载网络图片
//		Resources res = context.getResources();
//		URLDrawable drawable = new URLDrawable(
//				res.getDrawable(R.drawable.transparent));
		
		
		new ImageAsync(null).execute(savePath, source);
		return null;
		
//		File file = ImageLoader.getInstance().getDiskCache().get(source);
//		if (file.exists()) {
//			Drawable drawable =new BitmapDrawable(ImageLoader.getInstance().loadImageSync(source));
//			return drawable;
//		} else {
//
//			ImageLoader.getInstance().loadImage(source, new ImageLoadingListener() {
//				
//				@Override
//				public void onLoadingStarted(String imageUri, View view) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void onLoadingCancelled(String imageUri, View view) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
//			
//			return null;
//		}

	}

	private class ImageAsync extends AsyncTask<String, Integer, Drawable> {

		private URLDrawable drawable;

		public ImageAsync(URLDrawable drawable) {
			this.drawable = drawable;
		}

		@Override
		protected Drawable doInBackground(String... params) {
			// TODO Auto-generated method stub
//			String savePath = params[0];
//			String url = params[1];
//
//			InputStream in = null;
//			try {
//				// 获取网络图片
//				HttpGet http = new HttpGet(url);
//				HttpClient client = new DefaultHttpClient();
//				HttpResponse response = (HttpResponse) client.execute(http);
//				BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(
//						response.getEntity());
//				in = bufferedHttpEntity.getContent();
//
//			} catch (Exception e) {
//				try {
//					if (in != null)
//						in.close();
//				} catch (Exception e2) {
//					// TODO: handle exception
//				}
//			}
//
//			if (in == null)
//				return drawable;
//
//			try {
//				File file = new File(savePath);
//				String basePath = file.getParent();
//				File basePathFile = new File(basePath);
//				if (!basePathFile.exists()) {
//					basePathFile.mkdirs();
//				}
//				file.createNewFile();
//				FileOutputStream fileout = new FileOutputStream(file);
//				byte[] buffer = new byte[4 * 1024];
//				while (in.read(buffer) != -1) {
//					fileout.write(buffer);
//				}
//				fileout.flush();
//				fileout.close();
//				Drawable mDrawable = Drawable.createFromPath(savePath);
//				return mDrawable;
//			} catch (Exception e) {
//				// TODO: handle exception
//				System.out.println(e);
//			}
//			return drawable;
			return null;
		}

		@Override
		protected void onPostExecute(Drawable result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null && tv != null && content != null) {
//				drawable.setDrawable(result);
//				tv.setText(tv.getText()); // 通过这里的重新设置 TextView 的文字来更新UI
				tv.setText(Html.fromHtml(content));
			}
		}

	}

	public class URLDrawable extends BitmapDrawable {

		private Drawable drawable;

		public URLDrawable(Drawable defaultDraw) {
			setDrawable(defaultDraw);
		}

		private void setDrawable(Drawable nDrawable) {
			drawable = nDrawable;
//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//					drawable.getIntrinsicHeight());
//			setBounds(0, 0, drawable.getIntrinsicWidth(),
//					drawable.getIntrinsicHeight());
		}

		@Override
		public void draw(Canvas canvas) {
			// TODO Auto-generated method stub
			drawable.draw(canvas);
		}

	}
}

