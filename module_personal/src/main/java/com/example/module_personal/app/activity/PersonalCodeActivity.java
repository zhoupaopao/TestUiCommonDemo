package com.example.module_personal.app.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.example.lib.base.BaseActivity1;
import com.example.lib.utils.EncodingUtils;
import com.example.lib.utils.ImageUtil;
import com.example.lib.utils.PermissionUtils;
import com.example.lib.view.BottomDialogView;
import com.example.module_personal.R;
import com.example.module_personal.databinding.ActivityPersonalCodeBinding;
import com.example.module_personal.viewmodel.PersonalChangeMobelViewModel;
import com.example.module_personal.viewmodel.PersonalCodeViewModel;
import com.example.module_personal.viewmodel.PersonalTestViewModel;
import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.List;

public class PersonalCodeActivity extends BaseActivity1<ActivityPersonalCodeBinding> {
//    String id="rroajsidnb3232bchasodji";
    Bitmap qrCode = null;
    PersonalCodeViewModel viewModel;
//PersonalTestViewModel viewModel;
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_code;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

//        viewModel=new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(PersonalCodeViewModel.class);
//        mBinding.setData(viewModel);
//        mBinding.setLifecycleOwner(this);
        viewModel=new ViewModelProvider(this,new SavedStateViewModelFactory(getApplication(),this)).get(PersonalCodeViewModel.class);
        //  myViewModel= new ViewModelProvider(this,new SavedStateViewModelFactory(this)).get(MyViewModel.class);
        mBinding.setData(viewModel);
        mBinding.setLifecycleOwner(this);


    }
    private void achieveCode(){

        try {
            qrCode = EncodingUtils.createCode(PersonalCodeActivity.this,viewModel.getCode_name().getValue());
            viewModel.setImageCode(qrCode);
//            mBinding.ivCode.setImageBitmap(qrCode);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        achieveCode();
        mBinding.title.getTv_edit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomDialogView bottomDialogView = new BottomDialogView(PersonalCodeActivity.this, "保存图片", "重置二维码", "取消", v);
                bottomDialogView.setClicklistener(new BottomDialogView.DialogClickListenerInterface() {
                    @Override
                    public void doFirst() {
                        ImageUtil.saveBitmap(qrCode,PersonalCodeActivity.this);
                        tip("保存成功");
                    }
                    @Override
                    public void doSecond() {
                        viewModel.getCode_name().setValue(viewModel.getCode_name().getValue()+"aa");
                        achieveCode();
                    }
                    @Override
                    public void doCancel() {
                        bottomDialogView.getmPopupWindowAvatar().dismiss();
                    }
                });
                bottomDialogView.showAsDropDown(v);

//                List<TieBean> strings = new ArrayList<TieBean>();
//                strings.add(new TieBean("保存图片"));
//                strings.add(new TieBean("重置二维码"));
//                DialogUIUtils.showSheet(mContext, strings, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
//                    @Override
//                    public void onItemClick(CharSequence text, int position) {
//                        showProgressDialog();
//                        switch (position){
//                            case 0:
////                                ImageUtil.saveBitmapFile(qrCode);
//                                ImageUtil.saveBitmap(qrCode,PersonalCodeActivity.this);
//                                tip("保存成功");
//                                break;
//                            case 1:
//                                viewModel.getCode_name().setValue(viewModel.getCode_name().getValue()+"aa");
//                                achieveCode();
//
//                                break;
//                        }
//                        dismissProgressDialog();
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
}
