package com.example.lib.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

/**
 * 用来规范{@link BaseActivity} 和{@link BaseFragment} 风格。
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface IViewNoModel {

    /**
     * 根布局id
     * @return
     */
    @LayoutRes
    int getLayoutId();

    /**
     * 初始化数据
     * @param savedInstanceState
     */
    void initData(@Nullable Bundle savedInstanceState);

    /**
     * 是否使用DataBinding
     * @return
     */
    boolean isBinding();

    /**
     * 处理监听
     */
    void initListener();



}
