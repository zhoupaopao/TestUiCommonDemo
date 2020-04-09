package com.example.lib.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.example.lib.R;
import com.example.lib.utils.Utils;

import java.util.List;


/**
 * <p>Fragment的基类</p>
 *
 * @author 张华洋
 * @name BaseFragment
 */
@Keep
public abstract class BaseFragment<VDB extends ViewDataBinding> extends Fragment implements IViewNoModel {
    private Context context;
    protected BaseFragmentActivity mActivity;
    protected  String TAG;
    protected VDB mBinding;
    protected View mRootView;
    /**
     * 主线程Handler
     */
    private Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseFragmentActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(),container,false);
        if(isBinding()){
            mBinding = DataBindingUtil.bind(mRootView);
        }
        initData(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public boolean isBinding(){
        return true;
    }

    private String getRunningActivityName(){
        ActivityManager activityManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

    /**
     * 获取宿主Activity
     *
     * @return BaseActivity
     */
    protected BaseFragmentActivity getHoldingActivity() {
        return mActivity;
    }


    /**
     * 添加fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().addFragment(fragment, frameId);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (getChildFragmentManager().getFragments() != null && getChildFragmentManager().getFragments().size() > 0){
            List<Fragment> fragments = getChildFragmentManager().getFragments();
            for (Fragment mFragment: fragments) {
                mFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
    /**
     * 替换fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void replaceFragment(BaseFragment fragment, @IdRes int frameId) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().replaceFragment(fragment, frameId);
    }


    /**
     * 隐藏fragment
     *
     * @param fragment
     */
    protected void hideFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().hideFragment(fragment);
    }


    /**
     * 显示fragment
     *
     * @param fragment
     */
    protected void showFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().showFragment(fragment);
    }


    /**
     * 移除Fragment
     *
     * @param fragment
     */
    protected void removeFragment(BaseFragment fragment) {
        Utils.checkNotNull(fragment);
        getHoldingActivity().removeFragment(fragment);

    }


    /**
     * 弹出栈顶部的Fragment
     */
    protected void popFragment() {
        getHoldingActivity().popFragment();
    }

    /*
     * fragment中的返回键
     *
     * 默认返回flase，交给Activity处理
     * 返回true：执行fragment中需要执行的逻辑
     * 返回false：执行activity中的 onBackPressed
     * */
    public boolean onBackPressed() {
        return false;
    }
//    /**
//     * 设置左边标题
//     * @param leftTitleText
//     */
//    protected void setLeftTitleText(View view, String leftTitleText){
//        TextView txTitle = (TextView) view.findViewById(R.id.tvTitle);
//        if (txTitle != null) {
//            txTitle.setText(leftTitleText);
//        }
//    }

    public void tip(final int strId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, strId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void tip(final String str) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initListener() {

    }
}
