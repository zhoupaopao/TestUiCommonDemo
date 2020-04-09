package com.example.module_personal.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lib.base.BaseActivity1;
import com.example.module_personal.R;
import com.example.module_personal.databinding.ActivityPersonalSettingSafeBinding;

public class PersonalSettingSafeActivity extends BaseActivity1<ActivityPersonalSettingSafeBinding> {
    private final int MOBILE_REQUEST_CODE=1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_setting_safe;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBinding.lnivMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalSettingSafeActivity.this,PersonalChangeMobileActivity.class);
                startActivityForResult(intent,MOBILE_REQUEST_CODE);
            }
        });
        mBinding.lnivChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalSettingSafeActivity.this,PersonalChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case MOBILE_REQUEST_CODE:
                if(resultCode==RESULT_OK){
                    mBinding.lnivMobile.setText(data.getStringExtra("mobile"));
                }
                break;
        }
    }
}
