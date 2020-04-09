package com.example.module_login.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lib.base.BaseActivity;
import com.example.lib.base.BaseModel;
import com.example.lib.base.BaseViewModel;
import com.example.lib.base.livedata.MessageEvent;
import com.example.lib.bean.Resource;
import com.example.lib_resource.bean.TokenBean;
import com.example.module_login.model.LoginLinearLayoutModel;

import java.util.List;

import okhttp3.RequestBody;

public class LoginLinearLayoutViewModel extends BaseViewModel {
    private MutableLiveData<String> userName;
    private MutableLiveData<String> userPassword;
    private MediatorLiveData<TokenBean> source = new MediatorLiveData<>();
    private LoginLinearLayoutModel mModel = new LoginLinearLayoutModel();
    private LiveData<Resource<TokenBean>> tokenBeanLiveData;


    /**
     * 消息事件
     */


    public MutableLiveData<String> getUserName() {
        if (userName == null) {
            userName = new MutableLiveData<>();
            userName.setValue("18800000001");
        }
        return userName;
    }

    public MutableLiveData<String> getUserPassword() {
        if (userPassword == null) {
            userPassword = new MutableLiveData<>();
            userPassword.setValue("123456");
        }
        return userPassword;
    }

    public LiveData<TokenBean> getSource() {
        return source;
    }

    public void login(RequestBody requestBody, BaseActivity activity) {
        if (tokenBeanLiveData != null) {
            source.removeSource(tokenBeanLiveData);
        }
        tokenBeanLiveData = mModel.getTokenInfo(requestBody, activity);
        source.addSource(tokenBeanLiveData, resource -> {
            if (resource != null) {
                if (resource.isSuccess()) {
                    sendMessage("success");
                    source.setValue(resource.data);
                } else if (resource.isFailure() || resource.isError()) {
                    sendMessage("fail");
                }

            }

        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mModel != null) {
            mModel = null;
        }
    }
}
