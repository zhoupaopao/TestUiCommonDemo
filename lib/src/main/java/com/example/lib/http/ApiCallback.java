package com.example.lib.http;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public abstract class ApiCallback<T> implements Callback<T> {
    final  String TAG="ApiCallback";
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        T result = response.body();
        Log.d(TAG,"Response:" + result);
        onResponse(call,result);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d(TAG,"Response:" + t);
        onError(call,t);
    }

    public abstract void onResponse(Call<T> call,T result);

    public abstract void onError(Call<T> call, Throwable t);
}
