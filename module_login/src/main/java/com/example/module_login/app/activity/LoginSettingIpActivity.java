package com.example.module_login.app.activity;

import androidx.lifecycle.Observer;

import com.example.lib.base.MvvmActivity;
import com.example.module_login.BR;
import com.example.module_login.R;
import com.example.module_login.databinding.ActivityLoginIpSettingBinding;
import com.example.module_login.viewmodel.LoginIpSettingViewModel;

public class LoginSettingIpActivity extends MvvmActivity<ActivityLoginIpSettingBinding,LoginIpSettingViewModel> {
    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        viewModel.getFinishPage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("1")){
                    finish();
                }
            }
        });
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected LoginIpSettingViewModel getViewModel() {
        return new LoginIpSettingViewModel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_ip_setting;
    }
}
