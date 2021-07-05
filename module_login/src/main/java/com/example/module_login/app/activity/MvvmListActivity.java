package com.example.module_login.app.activity;

import android.os.Bundle;

import com.example.lib.base.BaseMvVmActivity;
import com.example.module_login.R;
import com.example.module_login.adapter.MvvmListAapter;
import com.example.module_login.databinding.ActivityMvvmListBinding;
import com.example.module_login.viewmodel.MvvmListVm;

public class MvvmListActivity extends BaseMvVmActivity<ActivityMvvmListBinding> {
    MvvmListVm newsListVm;
    /**
     * 新闻列表 adapter
     */
    MvvmListAapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_mvvm_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        initAdapter();
//        initVM();
    }
    /**
     * 初始化adapter
     */

}
