package com.example.module_personal.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.lib.base.BaseActivity1;
import com.example.module_personal.R;
import com.example.module_personal.databinding.ActivityPersonalChangeMobileBinding;
import com.example.module_personal.viewmodel.PersonalChangeMobelViewModel;

public class PersonalChangeMobileActivity extends BaseActivity1<ActivityPersonalChangeMobileBinding> {
    private PersonalChangeMobelViewModel viewModel;
    private final int REQURST_CODE=1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_change_mobile;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(PersonalChangeMobelViewModel.class);
        mBinding.setData(viewModel);
        mBinding.setLifecycleOwner(this);
    }

    @Override
    public void initListener() {
        mBinding.btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalChangeMobileActivity.this,PersonalVerifyCodeActivity.class);
                intent.putExtra("mobile",viewModel.getMobil_num().getValue());
                startActivityForResult(intent,REQURST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQURST_CODE:
                if(resultCode==RESULT_OK){
                    //返回页面
                    Intent intent=new Intent();
                    intent.putExtra("mobile",viewModel.getMobil_num().getValue());
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
        }
    }
}
