package com.example.module_homepage.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib.base.BaseFragment;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_homepage.R;
import com.example.module_homepage.app.activity.HomeListActivity;
import com.example.module_homepage.databinding.FragmentHomePageMainBinding;

@Route(path = ARouterConstants.Home_Page_Main_Fragment)
public class HomePageMainFragment extends BaseFragment<FragmentHomePageMainBinding> {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        Intent intent=new Intent(getActivity(), HomeListActivity.class);
//        startActivity(intent);
    }

    @Override
    public void initListener() {

    }
}
