package com.example.module_personal.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lib.base.BaseActivity1;
import com.example.module_personal.R;
import com.example.module_personal.databinding.ActivityPersonalSignNameBinding;

public class PersonalSignNameActivity extends BaseActivity1<ActivityPersonalSignNameBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_sign_name;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
        mBinding.twllTitle.getTv_edit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("name",mBinding.editName.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
