package com.he.joint.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.common.BitmapHelper;
import com.he.joint.dialog.ActionSheetDialog;
import com.he.joint.dialog.CusSeekBar;
import com.he.joint.dialog.DataTimeDialog;
import com.he.joint.dialog.EditNameDialog;
import com.he.joint.dialog.SelectListDialog;
import com.he.joint.mgr.DataMgr;
import com.he.joint.utils.CommonUtils;
import com.he.joint.utils.FileUtils;
import com.he.joint.utils.StringUtils;
import com.he.joint.utils.ToastUtils;
import com.he.joint.view.RoundImageView;
import com.third.grant.PermissionsManager;
import com.third.grant.PermissionsResultAction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */

public class MyInfoActivity  extends BaseActivity implements View.OnClickListener{
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private RoundImageView ivUserHead;
    private LinearLayout llName,llSex,llBirthday,llOccupation,llIndustry;
    private LinearLayout llSeekBar;
    private CusSeekBar mSeekBar;
    private TextView tvName,tvSex,tvBirthday,tvOccupation,tvIndustry;
    private File currPicFile;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        initTopBar("我的资料");
        initView();
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult((Activity) mContext,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {

                    }

                    @Override
                    public void onDenied(String permission) {

                    }
                });
    }

    private void initView() {
        ivUserHead = findView(R.id.ivUserHead);
        ivUserHead.setTag("default");
        llName = findView(R.id.llName);
        llSex = findView(R.id.llSex);
        llBirthday = findView(R.id.llBirthday);
        llOccupation = findView(R.id.llOccupation);
        llIndustry = findView(R.id.llIndustry);
        llSeekBar = findView(R.id.llSeekBar);
        tvName = findView(R.id.tvName);
        tvSex = findView(R.id.tvSex);
        tvBirthday = findView(R.id.tvBirthday);
        tvOccupation = findView(R.id.tvOccupation);
        tvIndustry = findView(R.id.tvIndustry);
        currPicFile = new File(FileUtils.getDiskCacheDir(mContext).getPath() + "/pic.png");
        ivUserHead.setOnClickListener(this);
        llName.setOnClickListener(this);
        llSex.setOnClickListener(this);
        llBirthday.setOnClickListener(this);
        llOccupation.setOnClickListener(this);
        llIndustry.setOnClickListener(this);
        setSeekBar();
    }
    private void setSeekBar(){
        mSeekBar = new CusSeekBar(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 30);
        params.leftMargin = 70;
        params.rightMargin = 70;
        mSeekBar.setLayoutParams(params);
        mSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.bar_color));
        mSeekBar.setThumb(getResources().getDrawable(android.R.color.transparent));
        mSeekBar.setMax(100);
        llSeekBar.addView(mSeekBar);
    }
    private void setSeekBarProgress(){
        int mProgress=0;
        if(!ivUserHead.getTag().toString().equals("default")){
            mProgress += 17;
        }
        if(!tvName.getText().toString().isEmpty()){
            mProgress += 17;
        }
        if(!tvSex.getText().toString().isEmpty()){
            mProgress += 17;
        }
        if(!tvBirthday.getText().toString().isEmpty()){
            mProgress += 17;
        }
        if(!tvOccupation.getText().toString().isEmpty()){
            mProgress += 17;
        }if(!tvIndustry.getText().toString().isEmpty()){
            mProgress += 17;
        }
        if (mProgress <= 100) {
            mSeekBar.setProgress(mProgress);
            mSeekBar.setSeekBarText(mProgress + "%");
        }else{
            mSeekBar.setProgress(100);
            mSeekBar.setSeekBarText("100%");
        }
    }
    @Override
    public void onClick(View view) {
        if(CommonUtils.isFastDoubleClick()){
            return;
        }
        if (view.getId() == ivUserHead.getId()){
            showDialog();
        }else if (view.getId() == llName.getId()){
            EditNameDialog dialog =new EditNameDialog(mContext);
            dialog.show();
            dialog.etListener = new EditNameDialog.EditNameListener() {
                @Override
                public void editName(String name) {
                    tvName.setText(name);
                    setSeekBarProgress();
                }
            };
        }else if (view.getId() == llSex.getId()){
            SelectListDialog listDialog=new SelectListDialog(mContext);
            listDialog.setTitle("请选性别");
            listDialog.setBotomText("");
            List<String> sexList=new ArrayList<String>();
            sexList.add("男");
            sexList.add("女");
            listDialog.setListData(sexList,"男");
            listDialog.show();
            listDialog.listener=new SelectListDialog.ListOKListener() {
                @Override
                public void onOK(String text) {
                    tvSex.setText(text);
                    setSeekBarProgress();
                }
            };
        }else if (view.getId() == llBirthday.getId()){
            DataTimeDialog dataTimeDialog=new DataTimeDialog(mContext);
            dataTimeDialog.show();
            dataTimeDialog.dataListener=new DataTimeDialog.DataListener() {
                @Override
                public void selectData(String data) {
                    tvBirthday.setText(data);
                    setSeekBarProgress();
                }
            };
        }else if (view.getId() == llOccupation.getId()){
            SelectListDialog listDialog=new SelectListDialog(mContext);
            listDialog.setTitle("请选职业");
            listDialog.setBotomText("");
            List<String> sexList=new ArrayList<String>();
            sexList.add("设计师");
            sexList.add("程序员");
            listDialog.setListData(sexList,"程序员");
            listDialog.show();
            listDialog.listener=new SelectListDialog.ListOKListener() {
                @Override
                public void onOK(String text) {
                    tvOccupation.setText(text);
                    setSeekBarProgress();
                }
            };
        }else if (view.getId() == llIndustry.getId()){
            SelectListDialog listDialog=new SelectListDialog(mContext);
            listDialog.setTitle("请选行业");
            listDialog.setBotomText("");
            List<String> sexList=new ArrayList<String>();
            sexList.add("互联网");
            sexList.add("媒体");
            listDialog.setListData(sexList,"媒体");
            listDialog.show();
            listDialog.listener=new SelectListDialog.ListOKListener() {
                @Override
                public void onOK(String text) {
                    tvIndustry.setText(text);
                    setSeekBarProgress();
                }
            };
        }
    }
    private void setImageHeadView(String imagePath) {
        if (StringUtils.isNotEmpty(imagePath)) {
            int size = (int) (55 * DataMgr.getInstance().screenDensity + 0.5);
            Bitmap bitmap = BitmapHelper.decodeBitmap(imagePath, size, size);
            ivUserHead.setImageBitmap(bitmap);
            ivUserHead.setTag("ivUserHead");
            setSeekBarProgress();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Uri selectedImage = Uri.fromFile(currPicFile);
                ;
                String filePath = FileUtils.FeedbackImagePath + System.currentTimeMillis();
                try {
                    FileUtils.copyFile(selectedImage, filePath, mContext);
                    imagePath = filePath;
                    setImageHeadView(imagePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == IMAGE_REQUEST_CODE) {
                Uri selectedImage = intent.getData();
                String filePath = FileUtils.FeedbackImagePath + System.currentTimeMillis();
                try {
                    FileUtils.copyFile(selectedImage, filePath, mContext);
                    imagePath = filePath;
                    setImageHeadView(imagePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showDialog() {

        new ActionSheetDialog(mContext).builder().setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                        boolean hasPermiss = PermissionsManager.getInstance().hasPermission(mContext,
                                Manifest.permission.CAMERA);
                        if (!hasPermiss) {
                            ToastUtils.show(mContext, "你已拒绝使用相机，如果需要使用相机，请打开应用的相机权限（设置->应用->任游->权限）", 2000);
                        } else {
                            Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currPicFile));
                            startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
                        }
                    }
                }).addSheetItem("从相册中选择", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                boolean hasPermission = PermissionsManager.getInstance().hasPermission((Activity) mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (!hasPermission) {
                    ToastUtils.show(mContext, "请打开应用的存储权限（设置->应用->任游->权限）");
                } else {
                    Intent intentFromGallery = new Intent();
                    intentFromGallery.setType("image/*"); // 设置文件类型
                    intentFromGallery.setAction(Intent.ACTION_PICK);
                    intentFromGallery
                            .setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
                }
            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        FileUtils.deleteAllFiles(new File(FileUtils.FeedbackImagePath));
    }
}
