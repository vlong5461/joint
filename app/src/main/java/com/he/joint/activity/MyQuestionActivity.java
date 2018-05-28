package com.he.joint.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.adapter.QuestionListAdapter;
import com.he.joint.api.BaseApi;
import com.he.joint.bean.QuestionBean;
import com.he.joint.common.Consts;
import com.he.joint.utils.CommonUtils;
import com.third.view.pullablelistview.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */

public class MyQuestionActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvNoReply,tvReply;
    private ListView listview;
    private PullToRefreshLayout pullLayout;
    private QuestionListAdapter adapter;
    private boolean isRefreshData = false;
    private List<QuestionBean> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);
        initTopBar("我的提问");
        initView();
    }

    private void initView() {
        tvNoReply = findView(R.id.tvNoReply);
        tvReply = findView(R.id.tvReply);
        listview = findView(R.id.listview);
        pullLayout = findView(R.id.refresh_view);
        tvNoReply.setOnClickListener(this);
        tvReply.setOnClickListener(this);
        dataList= new ArrayList<>();
        adapter = new QuestionListAdapter(mContext);
        listview.setAdapter(adapter);
        for(int i=0;i<5;i++) {
            QuestionBean questionBean = new QuestionBean();
            questionBean.name = "测试";
            dataList.add(questionBean);
        }
        setData();
        pullLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                isRefreshData = true;
                fetchData();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pullLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (CommonUtils.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == tvNoReply.getId()) {
            tvNoReply.setTextColor(getResources().getColor(R.color.black_222222));
            tvReply.setTextColor(getResources().getColor(R.color.gray_2));
            dataList.clear();
            for(int i=0;i<5;i++) {
                QuestionBean questionBean = new QuestionBean();
                questionBean.name = "测试";
                dataList.add(questionBean);
            }
            setData();
        }else if(view.getId() == tvReply.getId()) {
            tvReply.setTextColor(getResources().getColor(R.color.black_222222));
            tvNoReply.setTextColor(getResources().getColor(R.color.gray_2));
            dataList.clear();
            for(int i=0;i<5;i++) {
                QuestionBean questionBean = new QuestionBean();
                questionBean.name = "回复";
                dataList.add(questionBean);
            }
            setData();
        }
    }
    private void setData() {
        adapter.setDataList(dataList);
        adapter.notifyDataSetChanged();
    }

    private void fetchData() {

    }

    private void setPullLayoutStatus(BaseApi api) {
        if (isRefreshData) {
            isRefreshData = false;
            int pullStatus;
            if (api.responseCode == Consts.ResponseCode.Success) {
                if (api.contentCode == Consts.ContentCode.Success) {
                    pullStatus = PullToRefreshLayout.SUCCEED;
                } else {
                    pullStatus = PullToRefreshLayout.SUCCEED;
                }
            } else {
                pullStatus = PullToRefreshLayout.SUCCEED;
            }
            pullLayout.refreshFinish(pullStatus);
        }
    }

}
