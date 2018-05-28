package com.he.joint.activity;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.he.joint.R;
import com.he.joint.adapter.RecoderAdapter;
import com.he.joint.audio.AudioRecorderButton;
import com.he.joint.audio.MediaManager;
import com.he.joint.bean.RecorderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luo_haogui on 2017/5/27.
 */

public class RecoderAcitivity extends BaseActivity {
    private ListView mListView;

    private RecoderAdapter mAdapter;
    private List<RecorderBean> mDatas = new ArrayList<RecorderBean>();

    private AudioRecorderButton mAudioRecorderButton;

    private View animView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoder);

        mListView = (ListView) findViewById(R.id.id_listview);
        mAudioRecorderButton = (AudioRecorderButton) findViewById(R.id.id_recorder_button);


        mAudioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {

            public void onFinish(float seconds, String filePath) {
                RecorderBean recorder = new RecorderBean();
                recorder.time = seconds;
                recorder.filePath = filePath;
                mDatas.add(recorder);
                mAdapter.notifyDataSetChanged(); //通知更新的内容
                mListView.setSelection(mDatas.size() - 1); //将lisview设置为最后一个
            }
        });

        mAdapter = new RecoderAdapter(this, mDatas);
        mListView.setAdapter(mAdapter);

        //listView的item点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View view,int position, long id) {
                // 播放动画（帧动画）
                if (animView != null) {
                    animView.setBackgroundResource(R.drawable.adj);
                    animView = null;
                }
                animView = view.findViewById(R.id.id_recoder_anim);
                animView.setBackgroundResource(R.drawable.play_anim);
                AnimationDrawable animation = (AnimationDrawable) animView.getBackground();
                animation.start();
                // 播放录音
                MediaManager.playSound(mDatas.get(position).filePath,new MediaPlayer.OnCompletionListener() {

                    public void onCompletion(MediaPlayer mp) {
                        animView.setBackgroundResource(R.drawable.adj);
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }

}
