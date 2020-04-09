package com.example.module_personal.app.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.lib.base.BaseActivity1;
import com.example.lib.utils.ToastUtils;
import com.example.module_personal.R;
import com.example.module_personal.databinding.ActivityPersonalChangePasswordBinding;
import com.example.module_personal.viewmodel.PersonalChangeMobelViewModel;
import com.example.module_personal.viewmodel.PersonalChangePasswordViewModel;

public class PersonalChangePasswordActivity extends BaseActivity1<ActivityPersonalChangePasswordBinding> {
    PersonalChangePasswordViewModel viewModel;
    private String beforeText="";
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_change_password;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(PersonalChangePasswordViewModel.class);
        mBinding.setData(viewModel);
        mBinding.setLifecycleOwner(this);
    }

    @Override
    public void initListener() {

        mBinding.title.getTv_edit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_password=viewModel.getNew_password().getValue();
                String old_password=viewModel.getOld_password().getValue();
                String sure_password=viewModel.getSure_password().getValue();
                if(new_password.equals("")||old_password.equals("")||sure_password.equals("")){
                    ToastUtils.showShortToast("密码不能为空");
                    return;
                }
                if(!new_password.equals(sure_password)){
                    ToastUtils.showShortToast("新密码不一致");
                    return;
                }
                ToastUtils.showShortToast("修改成功");
                finish();
            }
        });
    }
}
