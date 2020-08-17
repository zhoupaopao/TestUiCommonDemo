package com.example.module_homepage.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lib.base.BaseActivity1;
import com.example.lib_resource.bean.LoginBean;
import com.example.module_homepage.viewmodel.UserBean;
import com.example.module_homepage.R;
import com.example.module_homepage.databinding.ActivityHomeListBinding;

public class HomeListActivity extends BaseActivity1<ActivityHomeListBinding> {
    private UserBean userBean;
    private LoginBean loginBean;
    @Override
    public int getLayoutId() {
        return R.layout.activity_home_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        userBean=new UserBean();
        loginBean=new LoginBean();
        userBean.setName("王大锤");
        userBean.setNote("11122223333");
        userBean.setIcon("http://img1.sycdn.imooc.com/5947c34300018d1805000062.jpg");
        loginBean.getPassword().set("password");
        mBinding.setUser(userBean);
        mBinding.setLogin(loginBean);
        mBinding.btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeListActivity.this,HomeRealListActivity.class);
                startActivity(intent);
            }
        });
    }
}
