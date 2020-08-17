package com.example.module_login.app.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib.base.BaseActivity1;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.R;
import com.example.module_login.databinding.ActivityLoginForgetPasswordBinding;
@Route(path = ARouterConstants.Login_ForgetPassword_Activity)
public class LoginForgetPasswordActivity extends BaseActivity1<ActivityLoginForgetPasswordBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_login_forget_password;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBinding.btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterConstants.Login_VerifyCode_Activity).withString("mobile_num",mBinding.etMobile.getText().toString()).withString("type","forget").navigation();

            }
        });
    }
}
