package com.he.joint.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.he.joint.R;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.FileCallback;
import com.lzy.okhttputils.request.BaseRequest;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by luo_haogui on 2017/5/27.
 */

public class PdfViewActivity extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener {

    private PDFView pdfView;
    private TextView tvProgress;
    private Integer pageNumber = 0;
    private String pdfFileName = "yyy.pdf";
    private Uri uri;
    private String AUrl,pdfName;
    private Boolean isDown = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        pdfView = (PDFView)findViewById(R.id.pdfView);

//        Intent intent = getIntent();   // 获取 Intent
//
//        AUrl  = intent.getStringExtra("url"); // 获取 String 值
        AUrl = "http://cn.createpdfonline.org/pdffiles/mypdf1(20170524024008).pdf";
        Log.e("接收url:",AUrl);

        String[] exts = AUrl.split("/");
        pdfFileName = exts[exts.length-1];
        Log.e("pdf文件名：",pdfFileName);

        pdfName = Environment.getExternalStorageDirectory() +
                "/download";

        File file = new File(pdfName, pdfFileName);

//        if(file.exists()){
//            Log.e("Tip：","报告已经存在！");
//            //文件已经存在，则直接显示
//            uri = Uri.fromFile(file);
//            displayFromUri(uri);
//            pdfView.setVisibility(View.VISIBLE);
//        }
//        else{
        pdfView.setVisibility(View.GONE);
        isDown = true;
//        }
        if(isDown){
            OkHttpUtils.get(AUrl)//
                    .tag(this)//
                    .execute(new PdfViewActivity.DownloadFileCallBack(pdfName, pdfFileName));//保存到sd卡
        }
        tvProgress.setText("0%");
        tvProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDown){
                    OkHttpUtils.get(AUrl)//
                            .tag(this)//
                            .execute(new PdfViewActivity.DownloadFileCallBack(pdfName, pdfFileName));//保存到sd卡
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume(){

        super.onResume();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    /**
     * 如果服务器不支持中文路径的情况下需要转换url的编码。
     * @param string
     * @return
     */
    public String encodeGB(String string)
    {
        //转换中文编码
        String split[] = string.split("/");
        for (int i = 1; i < split.length; i++) {
            try {
                split[i] = URLEncoder.encode(split[i], "GB2312");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            split[0] = split[0]+"/"+split[i];
        }
        split[0] = split[0].replaceAll("\\+", "%20");//处理空格
        return split[0];
    }

    /*pdf显示函数集合*/

    private void displayFromUri(Uri urii) {
        //pdfFileName = getFileName(urii);

        pdfView.fromUri(urii)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }


    public void onResult(int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            uri = intent.getData();
            displayFromUri(uri);
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();

        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            // Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkHttpUtils.getInstance().cancelTag(this);
    }

    private class DownloadFileCallBack extends FileCallback {

        public DownloadFileCallBack(String destFileDir, String destFileName) {
            super(destFileDir, destFileName);
        }

        @Override
        public void onBefore(BaseRequest request) {
//            btnFileDownload.setText("正在下载中");
        }

        @Override
        public void onResponse(boolean isFromCache, File file, Request request, Response response) {
//            btnFileDownload.setText("下载完成");
//            String pdfName = Environment.getExternalStorageDirectory() +
//                    "/download";
//            File file = new File(pdfName, pdfFileName);
            tvProgress.setVisibility(View.GONE);
            uri = Uri.fromFile(file);
            Log.e("----",uri.toString());
            displayFromUri(uri);
            pdfView.setVisibility(View.VISIBLE);
        }

        @Override
        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
            Log.e("downloadProgress","downloadProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);

            String downloadLength = Formatter.formatFileSize(getApplicationContext(),
                    currentSize);
            String totalLength = Formatter.formatFileSize(getApplicationContext(), totalSize);
//            tvDownloadSize.setText(downloadLength + "/" + totalLength);
            String netSpeed = Formatter.formatFileSize(getApplicationContext(), networkSpeed);
//            tvNetSpeed.setText(netSpeed + "/S");
            tvProgress.setVisibility(View.VISIBLE);
            tvProgress.setText((Math.round(progress * 10000) * 1.0f / 100) + "%");
        }

        @Override
        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
            super.onError(isFromCache, call, response, e);
//            btnFileDownload.setText("下载出错");
        }
    }
}
