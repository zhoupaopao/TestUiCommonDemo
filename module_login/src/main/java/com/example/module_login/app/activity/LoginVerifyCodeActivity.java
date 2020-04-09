package com.example.module_login.app.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib.base.BaseActivity1;
import com.example.lib.utils.ToastUtils;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.R;
import com.example.module_login.databinding.ActivityLoginRegisterBinding;
import com.example.module_login.databinding.ActivityLoginVerifycodeBinding;
import com.example.module_login.viewmodel.LoginRegisterViewModel;

@Route(path = ARouterConstants.Login_VerifyCode_Activity)
public class LoginVerifyCodeActivity extends BaseActivity1<ActivityLoginVerifycodeBinding> {
    @Autowired
    String mobile_num;
    private LoginRegisterViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_verifycode;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBinding.tvPhone.setText(mobile_num);
        mBinding.btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast(mBinding.codeView.getContent());
            }
        });
    }
}
