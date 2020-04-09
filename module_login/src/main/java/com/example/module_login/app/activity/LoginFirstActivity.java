package com.example.module_login.app.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib.base.BaseActivity;
import com.example.lib.http.HttpClient;
import com.example.lib.http.ProgressDialogSubscriber;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib_resource.bean.TokenBean;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.R;
import com.example.module_login.databinding.ActivityLoginFirstBinding;
import com.example.module_login.http.ApiService;
import com.example.module_login.view.LoginLinearlayout;
import com.example.module_login.viewmodel.LoginLinearLayoutViewModel;
import com.example.module_login.viewmodel.LoginMainViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.king.base.util.ToastUtils;


import okhttp3.FormBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Route(path = ARouterConstants.Login_First_Activity)
public class LoginFirstActivity extends BaseActivity {
    private LoginLinearlayout login_ll;
    private LoginLinearLayoutViewModel viewModel;
    private ActivityLoginFirstBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=View.inflate(this,R.layout.activity_login_first,null);
        binding=DataBindingUtil.bind(view);
        viewModel=binding.loginLl.viewModel;
        binding.setData(viewModel);
        binding.setLifecycleOwner(this);
        binding.loginLl.tv_forgetpsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("initData: ", "tokenBean.getAccess_token()");
//                SharedPrefUtil.putUsername("312132312");
//                SharedPrefUtil.putPassword("312132312");
//                SharedPrefUtil.putRemPassword(false);
//                Log.i("initData", SharedPrefUtil.getUsername());
            }
        });
        binding.loginLl.btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody requestBody = new FormBody.Builder()
                        .add("username", viewModel.getUserName().getValue())
                        .add("password", viewModel.getUserPassword().getValue())
                        .add("grant_type", "password")
                        .add("scope", "all")
                        .build();
                viewModel.login(requestBody,LoginFirstActivity.this);
            }
        });
        binding.loginLl.tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterConstants.Login_Register_Activity).navigation();
            }
        });
//        login_ll=findViewById(R.id.login_ll);
//        login_ll.btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        initData();
    }

    private void initData() {
        //让TextView观察ViewModel中数据的变化,并实时展示
        viewModel.getSource().observe(this, new Observer<TokenBean>() {
            @Override
            public void onChanged(TokenBean user) {
                Log.i("initData2: ", user.getAccess_token());
                //成功
                binding.loginLl.remember_check(user,viewModel.getUserName().getValue(),viewModel.getUserPassword().getValue());
                ARouter.getInstance().build(ARouterConstants.Home_Activity).navigation();
            }
        });
        //添加registerMessageEvent后使用方式,推荐
        viewModel.getMessageEvent().observe(this, (Observer<? super String>) message -> {
            ToastUtils.showToast(this, message);
        });
    }
}
