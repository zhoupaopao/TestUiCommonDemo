package com.example.module_personal.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.lib.base.BaseViewModel;

public class PersonalChangePasswordViewModel extends BaseViewModel {
    //也是需要请求网络的，获取验证码，暂时先不用
    private MutableLiveData<String> old_password;
    private MutableLiveData<String> new_password;
    private MutableLiveData<String> sure_password;
    public MutableLiveData<String> getNew_password() {
        if(new_password==null){
            new_password=new MutableLiveData<>();
            new_password.setValue("");
        }
        return new_password;
    }

    public MutableLiveData<String> getOld_password() {
        if(old_password==null){
            old_password=new MutableLiveData<>();
            old_password.setValue("");
        }
        return old_password;
    }

    public MutableLiveData<String> getSure_password() {
        if(sure_password==null){
            sure_password=new MutableLiveData<>();
            sure_password.setValue("");
        }
        return sure_password;
    }

}
