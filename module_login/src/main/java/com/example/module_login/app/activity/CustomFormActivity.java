package com.example.module_login.app.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib.base.BaseActivity;
import com.example.lib.base.MvvmActivity;
import com.example.lib_resource.bean.CustomItemBean;
import com.example.lib_resource.bean.ExpListenerBean;
import com.example.lib_resource.bean.FromValue;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.BR;
import com.example.module_login.R;
import com.example.module_login.adapter.CustomFormAadpter;
import com.example.module_login.databinding.ActivityCustomFormBinding;
import com.example.module_login.databinding.ActivityPadCustomListBinding;
import com.example.module_login.viewmodel.CustomFormViewModel;
import com.example.module_login.viewmodel.CustomPadListViewModel;

import java.util.ArrayList;
import java.util.List;


@Route(path = ARouterConstants.Login_CustomForm_Activity)
public class CustomFormActivity extends MvvmActivity<ActivityCustomFormBinding, CustomFormViewModel> implements CustomFormViewModel.CallBack {
    CustomFormAadpter customFormAadpter;




    @Override
    protected void initData() {
        viewModel.initAllData();
    }

    @Override
    protected void initListener() {
        viewDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        customFormAadpter = new CustomFormAadpter(viewModel.getFormBeans(), true);
        viewDataBinding.recyclerview.setAdapter(customFormAadpter);
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected CustomFormViewModel getViewModel() {
        return new CustomFormViewModel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_form;
    }


    @Override
    public void listAdapter() {
        customFormAadpter.notifyDataSetChanged();
    }
}
