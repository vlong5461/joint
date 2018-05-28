package com.he.joint.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.bean.QuestionBean;
import com.he.joint.mgr.DataMgr;
import com.he.joint.utils.StringUtils;
import com.he.joint.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */

public class QuestionListAdapter extends BaseAdapter {
    private Context ctx;
    private List<QuestionBean> dataList;

    public QuestionListAdapter(Context ctx) {
        this.ctx = ctx;

    }

    public void setDataList(List<QuestionBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_question_item, null);
            holder.llRecoderPlay = (LinearLayout) convertView.findViewById(R.id.llRecoderPlay);
            holder.llReplyAnswer = (LinearLayout) convertView.findViewById(R.id.llReplyAnswer);
            holder.llReply = (LinearLayout) convertView.findViewById(R.id.llReply);
            holder.rlQuestionBottom = (RelativeLayout) convertView.findViewById(R.id.rlQuestionBottom);
            holder.llZan = (LinearLayout) convertView.findViewById(R.id.llZan);
            holder.llListen = (LinearLayout) convertView.findViewById(R.id.llListen);
            holder.ivQuestionHead = (RoundImageView) convertView.findViewById(R.id.ivQuestionHead);
            holder.ivAnswerHead = (RoundImageView) convertView.findViewById(R.id.ivAnswerHead);
            holder.tvQuestionName = (TextView) convertView.findViewById(R.id.tvQuestionName);
            holder.tvQuestionDate = (TextView) convertView.findViewById(R.id.tvQuestionDate);
            holder.tvQuestionData = (TextView) convertView.findViewById(R.id.tvQuestionData);
            holder.tvAnswer = (TextView) convertView.findViewById(R.id.tvAnswer);
            holder.tvPlayTime = (TextView) convertView.findViewById(R.id.tvPlayTime);
            holder.tvReplyTime = (TextView) convertView.findViewById(R.id.tvReplyTime);
            holder.tvListenNum = (TextView) convertView.findViewById(R.id.tvListenNum);
            holder.tvZanNum = (TextView) convertView.findViewById(R.id.tvZanNum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        QuestionBean bean = dataList.get(position);
        holder.tvQuestionName.setText(bean.name);
        holder.tvQuestionData.setText(bean.name);
//        if (StringUtils.isNotEmpty("")) {
//            ImageLoader.getInstance().displayImage("", holder.ivAnswerHead, DataMgr.options);
//        }

        return convertView;
    }

    static class ViewHolder {
        LinearLayout llRecoderPlay,llZan,llListen,llReply,llReplyAnswer;
        RelativeLayout rlQuestionBottom;
        RoundImageView ivQuestionHead,ivAnswerHead;
        TextView tvQuestionName, tvQuestionData, tvQuestionDate,tvPlayTime;
        TextView tvAnswer,tvReplyTime,tvListenNum,tvZanNum;
    }

}
