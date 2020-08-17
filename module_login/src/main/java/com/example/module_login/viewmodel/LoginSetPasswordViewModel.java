package com.example.module_login.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.lib.base.BaseViewModel;

public class LoginSetPasswordViewModel extends BaseViewModel {
    //也是需要请求网络的，获取验证码，暂时先不用
    private MutableLiveData<String>password;

    public MutableLiveData<String> getPassword() {
        if(password==null){
            password=new MutableLiveData<>();
            password.setValue("");
        }
        return password;
    }
}
