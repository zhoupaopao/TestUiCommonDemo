package com.example.module_personal.app.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.example.lib.base.BaseActivity1;
import com.example.lib.http.HttpClient;
import com.example.lib.http.ProgressDialogSubscriber;
import com.example.lib.utils.FileUtil;
import com.example.lib.utils.ImageUtil;
import com.example.lib.utils.PermissionInterface;
import com.example.lib.utils.PermissionUtils;
import com.example.lib.utils.ToastUtils;
import com.example.lib.view.BottomDialogView;
import com.example.module_personal.R;
import com.example.module_personal.databinding.ActivityPersonalAccountMessageBinding;
import com.example.module_personal.http.ApiService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PersonalAccountMessageActivity extends BaseActivity1<ActivityPersonalAccountMessageBinding> implements PermissionInterface, View.OnClickListener {
    private static final int REQUEST_NICKNAME = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_PHOTO = 3;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_account_message;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBinding.lnivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PersonalMessageMoreActivity.class);
                startActivity(intent);
            }
        });
        mBinding.lnivNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PersonalNickNameActivity.class);
                intent.putExtra("nickName", mBinding.lnivNickname.getText());
                startActivityForResult(intent, REQUEST_NICKNAME);
            }
        });
        mBinding.lnivCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PersonalCodeActivity.class);
                startActivity(intent);
            }
        });
        mBinding.lnivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPopWindowUpdateAvatar(v);
                BottomDialogView bottomDialogView = new BottomDialogView(PersonalAccountMessageActivity.this, "拍照", "我的相册", "取消", v);
                bottomDialogView.setClicklistener(new BottomDialogView.DialogClickListenerInterface() {
                    @Override
                    public void doFirst() {
                        PermissionUtils.checkPermissionActivity(PersonalAccountMessageActivity.this, mBinding.lnivHeader, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100, PersonalAccountMessageActivity.this);
                    }
                    @Override
                    public void doSecond() {
                        PermissionUtils.checkPermissionActivity(PersonalAccountMessageActivity.this, mBinding.lnivHeader, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 200, PersonalAccountMessageActivity.this);
                    }
                    @Override
                    public void doCancel() {
                        bottomDialogView.getmPopupWindowAvatar().dismiss();
                    }
                });
                bottomDialogView.showAsDropDown(v);
//                List<TieBean> strings = new ArrayList<TieBean>();
//                strings.add(new TieBean("拍摄"));
//                strings.add(new TieBean("从手机相机选取"));
//                DialogUIUtils.showSheet(mContext, strings, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
//                    @Override
//                    public void onItemClick(CharSequence text, int position) {
//                        switch (position){
//                            case 0:
//                                PermissionUtils.checkPermissionActivity(PersonalAccountMessageActivity.this,mBinding.lnivHeader,new String[]{ Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},100,PersonalAccountMessageActivity.this);
//                                break;
//                            case 1:
//                                PermissionUtils.checkPermissionActivity(PersonalAccountMessageActivity.this,mBinding.lnivHeader,new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},200,PersonalAccountMessageActivity.this);
//                                break;
//                        }
//                    }
//
//                    @Override
//                    public void onBottomBtnClick() {
//
//                    }
//                }).show();
            }
        });
    }

    private PopupWindow mPopupWindowAvatar;

    //底部弹出框
    private void showPopWindowUpdateAvatar(View v) {
        View view = LayoutInflater.from(v.getContext()).inflate(
                R.layout.layout_personal_pop_bottom_dialog, null);
        mPopupWindowAvatar = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopupWindowAvatar.setBackgroundDrawable(new BitmapDrawable());
//        mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        view.findViewById(R.id.cancel_pop).setOnClickListener(this);
        view.findViewById(R.id.take_phone_tv).setOnClickListener(this);
        view.findViewById(R.id.select_phone_tv).setOnClickListener(this);
        LinearLayout ll_bg = view.findViewById(R.id.ll_bg);
        ll_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindowAvatar.dismiss();
            }
        });
        mPopupWindowAvatar.showAtLocation(v, Gravity.NO_GRAVITY, x, y + mPopupWindowAvatar.getHeight());
        mPopupWindowAvatar.showAsDropDown(v);
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

    private void Camera() {
        FileUtil.createSDCardDir();
        Date date1 = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss");
        imgName = dateFormat1.format(date1);
        imgString = FileUtil.SDPATH + imgName + ".jpeg";
        File cameraPhoto = new File(imgString);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoUri = FileProvider.getUriForFile(
                baseActivity,
                baseActivity.getPackageName() + ".fileprovider",
                cameraPhoto);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(takePhotoIntent, REQUEST_CAMERA);
    }

    /**
     * 从相册中选择图片
     */
    private void Storage() {
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
        switch (requestCode) {
            case REQUEST_NICKNAME:
                if (resultCode == RESULT_OK) {
                    mBinding.lnivNickname.setText(data.getStringExtra("nickName"));
                }
                break;
            case REQUEST_CAMERA:
                File file = new File(imgString);
                Bitmap photo = ImageUtil.getBitMBitmap(imgString);
                if (photo != null) {
                    mBinding.lnivHeader.getIv_icon().setImageBitmap(photo);
//                    ImageUtil.compressImage(photo, file, 30);
//                    uploadImage(file,0);
                }
                break;
            case REQUEST_PHOTO:
                File file1 = new File(imgString);
                if (data != null && data.getData() != null) {
                    Bitmap photo1 = ImageUtil.getBitmapFromUri(baseActivity, data.getData());
                    if (photo1 != null) {
                        mBinding.lnivHeader.getIv_icon().setImageBitmap(photo1);
//                        ImageUtil.compressImage(photo1, file1, 30);
//                        uploadImage(file1,0);
                    }
                }
                break;
        }
    }

    /**
     * 上传图片
     *
     * @param file
     */
    private void uploadImage(File file, int type) {
        //上传图片需要MultipartBody
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        HttpClient.createApi(ApiService.class)
                .upLoadImage(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressDialogSubscriber<JsonObject>(baseActivity, true) {
                               @Override
                               public void onNext(JsonObject data) {
                                   super.onNext(data);
                                   Log.d("uploadImage", data.toString());
//                                   ImageLoadBean imageLoadBean = new Gson().fromJson(data.toString(),ImageLoadBean.class);
//                                   if (imageLoadBean!=null && imageLoadBean.getData()!=null){
//                                       if (type == 0){
//                                           avatarName = imageLoadBean.getData().getPicname();
//                                       }else {
//                                           fingerprint_img = imageLoadBean.getData().getPicname();
//                                       }
//                                   }
                               }

                               @Override
                               public void onError(Throwable e) {
                                   super.onError(e);
                                   e.printStackTrace();
//                                   if (type == 0){
//                                       tip("头像上传失败，请重新选择");
//                                   }
//                                   img_avatar.setImageResource(R.mipmap.advice_image_add);
                               }
                           }
                );

    }

    @Override
    public void success(int requestCode) {
        if (requestCode == 200) {
            Storage();
        }
        if (requestCode == 100) {
            Camera();
        }
    }

    @Override
    public void errer(int requestCode) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.take_phone_tv) {
            //拍照
            PermissionUtils.checkPermissionActivity(PersonalAccountMessageActivity.this, mBinding.lnivHeader, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100, PersonalAccountMessageActivity.this);
        } else if (id == R.id.select_phone_tv) {
            //相册
            PermissionUtils.checkPermissionActivity(PersonalAccountMessageActivity.this, mBinding.lnivHeader, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 200, PersonalAccountMessageActivity.this);
        } else if (id == R.id.cancel_pop) {
            //取消
            mPopupWindowAvatar.dismiss();
        }
    }
}
