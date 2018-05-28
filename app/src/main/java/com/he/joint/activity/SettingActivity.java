package com.he.joint.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.common.Consts;
import com.he.joint.common.OpenUDID;
import com.he.joint.common.PreferenceHelper;
import com.he.joint.common.UIHelper;
import com.he.joint.mgr.DataMgr;
import com.he.joint.mgr.LoginMgr;
import com.he.joint.utils.NotificationsUtils;
import com.he.joint.utils.StringUtils;
import com.he.joint.utils.ToastUtils;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by luo_haogui on 2017/6/2.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llFeedback, llScore, llAssistant, llClearCache, llNotification;
    private Button btnLogout;
    private TextView tvCacheSize,tvVersion;
    private TextView tvStatus;
    private ImageView ivSwitch;
    private Handler handler = new Handler();
    private boolean isOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initTopBar("设置");
        initView();
        setLogoutButtonStatus();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            setSwitchStatus();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            boolean isNotification = NotificationsUtils.isNotificationEnabled(mContext);
            if (isNotification) {
                tvStatus.setText("已开启");
                ivSwitch.setImageResource(R.drawable.ty_kaiguan_2);
            }else{
                tvStatus.setText("已关闭");
                ivSwitch.setImageResource(R.drawable.ty_kaiguan_1);
            }
        }
    }
    private void initView() {
        tvStatus = findView(R.id.tvStatus);
        ivSwitch = findView(R.id.ivSwitch);
        llNotification=findView(R.id.llNotification);
        llFeedback = findView(R.id.llFeedback);
        llScore = findView(R.id.llScore);
        llAssistant = findView(R.id.llAssistant);
        llClearCache = findView(R.id.llClearCache);
        btnLogout = findView(R.id.btnLogout);
        tvCacheSize = findView(R.id.tvCacheSize);
        tvVersion = findView(R.id.tvVersion);

        llFeedback.setOnClickListener(this);
        llScore.setOnClickListener(this);
        llAssistant.setOnClickListener(this);
        llClearCache.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        llNotification.setOnClickListener(this);

        String version="";
        try {
            version= OpenUDID.getVersionName(mContext);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tvVersion.setText("当前版本："+version);


        ivSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    boolean isNotification = NotificationsUtils.isNotificationEnabled(mContext);
                    NotificationsUtils.gotoSetting(mContext);
                }else {
                    isOn = !isOn;
                    PreferenceHelper.saveToSharedPreferences(Consts.PreferenceKeys.MessageNotificationSwitch, isOn);
                    setSwitchStatus();
                    if (isOn) {
                        JPushInterface.resumePush(getApplicationContext());
                    } else {
                        JPushInterface.stopPush(getApplicationContext());
                    }
                }
            }
        });

        new Thread(){
            public void run() {
                try {
                    final String text = DataMgr.getInstance().getCacheSize();
                    if (StringUtils.isNotEmpty(text) && handler != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (tvCacheSize != null) {
                                    tvCacheSize.setText(text);
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == llFeedback.getId()) {
            UIHelper.startActivity(mContext, MoreFeedbackActivity.class);
        } else if (view.getId() == llScore.getId()) {

        } else if (view.getId() == llAssistant.getId()) {

        } else if (view.getId() == llClearCache.getId()) {
            DataMgr.getInstance().clearCache();
            tvCacheSize.setText("");
            ToastUtils.show(mContext, "缓存清理完成！");
        } else if (view.getId() == btnLogout.getId()) {
            LoginMgr.shareInstance().logout();
            setLogoutButtonStatus();
            finish();
        }
    }

    private void setLogoutButtonStatus() {
        if (LoginMgr.shareInstance().getLoginStatus()) {
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            btnLogout.setVisibility(View.INVISIBLE);
        }
    }
    private void setSwitchStatus() {
        isOn = (Boolean) PreferenceHelper.getFromSharedPreferences(Consts.PreferenceKeys.MessageNotificationSwitch, Boolean.class.getName());
        if (isOn) {
            tvStatus.setText("已开启");
            ivSwitch.setImageResource(R.drawable.ty_kaiguan_2);
        } else {
            tvStatus.setText("已关闭");
            ivSwitch.setImageResource(R.drawable.ty_kaiguan_1);
        }
    }
}
