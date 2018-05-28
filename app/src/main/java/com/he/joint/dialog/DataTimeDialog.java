package com.he.joint.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.utils.ToastUtils;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/6/3.
 */

public class DataTimeDialog  extends BaseDialog {

    private static int default_width = -1;
    private static int default_height = -2;

    private TextView tvCancel, tvOk;
    private DatePicker datePicker;
    private Context mContext;

    public DataTimeDialog(Context context) {
        super(context, default_width, default_height, R.layout.dialog_data_time, R.style.DialogStyle2,
                Gravity.CENTER);
        this.setCancelable(true);
        this.mContext=context;
        initView();
    }

    private void initView() {
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvOk = (TextView) findViewById(R.id.tvOk);
        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DAY_OF_MONTH, 7);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker arg0, int year, int month, int day) {

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                DataTimeDialog.this.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String data=datePicker.getYear()+"年"+datePicker.getMonth()+"月"+datePicker.getDayOfMonth()+"日";
                if (dataListener != null) {
                    dataListener.selectData(data);
                }
                DataTimeDialog.this.dismiss();
            }
        });
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public DataTimeDialog.DataListener dataListener;
    public interface DataListener {
        public void selectData(String data);
    }
}
