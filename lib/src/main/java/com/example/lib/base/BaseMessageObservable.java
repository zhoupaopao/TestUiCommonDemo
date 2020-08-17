package com.example.lib.base;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.example.lib.base.livedata.MessageEvent;

public class BaseMessageObservable extends BaseObservable implements IViewModel{
    /**
     * 消息事件
     */
    private MessageEvent mMessageEvent = new MessageEvent();

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        mMessageEvent.call();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    /**
     * @return {@link #mMessageEvent}
     */
    public MessageEvent getMessageEvent(){
        return mMessageEvent;
    }



    /**
     * 也可通过观察{@link #getMessageEvent()}接收消息事件
     * @param message 消息内容
     */
    public void sendMessage(String message){
        mMessageEvent.setValue(message);
    }
}
