package com.he.joint.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.utils.StringUtils;
import com.he.joint.utils.WidgetUtils;

import java.util.HashMap;

public class PublicWebViewActivity extends BaseActivity {

	private String topTitle;
	private String url;
	private ImageView topNavBarLeftBack;
	protected TextView topNavBarTitle;
	private WebView webview;
	private Context mContext;
	private Handler mHandler = new Handler();

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_public_webview);
		mContext =this;
		if (getIntent() != null) {
			topTitle = this.getIntent().getStringExtra("title");
			url = this.getIntent().getStringExtra("url");
		}

		if (StringUtils.isEmpty(topTitle)) {
			topTitle = "";
		}
		
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setSupportZoom(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.getSettings().setDisplayZoomControls(false);
		webview.getSettings().setAppCacheEnabled(false);  //开启缓存功能
		webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);      //缓存模式
		webview.setWebChromeClient(new WebChromeClient());
		webview.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		webview.addJavascriptInterface(new JSObject(), "jsObj");
		if (StringUtils.isNotEmpty(url)) {
			webview.loadUrl(url);
			webview.setWebViewClient(new WebViewClient() {
				@SuppressLint("NewApi")
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					return super.shouldOverrideUrlLoading(view, url);
				}
				// 加载后调用
				@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
				@Override
				public void onPageFinished(WebView view, String url) {
					getUserFromApp();
				}
				// 加载前调用
				@Override
				public void onPageStarted(WebView view, String url, Bitmap favicon) {

				}
			});
		}

		initTop(topTitle);
	}

	@Override
	protected void onStart() {
		super.onStart();
		getUserFromApp();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (PublicWebViewActivity.this.getCurrentFocus() != null) {
					WidgetUtils.hideSoftInput(mContext, PublicWebViewActivity.this.getCurrentFocus());
				}
			}
		}, 500);
	}

	private  void getUserFromApp(){

	}
	public void initTop(String title){
		topNavBarLeftBack = findView(R.id.topNavBarLeftBack);
		topNavBarTitle = findView(R.id.topNavBarTitle);
		if (topNavBarTitle != null) {
			topNavBarTitle.setText(title);
		}
		if (topNavBarLeftBack != null) {
			topNavBarLeftBack.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (webview.canGoBack()) {
						webview.goBack();
					} else {
						finish();
					}
				}
			});
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack();
			return true;
		} else if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finish();
		} else {
			return true;
		}
		return false;
	}

	public class JSObject {
		@JavascriptInterface
		public void callback(String json) {

		}
	}
}