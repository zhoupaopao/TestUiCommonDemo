package com.example.module_personal.app.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.example.lib.base.BaseActivity1;
import com.example.lib.utils.FileUtil;
import com.example.lib.utils.ImageUtil;
import com.example.lib.utils.PermissionInterface;
import com.example.lib.utils.PermissionUtils;
import com.example.lib.utils.ToastUtils;
import com.example.module_personal.R;
import com.example.module_personal.databinding.ActivityPersonalAccountMessageBinding;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonalAccountMessageActivity extends BaseActivity1<ActivityPersonalAccountMessageBinding> implements PermissionInterface {
    private static final int REQUEST_NICKNAME=1;
    private static final int REQUEST_CAMERA=2;
    private static final int REQUEST_PHOTO=3;
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_account_message;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBinding.lnivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,PersonalMessageMoreActivity.class);
                startActivity(intent);
            }
        });
        mBinding.lnivNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,PersonalNickNameActivity.class);
                intent.putExtra("nickName",mBinding.lnivNickname.getText());
                startActivityForResult(intent,REQUEST_NICKNAME);
            }
        });
        mBinding.lnivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TieBean> strings = new ArrayList<TieBean>();
                strings.add(new TieBean("拍摄"));
                strings.add(new TieBean("从手机相机选取"));
                DialogUIUtils.showSheet(mContext, strings, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        switch (position){
                            case 0:
                                PermissionUtils.checkPermissionActivity(PersonalAccountMessageActivity.this,mBinding.lnivHeader,new String[]{ Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},100,PersonalAccountMessageActivity.this);
                                break;
                            case 1:
                                PermissionUtils.checkPermissionActivity(PersonalAccountMessageActivity.this,mBinding.lnivHeader,new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},200,PersonalAccountMessageActivity.this);
                                break;
                        }
                    }

                    @Override
                    public void onBottomBtnClick() {

                    }
                }).show();
            }
        });
    }
    /**
     * 打开照相机
     */
    public static File tempFile;
    private Uri photoUri;
    private String imgName;
    private String imgString; //
    private String avatarName = "";//需要提交的头像名称
    private String fingerprint_img = "";

    private void Camera(){
        FileUtil.createSDCardDir();
        Date date1 = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss");
        imgName = dateFormat1.format(date1);
        imgString = FileUtil.SDPATH + imgName + ".jpeg";
        File cameraPhoto = new File(imgString);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoUri = FileProvider.getUriForFile(
                baseActivity,
                baseActivity.getPackageName()+".fileprovider",
                cameraPhoto);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(takePhotoIntent, REQUEST_CAMERA);
    }
    /**
     * 从相册中选择图片
     */
    private void Storage(){
        FileUtil.createSDCardDir();
        Date date1 = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss");
        imgName = dateFormat1.format(date1);
        imgString = FileUtil.SDPATH + imgName + ".jpeg";

        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent1, REQUEST_PHOTO);
//        mPopupWindowAvatar.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_NICKNAME:
                if(resultCode==RESULT_OK){
                    mBinding.lnivNickname.setText(data.getStringExtra("nickName"));
                }
                break;
            case REQUEST_CAMERA:
//                File file = new File(imgString);
//                Bitmap photo = ImageUtil.getBitMBitmap(imgString);
//                if (photo!=null){
//                    img_avatar.setImageBitmap(photo);
//                    ImageUtil.compressImage(photo, file, 30);
//                    uploadImage(file,0);
//                }
                break;
            case REQUEST_PHOTO:
//                File file = new File(imgString);
//                if(data!=null && data.getData()!=null){
//                    Bitmap photo = ImageUtil.getBitmapFromUri(getActivity(), data.getData());
//                    if (photo!=null){
//                        img_avatar.setImageBitmap(photo);
//                        ImageUtil.compressImage(photo, file, 30);
//                        uploadImage(file,0);
//                    }
//                }
                break;
        }
    }

    @Override
    public void success(int requestCode) {

    }

    @Override
    public void errer(int requestCode) {

    }
}
