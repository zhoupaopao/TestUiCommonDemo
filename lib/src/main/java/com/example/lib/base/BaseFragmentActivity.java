package com.example.lib.base;


import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;

import com.dou361.dialogui.DialogUIUtils;
import com.example.lib.R;
import com.example.lib.http.IProgressDialogAction;
import com.example.lib.utils.Utils;

import java.util.List;

import static com.example.lib.utils.Utils.setStatusBarColor;


/**
 * <p>Activity基类 </p>
 *
 * @author 2016/12/2 17:33
 * @version V1.0.0
 * @name BaseActivity
 */
@Keep
public abstract class BaseFragmentActivity<VDB extends ViewDataBinding> extends FragmentActivity implements IViewNoModel, IProgressDialogAction {
    protected  String TAG;
    private Dialog dialog;
    public Context mContext;

    protected VDB mBinding;
    /**
     * 封装的findViewByID方法
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }
////为了使用svg图片
//    static {
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewManager.getInstance().addActivity(this);
        setStatusBarColor(this, R.color.status_bar_color);
        //初始化注解
        ARouter.getInstance().inject(this);
        mContext = this;
//        BaseApplication.addActivity(this);
        if(isBinding()){
            mBinding = DataBindingUtil.setContentView(this,getLayoutId());
        }else{
            setContentView(getLayoutId());
        }
        TAG=getRunningActivityName();
//        BaseApplication.addActivity(this);
        initData(savedInstanceState);
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
    public boolean isBinding(){
        return true;
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    /**
     * 解决Fragment中的onActivityResult()方法无响应问题。
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 1.使用getSupportFragmentManager().getFragments()获取到当前Activity中添加的Fragment集合
         * 2.遍历Fragment集合，手动调用在当前Activity中的Fragment中的onActivityResult()方法。
         */
        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment mFragment : fragments) {
                mFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
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
    private String getRunningActivityName(){
        ActivityManager activityManager=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

    @Override
    public void initListener() {

    }
}
