package com.he.joint.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.he.joint.R;
import com.he.joint.adapter.ExpertGridViewAdapter;
import com.he.joint.adapter.FeedbackGridViewAdapter;
import com.he.joint.common.UIHelper;
import com.he.joint.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */

public class MyAttentionActivity extends BaseActivity {

    private GridView gvExpert;
    private ExpertGridViewAdapter adapter;
    private List<String> imageList;
    private boolean isShowDel=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attention);
        initTopBar("我的关注","编辑");
        initView();
        setData();
    }

    @Override
    protected void topNavBarRightClicked() {
        super.topNavBarRightClicked();
        if(adapter!=null && CollectionUtils.isNotEmpty(imageList)){
            isShowDel = !isShowDel;
            adapter.setShowDel(isShowDel);
            adapter.setDataList(imageList);
            adapter.notifyDataSetChanged();
        }
    }

    private void initView(){
        imageList = new ArrayList<String>();
        gvExpert = findView(R.id.gvExpert);
        adapter = new ExpertGridViewAdapter(mContext);
        gvExpert.setAdapter(adapter);
        adapter.listener = new ExpertGridViewAdapter.ExpertGridViewAdapterListener() {
            @Override
            public void onDelete(int index) {
                if (imageList != null){
                    if(imageList.size() > index) {
                        imageList.remove(index);
                        adapter.setDataList(imageList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        };
        gvExpert.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UIHelper.startActivity(mContext,ExpertDetailsActivity.class);
            }
        });
    }

    private void setData(){
        for(int i=0;i<5;i++){
            String str="1";
            imageList.add(str);
        }
        adapter.setDataList(imageList);
        adapter.notifyDataSetChanged();
    }
}
