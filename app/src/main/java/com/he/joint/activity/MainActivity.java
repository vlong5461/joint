package com.he.joint.activity;

import android.annotation.SuppressLint;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.view.MainHomeVeiw;
import com.he.joint.view.MainMyView;

/**
 * Created by luo_haogui on 2017/4/27.
 */

public class MainActivity extends BaseActivity {

    private FrameLayout flContentContainer;
    private LinearLayout llBarHomeContainer,llBarAskContainer,llBarMessageContainer,llBarMyContainer;
    private ImageView ivBarHome,ivBarAsk,ivBarMessage,ivBarMy;
    private TextView tvBarHome,tvBarAsk,tvMessage,tvMessageCount,tvBarMessage,tvBarMy;
    private int currentTabBarIndex = TabBarIndex.Home;
    private MainMyView myView;
    private MainHomeVeiw mainHomeVeiw;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        flContentContainer = findView(R.id.flContentContainer);
        llBarHomeContainer = findView(R.id.llBarHomeContainer);
        llBarAskContainer = findView(R.id.llBarAskContainer);
        llBarMessageContainer = findView(R.id.llBarMessageContainer);
        llBarMyContainer = findView(R.id.llBarMyContainer);

        ivBarHome = findView(R.id.ivBarHome);
        ivBarAsk = findView(R.id.ivBarAsk);
        ivBarMessage = findView(R.id.ivBarMessage);
        ivBarMy = findView(R.id.ivBarMy);

        tvBarHome = findView(R.id.tvBarHome);
        tvBarAsk = findView(R.id.tvBarAsk);
        tvBarMessage = findView(R.id.tvBarMessage);
        tvBarMy = findView(R.id.tvBarMy);
        tvMessage = findView(R.id.tvMessage);
        tvMessageCount= findView(R.id.tvMessageCount);

        llBarHomeContainer.setOnClickListener(new ViewOnClick());
        llBarAskContainer.setOnClickListener(new ViewOnClick());
        llBarMessageContainer.setOnClickListener(new ViewOnClick());
        llBarMyContainer.setOnClickListener(new ViewOnClick());

        onTabBarClick();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void onTabBarClick() {
        onTabBarClick(false);
    }

    @SuppressLint("InflateParams")
    private void onTabBarClick(boolean isFromHomeVideo) {
        tvBarHome.setTextColor(getResources().getColor(R.color.gray_text));
        tvBarAsk.setTextColor(getResources().getColor(R.color.gray_text));
        tvBarMessage.setTextColor(getResources().getColor(R.color.gray_text));
        tvBarMy.setTextColor(getResources().getColor(R.color.gray_text));

        ivBarHome.setImageResource(R.drawable.tab_home_normal);
        ivBarAsk.setImageResource(R.drawable.tab_zhibo_normal);
        ivBarMessage.setImageResource(R.drawable.tab_kefu_normal);
        ivBarMy.setImageResource(R.drawable.tab_wode_normal);

        if(mainHomeVeiw!=null){
            mainHomeVeiw.setVisibility(View.GONE);
        }
        if (myView != null) {
            myView.setVisibility(View.GONE);
        }
        if (currentTabBarIndex == TabBarIndex.Home) {
            tvBarHome.setTextColor(getResources().getColor(R.color.orange_red));
            ivBarHome.setImageResource(R.drawable.tab_home_pressed);
            if (mainHomeVeiw == null) {
                mainHomeVeiw = (MainHomeVeiw) getLayoutInflater().inflate(R.layout.view_main_home, null);
                flContentContainer.addView(mainHomeVeiw);
            }
            mainHomeVeiw.setVisibility(View.VISIBLE);
        } else if (currentTabBarIndex == TabBarIndex.ASK) {
            tvBarAsk.setTextColor(getResources().getColor(R.color.orange_red));
            ivBarAsk.setImageResource(R.drawable.tab_zhibo_pressed);
        } else if (currentTabBarIndex == TabBarIndex.MESSAGE) {
            tvBarMessage.setTextColor(getResources().getColor(R.color.orange_red));
            ivBarMessage.setImageResource(R.drawable.tab_kefu_pressed);
        } else if (currentTabBarIndex == TabBarIndex.My) {
            tvBarMy.setTextColor(getResources().getColor(R.color.orange_red));
            ivBarMy.setImageResource(R.drawable.tab_wode_pressed);
            if (myView == null) {
                myView = (MainMyView) getLayoutInflater().inflate(R.layout.view_main_my, null);
                flContentContainer.addView(myView);
            }
            myView.setVisibility(View.VISIBLE);
        }
    }



    private class ViewOnClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == llBarHomeContainer.getId()) {
                if (currentTabBarIndex != TabBarIndex.Home) {
                    currentTabBarIndex = TabBarIndex.Home;
                    onTabBarClick();
                }
            } else if (view.getId() == llBarAskContainer.getId()) {
                if (currentTabBarIndex != TabBarIndex.ASK) {
                    currentTabBarIndex = TabBarIndex.ASK;
                    onTabBarClick();
                }
            } else if (view.getId() == llBarMessageContainer.getId()) {
                if (currentTabBarIndex != TabBarIndex.MESSAGE) {
                    currentTabBarIndex = TabBarIndex.MESSAGE;
                    onTabBarClick();
                }
            } else if (view.getId() == llBarMyContainer.getId()) {
                if (currentTabBarIndex != TabBarIndex.My) {
                    currentTabBarIndex = TabBarIndex.My;
                    onTabBarClick();
                }
            }
        }

    }

    private interface TabBarIndex {
        int Home = 1;
        int ASK = 2;
        int MESSAGE = 3;
        int My = 4;
    }
}
