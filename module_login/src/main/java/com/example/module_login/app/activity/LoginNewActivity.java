package com.example.module_login.app.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib.base.BaseActivity1;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib_resource.bean.TokenBean;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.R;
import com.example.module_login.databinding.ActivityNewLoginBinding;
import com.example.module_login.viewmodel.LoginLinearLayoutViewModel;
import com.king.base.util.ToastUtils;

import okhttp3.FormBody;
import okhttp3.RequestBody;

@Route(path = ARouterConstants.Login_New_Activity)
public class LoginNewActivity  extends BaseActivity1<ActivityNewLoginBinding> {
    private LoginLinearLayoutViewModel viewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(LoginLinearLayoutViewModel.class);
        mBinding.setData(viewModel);
        mBinding.setLifecycleOwner(this);

    }

    @Override
    public void initListener() {
        mBinding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody requestBody = new FormBody.Builder()
                        .add("username", viewModel.getUserName().getValue())
                        .add("password", viewModel.getUserPassword().getValue())
                        .add("grant_type", "password")
                        .add("scope", "all")
                        .build();
                viewModel.login(requestBody,LoginNewActivity.this);
            }
        });
        mBinding.iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.clearUserName();
            }
        });
        viewModel.getSource().observe(this, new Observer<TokenBean>() {
            @Override
            public void onChanged(TokenBean user) {
                Log.i("initData2: ", user.getAccess_token());
                //成功
                remember_check(user,viewModel.getUserName().getValue(),viewModel.getUserPassword().getValue());
                ARouter.getInstance().build(ARouterConstants.Home_Activity).navigation();
            }
        });
        //添加registerMessageEvent后使用方式,推荐
        viewModel.getMessageEvent().observe(this, (Observer<? super String>) message -> {
            ToastUtils.showToast(this, message);
        });
        viewModel.getUserName().observe(this, new Observer<String>() {
            //监听账号名
            @Override
            public void onChanged(String s) {
                if(s.equals("")){
                    mBinding.iv1.setVisibility(View.GONE);
                }else{
                    mBinding.iv1.setVisibility(View.VISIBLE);
                }
            }
        });
        mBinding.tvForgetpsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterConstants.Login_ForgetPassword_Activity).navigation();
            }
        });
        mBinding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterConstants.Login_Register_Activity).navigation();
            }
        });
    }
    public void remember_check(TokenBean mBean, String username, String password) {
        //对勾选的进行记录
        SharedPrefUtil.putRefrshToken(mBean.getRefresh_token());
        SharedPrefUtil.putToken(mBean.getAccess_token());
        SharedPrefUtil.putTokenBean(mBean);
        SharedPrefUtil.putUsername(username);
//        SharedPrefUtil.putRemPassword(binding.cbRem.isChecked());
//        if (binding.cbRem.isChecked()) {
//            SharedPrefUtil.putPassword(password);
//        } else {
//            SharedPrefUtil.removePassword();
//        }
//        if (binding.cbAutologin.isChecked()) {
//            SharedPrefUtil.putAutoLogin(true);
//        } else {
//            SharedPrefUtil.removeAutoLogin();
//        }
    }
}
