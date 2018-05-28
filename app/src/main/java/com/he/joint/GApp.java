package com.he.joint;

import android.app.Application;

import com.he.joint.mgr.DataMgr;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cookie.store.PersistentCookieStore;
import com.lzy.okhttputils.model.HttpParams;

import cn.jpush.android.api.JPushInterface;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2015/9/23
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //        HttpHeaders headers = new HttpHeaders();
        //        headers.put("commonHeaderKey1", "commonHeaderValue1");    //所有的 header 都 不支持 中文
        //        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
        //        params.put("commonParamsKey1", "commonParamsValue1");     //所有的 params 都 支持 中文
        //        params.put("commonParamsKey2", "这里支持中文参数");

        //必须调用初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                 //全局的写入超时时间
                //                .setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
                //                .addCommonHeaders(headers)                                         //设置全局公共头
                .addCommonParams(params);                                          //设置全局公共参数

        DataMgr.getInstance().initData(this);
        JPushInterface.init(this);     		// 初始化 JPush

    }
}
