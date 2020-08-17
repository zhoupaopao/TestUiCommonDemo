package com.example.module_login.app.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib.base.BaseActivity1;
import com.example.lib.base.ViewManager;
import com.example.lib.utils.ToastUtils;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.R;
import com.example.module_login.databinding.ActivityLoginSetPasswordBinding;
import com.example.module_login.viewmodel.LoginLinearLayoutViewModel;
import com.example.module_login.viewmodel.LoginSetPasswordViewModel;

@Route(path = ARouterConstants.Login_SetPassword_Activity)
public class LoginSetPasswordActivity extends BaseActivity1<ActivityLoginSetPasswordBinding> {
    LoginSetPasswordViewModel viewModel;
    @Autowired
    String type;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login_set_password;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(LoginSetPasswordViewModel.class);
        mBinding.setData(viewModel);
        mBinding.setLifecycleOwner(this);
    }

    @Override
    public void initListener() {
        super.initListener();
        mBinding.btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast(viewModel.getPassword().getValue());
                if(type.equals("forget")){
                    //忘记密码过来的
                    ViewManager.getInstance().reLogin();
                }else if(type.equals("setting")){
                    //注册过来的
                    ARouter.getInstance().build(ARouterConstants.Login_PersonalSetting_Activity).navigation();
                }


            }
        });
    }
}
