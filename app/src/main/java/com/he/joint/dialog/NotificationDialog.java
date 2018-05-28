package com.he.joint.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.he.joint.R;


/**
 * Created by luo_haogui on 2017/5/22.
 */

public class NotificationDialog extends BaseDialog{

    private static int default_width = -2;
    private static int default_height = -2;
    private Context mContext;
    private TextView tvCancel,tvOpenNotice;
    private ImageView ivClose;

    public NotificationDialog(Context context) {
        super(context, default_width, default_height, R.layout.dialog_notification, R.style.DialogStyle2,
                Gravity.CENTER);
        this.mContext=context;
        this.setCancelable(false);
        initView();
    }

    private void initView() {
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvOpenNotice = (TextView) findViewById(R.id.tvOpenNotice);
        ivClose = (ImageView) findViewById(R.id.ivClose);
        tvOpenNotice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //save data
                if (openListener != null) {
                    openListener.onOpen();
                }
                NotificationDialog.this.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                NotificationDialog.this.dismiss();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                NotificationDialog.this.dismiss();
            }
        });
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public NotificationDialog.OpenNoticeListener openListener;
    public interface OpenNoticeListener {
        public void onOpen();
    }

}
