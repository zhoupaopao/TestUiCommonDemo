package com.example.module_login.app.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib.base.MvvmActivity;
import com.example.lib.utils.ToastUtils;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.BR;
import com.example.module_login.R;
import com.example.module_login.databinding.ActivityTeatBinding;
import com.example.module_login.viewmodel.TeatViewMdel;


public class TeatActivity extends MvvmActivity<ActivityTeatBinding, TeatViewMdel> {
    @Override
    protected void initData() {
        viewDataBinding.tvMsg.setText("edqweqeqwe");
    }

    @Override
    protected void initListener() {
viewDataBinding.btSubmit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ToastUtils.showLongToast("13123123123");
    }
});
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected TeatViewMdel getViewModel() {
        return new TeatViewMdel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_teat;
    }
}
