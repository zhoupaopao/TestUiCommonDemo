package com.example.lib.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

//import com.arch.demo.core.R;
//import com.arch.demo.core.loadsir.EmptyCallback;
//import com.arch.demo.core.loadsir.ErrorCallback;
//import com.arch.demo.core.loadsir.LoadingCallback;
//import com.arch.demo.core.utils.AppManager;
//import com.arch.demo.core.viewmodel.IMvvmBaseViewModel;
import com.example.lib.R;
import com.example.lib.loadsir.EmptyCallback;
import com.example.lib.loadsir.ErrorCallback;
import com.example.lib.loadsir.LoadingCallback;
import com.example.lib.viewmodel.IMvvmBaseViewModel;
import com.example.lib.viewmodel.MvvmBaseViewModel;
import com.gyf.immersionbar.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
//import com.kingja.loadsir.callback.Callback;
//import com.kingja.loadsir.core.LoadService;
//import com.kingja.loadsir.core.LoadSir;


public abstract class MvvmActivity<V extends ViewDataBinding, VM extends MvvmBaseViewModel> extends AppCompatActivity implements IBaseView {
    protected VM viewModel;
    private LoadService mLoadService;
    protected V viewDataBinding;
    private ProgressDialog mProgressDialog;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.white).statusBarDarkFont(true, 0.2f).init();
        initParameters();
        initViewModel();
        performDataBinding();
        initInternalObserver();

        mContext = this;
        initListener();
        initData();
        ViewManager.getInstance().addActivity(this);
    }
    /***
     *   初始化参数
     */
    protected void initParameters() {

    }
     /** 初始化数据 */

    protected abstract void initListener();
    protected abstract void initData();
     private void initViewModel() {
        viewModel = getViewModel();
        if(viewModel != null) {
            viewModel.attachUI(this);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null && viewModel.isUIAttached()) {
            viewModel.detachUI();
        }
        if(mProgressDialog!=null&&mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        ViewManager.getInstance().finishActivity(this);
    }

    @Override
    public void onRefreshEmpty() {
        if(mLoadService != null) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }
    @Override
    public void onRefreshFailure(String message) {
        if(mLoadService != null) {
            mLoadService.showCallback(ErrorCallback.class);
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }
    @Override
    public void showLoadingDialog(String hint) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        mProgressDialog.setMessage(hint);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }
    @Override
    public void hideLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
    @Override
    public void showContent() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }

    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
        showContent();
    }

    protected abstract void onRetryBtnClick();

    protected abstract VM getViewModel();

    public abstract int getBindingVariable();

    public abstract
    @LayoutRes
    int getLayoutId();

    private void performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.viewModel = viewModel == null ? getViewModel() : viewModel;
        if(getBindingVariable() > 0) {
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
        }
        viewDataBinding.executePendingBindings();
        viewDataBinding.setLifecycleOwner(this);
    }


    protected  void initInternalObserver(){
        viewModel.loadingEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean o) {
                if(o){
                    showLoadingDialog("加载中");
                }else{
                    hideLoadingDialog();
                }
            }
        });
    }

}
