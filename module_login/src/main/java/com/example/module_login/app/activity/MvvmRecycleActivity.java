package com.example.module_login.app.activity;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib.base.MvvmActivity;
import com.example.lib_resource.bean.Student;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.BR;
import com.example.module_login.R;
import com.example.module_login.adapter.MvvmRecycleAdapter;
import com.example.module_login.databinding.ActivityMvvmRecycleviewBinding;
import com.example.module_login.databinding.ActivityTestMvvmBinding;
import com.example.module_login.viewmodel.RecycleMvvmViewModel;
import com.example.module_login.viewmodel.TextMvvmViewModel;

@Route(path = ARouterConstants.Login_MvvmRecycle_Activity)
public class MvvmRecycleActivity extends MvvmActivity<ActivityMvvmRecycleviewBinding, RecycleMvvmViewModel> implements RecycleMvvmViewModel.CallBack{
    MvvmRecycleAdapter<Student> adapter;
    @Override
    protected void initData() {
        adapter=new MvvmRecycleAdapter<>(
                this,
                getLayoutInflater(),
                R.layout.item_list_test_mvvm,
                BR.student,
                viewModel.getStudents()
        );
        viewDataBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.recycleView.setAdapter(adapter);
        viewModel.getMessage();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected RecycleMvvmViewModel getViewModel() {
        return new RecycleMvvmViewModel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mvvm_recycleview;
    }
    @Override
    public void listAdapter() {
        adapter.notifyDataSetChanged();
    }
}
