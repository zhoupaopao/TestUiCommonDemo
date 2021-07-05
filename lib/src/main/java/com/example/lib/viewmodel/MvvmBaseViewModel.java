package com.example.lib.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;


/**
 * @author Administrator
 */
public class MvvmBaseViewModel<V> extends ViewModel implements IMvvmBaseViewModel<V> {
    private Reference<V> mUIRef;
//    protected M model;
public MutableLiveData<Boolean> loadingEvent=new MutableLiveData<>();//控制加载显示

    @Override
    public void attachUI(V ui) {
        mUIRef = new WeakReference<>(ui);
    }

    @Override
    @Nullable
    public V getPageView() {
        if (mUIRef == null) {
            return null;
        }
        return mUIRef.get();
    }

    @Override
    public boolean isUIAttached() {
        return mUIRef != null && mUIRef.get() != null;
    }

    @Override
    public void detachUI() {
        if (mUIRef != null) {
            mUIRef.clear();
            mUIRef = null;

        }
    }

    @Override
    public void showLoading(Boolean isShow) {
        loadingEvent.postValue(isShow);
    }
}
