package com.example.module_login.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.lib.base.BaseViewModel;

public class LoginVerifyCodeViewModel extends BaseViewModel {
    //也是需要请求网络的，获取验证码，暂时先不用
    private MutableLiveData<String>verify_code;

    public MutableLiveData<String> getVerify_code() {
        if(verify_code==null){
            verify_code=new MutableLiveData<>();
            verify_code.setValue("");
        }
        return verify_code;
    }
}
