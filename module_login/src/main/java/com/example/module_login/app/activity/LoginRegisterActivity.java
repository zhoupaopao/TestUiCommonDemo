package com.example.module_login.app.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib.base.BaseActivity;
import com.example.lib.base.BaseActivity1;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.R;
import com.example.module_login.databinding.ActivityLoginRegisterBinding;
import com.example.module_login.viewmodel.LoginRegisterViewModel;

@Route(path = ARouterConstants.Login_Register_Activity)
public class LoginRegisterActivity extends BaseActivity1<ActivityLoginRegisterBinding> {
    private LoginRegisterViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_register;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(LoginRegisterViewModel.class);
        mBinding.setData(viewModel);
        mBinding.setLifecycleOwner(this);
        mBinding.btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                ARouter.getInstance().build(ARouterConstants.Login_VerifyCode_Activity).withString("mobile_num",viewModel.getMobil_num().getValue()).withString("type","setting").navigation();

            }
        });
    }
}
