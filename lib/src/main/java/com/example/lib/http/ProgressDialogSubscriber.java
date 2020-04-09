package com.example.lib.http;


import android.util.Log;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * @Description: 显示进度条的订阅者
 * @author: zhh
 * @date: 2017/3/7 10:52
 */
public abstract class ProgressDialogSubscriber<T> extends Subscriber<T> {

    private IProgressDialogAction progressDialogAction;

    public boolean isShowprogress = true;

    public ProgressDialogSubscriber() {
//        this.progressDialogAction = progressDialogAction;
    }
    public ProgressDialogSubscriber(IProgressDialogAction progressDialogAction) {
        this.progressDialogAction = progressDialogAction;
    }

    public ProgressDialogSubscriber(IProgressDialogAction progressDialogAction, boolean isShowprogress) {
        this.progressDialogAction = progressDialogAction;
    }


    @Override
    public void onStart() {
        if (isShowprogress)
            if (progressDialogAction != null)
                progressDialogAction.showProgressDialog();

    }


    @Override
    public void onCompleted() {
        if (progressDialogAction != null)
            progressDialogAction.dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
//        Log.w("json", e.toString());
        if (progressDialogAction != null)
            progressDialogAction.dismissProgressDialog();
//        Log.d("ProgressDialogSubscribe", e.getMessage());
//        Log.d("ProgressDialogSubscribe", "e.getCause():" + e.getCause());
//
//
//        String error = ApiException.handleException(e).getMessage();
//        Log.e("zzz", error);

        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            if (httpException.code() == 401) {
                //执行刷新token

//                BaseApplication.getInstance().doReLogin();
            }
        }
    }


    @Override
    public void onNext(T t) {
        Log.d("json1", t.toString());
    }


}
