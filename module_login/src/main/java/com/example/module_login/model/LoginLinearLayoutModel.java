package com.example.module_login.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lib.base.BaseActivity;
import com.example.lib.base.BaseActivity1;
import com.example.lib.base.BaseModel;
import com.example.lib.bean.Resource;
import com.example.lib.http.ApiCallback;
import com.example.lib.http.HttpClient;
import com.example.lib.http.ProgressDialogSubscriber;
import com.example.lib_resource.bean.TokenBean;
import com.example.module_login.app.activity.LoginFirstActivity;
import com.example.module_login.http.ApiService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginLinearLayoutModel extends BaseModel {
    private MutableLiveData<Resource<TokenBean>> tokenLiveData = new MutableLiveData<>();

    //11232131221212
    public LiveData<Resource<TokenBean>> getTokenInfo(RequestBody requestBody){
        tokenLiveData.setValue(Resource.loading());
        HttpClient.createApi(ApiService.class)
                .token(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressDialogSubscriber<JsonObject>() {
                               @Override
                               public void onNext(JsonObject data) {
                                   super.onNext(data);
                                   TokenBean mBean = new Gson().fromJson(data.toString(), TokenBean.class);
                                   tokenLiveData.setValue(Resource.success(mBean));
//                                   tokenLiveData.setValue(mBean); //binding.loginLl.remember_check(mBean,viewModel.getUserName().getValue(),viewModel.getUserPassword().getValue());
                                   return;
                               }

                               @Override
                               public void onError(Throwable e) {
                                   super.onError(e);
//                                   e.printStackTrace();
                                   tokenLiveData.setValue(Resource.error(e));
                               }
                           }
                );
        return tokenLiveData;
    }
}
