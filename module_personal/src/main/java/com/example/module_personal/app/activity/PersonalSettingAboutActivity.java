package com.example.module_personal.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lib.base.BaseActivity;
import com.example.lib.base.BaseActivity1;
import com.example.lib.utils.Utils;
import com.example.module_personal.R;
import com.example.module_personal.databinding.ActivityPersonalSettingAboutBinding;


public class PersonalSettingAboutActivity extends BaseActivity1<ActivityPersonalSettingAboutBinding> {
    private String verson;
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_setting_about;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        verson= Utils.getAppVersionName(this);
        mBinding.tvVerson.setText("Version  "+verson);
    }

    @Override
    public void initListener() {
        mBinding.lnivSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalSettingAboutActivity.this,PersonalSettingSecretActivity.class);
                startActivity(intent);
            }
        });
    }
}
