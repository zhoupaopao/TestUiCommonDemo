package com.example.lib.base;


import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dou361.dialogui.DialogUIUtils;
import com.example.lib.R;
import com.example.lib.http.IProgressDialogAction;
import com.example.lib.utils.Utils;
import com.gyf.immersionbar.ImmersionBar;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * <p>Activity基类 </p>
 *
 * @author 2016/12/2 17:33
 * @version V1.0.0
 * @name BaseActivity
 */
@Keep
public abstract class BaseActivity1<VDB extends ViewDataBinding> extends AppCompatActivity implements IViewNoModel,IProgressDialogAction {
    protected  String TAG;
    private Dialog dialog;
    public Context mContext;
    public BaseActivity1 baseActivity;
    protected VDB mBinding;


    /**
     * 主线程Handler
     */
    private Handler handler = new Handler(Looper.getMainLooper());
    /**
     * 封装的findViewByID方法
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewManager.getInstance().addActivity(this);
        //初始化注解
        ARouter.getInstance().inject(this);
        mContext = this;
        baseActivity=this;
        ImmersionBar.with(this).statusBarColor(R.color.bg_main).init();

        if(isBinding()){
            mBinding = DataBindingUtil.setContentView(this,getLayoutId());
        }else{
            setContentView(getLayoutId());
        }
        TAG=getRunningActivityName();
//        BaseApplication.addActivity(this);
        initData(savedInstanceState);
        initListener();
    }

    @Override
    public void initListener() {

    }

    /**
     * 是否使用DataBinding
     * @return  默认为true 表示使用。如果为false，则不会初始化{@link #mBinding}。
     */
    @Override
    public boolean isBinding(){
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewManager.getInstance().finishActivity(this);
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private String getRunningActivityName(){
        ActivityManager activityManager=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }




    /**
     * 添加fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .add(frameId, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();

    }


    /**
     * 替换fragment
     * @param fragment
     * @param frameId
     */
    protected void replaceFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .replace(frameId, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();

    }


    /**
     * 隐藏fragment
     * @param fragment
     */
    protected void hideFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .hide(fragment)
                .commitAllowingStateLoss();

    }


    /**
     * 显示fragment
     * @param fragment
     */
    protected void showFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .show(fragment)
                .commitAllowingStateLoss();

    }


    /**
     * 移除fragment
     * @param fragment
     */
    protected void removeFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commitAllowingStateLoss();

    }


    /**
     * 弹出栈顶部的Fragment
     */
    protected void popFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void showProgressDialog() {
        showProgressDialog(true,true);

    }

    public void showProgressDialog(boolean cancelable,boolean outsidecancel) {
        if (dialog == null) {
             dialog= DialogUIUtils.showLoading(this,"加载中",true,cancelable,outsidecancel,false).show();
        } else {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
//        if (customDialog == null) {
//            customDialog = new CustomDialog(this, R.style.CustomDialog);
//            customDialog.show();
//        } else {
//            if (!customDialog.isShowing()) {
//                customDialog.show();
//            }
//        }

    }

    @Override
    public void dismissProgressDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void tip(final String str) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
