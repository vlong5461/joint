package com.he.joint.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.utils.ToastUtils;

/**
 * Created by Administrator on 2017/6/3.
 */

public class EditNameDialog  extends BaseDialog {

    private static int default_width = -1;
    private static int default_height = -2;

    private TextView tvCancel, tvOk;
    private EditText etUserName;
    private Context mContext;

    public EditNameDialog(Context context) {
        super(context, default_width, default_height, R.layout.dialog_edit_name, R.style.DialogStyle2,
                Gravity.CENTER);
        this.setCancelable(true);
        this.mContext=context;
        initView();
    }

    private void initView() {
        etUserName = (EditText) findViewById(R.id.etUserName);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvOk = (TextView) findViewById(R.id.tvOk);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditNameDialog.this.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (etUserName.getText().toString().isEmpty()) {
                    ToastUtils.show(getContext().getApplicationContext(), "请输入昵称!");
                    return;
                }
                String name=etUserName.getText().toString();
                if (etListener != null) {
                    etListener.editName(name);
                }
                setNameData();
                EditNameDialog.this.dismiss();
            }
        });
    }

    private void setNameData(){

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public EditNameDialog.EditNameListener etListener;
    public interface EditNameListener {
        public void editName(String name);
    }
}
