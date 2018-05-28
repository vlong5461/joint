package com.he.joint.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.common.BitmapHelper;
import com.he.joint.common.UIHelper;
import com.he.joint.dialog.ActionSheetDialog;
import com.he.joint.mgr.DataMgr;
import com.he.joint.photo.Bimp;
import com.he.joint.utils.FileUtils;
import com.he.joint.utils.StringUtils;
import com.he.joint.utils.ToastUtils;
import com.third.grant.PermissionsManager;
import com.third.grant.PermissionsResultAction;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */

public class ExpertInforActivity extends BaseActivity{
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private ImageView ivHead;
    private EditText etIntroduce;
    private TextView tvNumber;
    private Button btnSubmit;
    private File currPicFile;
    private String imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_info);
        initTopBar("专家资料");
        initView();
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult((Activity) mContext,
                new String[] { Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {

                    }

                    @Override
                    public void onDenied(String permission) {

                    }
                });
    }

    private void initView() {
        ivHead = findView(R.id.ivHead);
        etIntroduce = findView(R.id.etIntroduce);
        tvNumber = findView(R.id.tvNumber);
        btnSubmit =findView(R.id.btnSubmit);
        etIntroduce.addTextChangedListener(mTextWatcher);
        currPicFile = new File(FileUtils.getDiskCacheDir(mContext).getPath() + "/pic.png");
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart ;
        private int editEnd ;
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
//          mTextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            editStart = etIntroduce.getSelectionStart();
            editEnd = etIntroduce.getSelectionEnd();
            tvNumber.setText(temp.length() + "/500");
            if (temp.length() > 500) {
                ToastUtils.show(mContext,"你输入的字数已经超过了限制！");
                s.delete(editStart-1, editEnd);
                int tempSelection = editStart;
                etIntroduce.setText(s);
                etIntroduce.setSelection(tempSelection);
            }
        }
    };
    private void setImageHeadView(String imagePath){
        if(StringUtils.isNotEmpty(imagePath)) {
            int size = (int) (55 * DataMgr.getInstance().screenDensity + 0.5);
            Bitmap bitmap = BitmapHelper.decodeBitmap(imagePath, size, size);
            ivHead.setImageBitmap(bitmap);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode != RESULT_CANCELED) {
            if(requestCode == CAMERA_REQUEST_CODE){
                Uri selectedImage = Uri.fromFile(currPicFile);;
                String filePath = FileUtils.FeedbackImagePath + System.currentTimeMillis();
                try {
                    FileUtils.copyFile(selectedImage, filePath, mContext);
                    imagePath = filePath;
                    setImageHeadView(imagePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(requestCode == IMAGE_REQUEST_CODE){
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

                        boolean hasPermiss = PermissionsManager.getInstance().hasPermission( mContext,
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
                boolean hasPermission = PermissionsManager.getInstance().hasPermission((Activity)mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(!hasPermission){
                    ToastUtils.show(mContext, "请打开应用的存储权限（设置->应用->任游->权限）");
                }else {
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
