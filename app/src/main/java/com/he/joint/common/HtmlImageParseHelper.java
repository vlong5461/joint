package com.he.joint.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.activity.PublicWebViewActivity;
import com.he.joint.activity.ViewBigImageActivity;
import com.he.joint.mgr.DataMgr;
import com.he.joint.utils.CollectionUtils;
import com.he.joint.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luo_haogui on 2017/5/12.
 */

public class HtmlImageParseHelper {


    private LinearLayout container;
    private String html;
    private List<String> textList;
    private List<String> imageList;
    private Context context;
    private int textSize = 15;

    public HtmlImageParseHelper(LinearLayout container, String html) {
        this.container = container;
        this.html = html;
        this.context = container.getContext();
        this.textSize = 15;
        initData();
    }

    public HtmlImageParseHelper(LinearLayout container, String html, int textSize) {
        this.container = container;
        this.html = html;
        this.context = container.getContext();
        this.textSize = textSize;
        initData();
    }

    private void initData() {
        textList = new ArrayList<String>();
        imageList = new ArrayList<String>();
//xxxxxxxxxxxxxx<img src="http://www.rruu.com/img/upload1/2016/20160801/f5e7e83e-8f18-4d42-b496-8221f155024a.jpg" with="800" height="511" onclick="window.jsObj.callback('http://www.rruu.com/img/upload1/2016/20160801/f5e7e83e-8f18-4d42-b496-8221f155024a.jpg');" alt="" />
        List<String> imgTagList = new ArrayList<String>();
        Pattern pattern = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
        Pattern pattern2 = Pattern.compile("src=\".*\"");
        Matcher matcher = pattern.matcher(this.html);
        Matcher matcher2;
        while (matcher.find()) {
            String imgTag = matcher.group();
            matcher2 = pattern2.matcher(imgTag);
            if (matcher2.find()) {
                String src = matcher2.group();
                if (StringUtils.isNotEmpty(src)) {
                    int index = src.indexOf("\"", 5);
                    String url = src.substring(5, index);
                    imageList.add(url);
                }
            }
            imgTagList.add(imgTag);
        }

//		StringBuffer sb = new StringBuffer();
//		try {
//			BufferedReader br = new BufferedReader(new StringReader(this.html));
//			String line = null;
//			while ((line = br.readLine()) != null) {
//				sb.append(line) ;
//				sb.append("<br />");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		String source = sb.toString();

        String source = this.html.replaceAll("\\\\n", "<br/>").replaceAll("\n", "<br/>");
//		String source = this.html.trim().replaceAll("\r\n", "XXXXXXXXXXXXXXX").replaceAll("\n", "YYYYYYYYYYYYYYY");
        if (imgTagList.size() > 0) {
            for (String string : imgTagList) {
                source = source.replace(string, "<@>");
            }

            String[] array = source.split("<@>");
            textList.addAll(Arrays.asList(array));
        } else {
            textList.add(source);
        }
    }

    public void showHtmlContent() {
        if (container == null) {
            return;
        }
        container.removeAllViews();
        if(textList.size()>0) {
            for (int i = 0; i < textList.size(); i++) {
                if (StringUtils.isNotEmpty(textList.get(i))) {
                    TextView tv = new TextView(context);
                    tv.setTextColor(context.getResources().getColor(R.color.black_666666));
                    tv.setTextSize(textSize);
//				int count = textList.get(i).length();
//				String text = textList.get(i).trim().replaceAll("\n", "<br/>");
//				int count2 = textList.get(i).length();
//				if (text.length() > 250) {
//					String sub = text.substring(242, 246);
//					MyLog.i(">>>>>>>>>>>>>> " + sub);;
//				}
                    String text = textList.get(i).trim();

//				Spanned span = Html.fromHtml(text);
//				MyLog.i("========" + span.toString());

//				text = text.replaceAll("<ol>", "</br>");
                    text = text.replaceAll("<li>", "· ");
                    text = text.replaceAll("</li>", "</br>");
//				text = text.replaceAll("</ol>", "");

                    Pattern pattern = Pattern.compile("<(a)(.*?)(</a>)");
                    Matcher matcher = pattern.matcher(text);
                    List<Object[]> list = new ArrayList<>();
                    Pattern pattern2 = Pattern.compile("href=\".*\"");
                    while (matcher.find()) {
                        String link = matcher.group();
                        int start = text.indexOf(link);
                        Object[] array = new Object[4];
                        array[0] = start;
                        array[1] = start + link.length();
                        String url = "";
                        Matcher matcher2 = pattern2.matcher(link);
                        if (matcher2.find()) {
                            String src = matcher2.group();
                            if (StringUtils.isNotEmpty(src)) {
                                int index = src.indexOf("\"", 6);
                                url = src.substring(6, index);
                            }
                        }
                        array[2] = url;
                        int text_start = link.indexOf(">") + 1;
                        int text_end = link.indexOf("</a>");
                        String link_title = link.substring(text_start, text_end);
                        array[3] = link_title;
                        list.add(array);
                    }
                    if (CollectionUtils.isNotEmpty(list)) {
                        SpannableStringBuilder span_new = new SpannableStringBuilder();
                        int position = 0;
                        for (Object[] array : list) {
                            if (position < (int) array[0]) {
                                span_new.append(Html.fromHtml(text.substring(position, (int) array[0])));
                                position = (int) array[0];
                            }

                            Spanned span_html = Html.fromHtml(array[3].toString());
                            Spannable sp = new SpannableString(span_html);
                            sp.setSpan(new ClickableLink(array[2].toString(), sp.toString()), 0, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            span_new.append(sp);

                            position = (int) array[1];

                        }
                        if (position < text.length() - 1) {
                            span_new.append(Html.fromHtml(text.substring(position, text.length())));
                        }
                        tv.setText(span_new);
                    } else {
                        tv.setText(Html.fromHtml(text));
                    }

                    tv.setLineSpacing(0, 1.4f);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    tv.setLayoutParams(params);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());

                    container.addView(tv);
                }

                if (i < imageList.size()) {
                    ImageView iv = new ImageView(context);
                    iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    //iv.setBackgroundColor(context.getResources().getColor(R.color.white_1));

                    container.addView(iv);
                    ImageLoader.getInstance().displayImage(imageList.get(i).trim(), iv, DataMgr.options, new ImageLoadingListener() {

                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            if (loadedImage != null) {
                                LinearLayout.LayoutParams params;
                                if (loadedImage.getWidth() > view.getMeasuredWidth()) {
                                    float rate = (float) loadedImage.getWidth() / (float) view.getMeasuredWidth();
                                    int height = (int) (loadedImage.getHeight() / rate);
                                    params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                            height);
                                } else {
                                    params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                                }

                                view.setLayoutParams(params);
                            }
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {

                        }
                    });

                    final int index = i;
                    iv.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            Bundle b = new Bundle();
                            b.putSerializable("data", (Serializable) imageList);
                            b.putString("title", "");
                            b.putInt("index", index);
                            UIHelper.startActivity(context, ViewBigImageActivity.class, b);
                        }
                    });
                }

            }
        }else{
            for(int i=0;i<imageList.size();i++) {
                if (i < imageList.size()) {
                    ImageView iv = new ImageView(context);
                    iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    //iv.setBackgroundColor(context.getResources().getColor(R.color.white_1));

                    container.addView(iv);
                    ImageLoader.getInstance().displayImage(imageList.get(i).trim(), iv, DataMgr.options, new ImageLoadingListener() {

                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            if (loadedImage != null) {
                                LinearLayout.LayoutParams params;
                                if (loadedImage.getWidth() > view.getMeasuredWidth()) {
                                    float rate = (float) loadedImage.getWidth() / (float) view.getMeasuredWidth();
                                    int height = (int) (loadedImage.getHeight() / rate);
                                    params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                            height);
                                } else {
                                    params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                                }

                                view.setLayoutParams(params);
                            }
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {

                        }
                    });

                    final int index = i;
                    iv.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            Bundle b = new Bundle();
                            b.putSerializable("data", (Serializable) imageList);
                            b.putString("title", "");
                            b.putInt("index", index);
                            UIHelper.startActivity(context, ViewBigImageActivity.class, b);
                        }
                    });
                }
            }
        }
    }

    private class ClickableLink extends android.text.style.ClickableSpan {
        private String url;
        private String title;

        public ClickableLink(String url, String title) {
            this.url = url;
            this.title = title;
        }

        @Override
        public void onClick(View view) {
            Bundle b= new Bundle();
            b.putString("title", title);
            b.putString("url", url);
            UIHelper.startActivity(context, PublicWebViewActivity.class, b);
        }
    }

}
