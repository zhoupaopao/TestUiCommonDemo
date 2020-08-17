package com.example.module_login.app.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib.base.BaseActivity1;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.R;
import com.example.module_login.databinding.ActivityLoginPersonalSettingBinding;

@Route(path = ARouterConstants.Login_PersonalSetting_Activity)
public class LoginPersonalSettingActivity  extends BaseActivity1<ActivityLoginPersonalSettingBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_login_personal_setting;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
