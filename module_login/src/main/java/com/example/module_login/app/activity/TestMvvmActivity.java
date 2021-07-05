package com.example.module_login.app.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib.base.MvvmActivity;
import com.example.lib.utils.ImageUtil;
import com.example.lib_resource.bean.Student;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.BR;
import com.example.module_login.R;
import com.example.module_login.adapter.TestMvvmAdapter;
import com.example.module_login.databinding.ActivityTestMvvmBinding;
import com.example.module_login.viewmodel.TextMvvmViewModel;


@Route(path = ARouterConstants.Login_Test_Activity)
public class TestMvvmActivity extends MvvmActivity<ActivityTestMvvmBinding, TextMvvmViewModel>implements TextMvvmViewModel.CallBack {
    TestMvvmAdapter<Student>adapter;
    @Override
    protected void initData() {
       adapter=new TestMvvmAdapter<>(this,
                getLayoutInflater(),
                R.layout.item_list_test_mvvm,
                BR.student,
                viewModel.getStudents());
        viewDataBinding.listview.setAdapter(adapter);
        viewModel.getMessage();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected TextMvvmViewModel getViewModel() {
        return new TextMvvmViewModel();
    }

    @Override
    public int getBindingVariable() {
        return  BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_mvvm;
    }

    @Override
    public void listAdapter() {
//        adapter=new TestMvvmAdapter<>(this,
//                getLayoutInflater(),
//                R.layout.item_list_test_mvvm,
//                BR.student,
//                viewModel.getStudents());
//        viewDataBinding.listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
