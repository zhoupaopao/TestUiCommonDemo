package com.example.module_personal.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lib.base.BaseActivity1;
import com.example.module_personal.R;
import com.example.module_personal.databinding.ActivityPersonalNickNameBinding;

/**
 * 昵称修改
 */
public class PersonalNickNameActivity extends BaseActivity1<ActivityPersonalNickNameBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_nick_name;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBinding.cevEdit.setContent(getIntent().getStringExtra("nickName"));
    }

    @Override
    public void initListener() {
        mBinding.twllTitle.getTv_edit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("nickName",mBinding.cevEdit.getContent());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
