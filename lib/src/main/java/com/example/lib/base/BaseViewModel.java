package com.example.lib.base;

import android.app.Application;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.lib.base.livedata.MessageEvent;
import com.example.lib.base.livedata.SingleLiveEvent;
import com.example.lib.base.livedata.StatusEvent;


/**
 * 标准MVVM模式中的VM (ViewModel)层基类
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class BaseViewModel extends ViewModel implements IViewModel{



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
//        if(mModel != null){
//            mModel.onDestroy();
//            mModel = null;
//        }
        mMessageEvent.call();
    }
    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
    }
//    /**
//     * {@link BaseModel}
//     * @return {@link #mModel}
//     */
//    public BaseModel getModel(){
//        return this.mModel;
//    }

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

//    /**
//     * 也可通过观察{@link #getMessageEvent()}接收消息事件
//     * @param msgId 资源文件id
//     */
//    public void sendMessage(@StringRes int msgId) {
//        mMessageEvent.setValue(getApplication().getString(msgId));
//    }
}
