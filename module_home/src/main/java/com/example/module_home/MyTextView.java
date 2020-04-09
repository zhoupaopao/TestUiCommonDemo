package com.example.module_home;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
//自定义textview组件，监听组件的生命周期
public class MyTextView extends androidx.appcompat.widget.AppCompatTextView implements LifecycleObserver {
    private static final String TAG = "MyTextView";

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void CREATEText(){
        setText("ON_CREATE");
        Log.i(TAG, "ON_CREATE: ");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void STOPText(){
        setText("ON_STOP");
        Log.i(TAG, "ON_STOP: ");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destoryText(){
        setText("ON_DESTROY");
        Log.i(TAG, "destoryText: ");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pauseText(){
        setText("ON_PAUSE");
        Log.i(TAG, "pauseText: ");
    }
}
