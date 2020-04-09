package com.example.module_personal.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.lib.base.BaseViewModel;

public class PersonalVerifyCodeViewModel extends BaseViewModel {
    //也是需要请求网络的，获取验证码，暂时先不用
    private MutableLiveData<String>verify_code;
    private MutableLiveData<Integer>time;

    public MutableLiveData<String> getVerify_code() {
        if(verify_code==null){
            verify_code=new MutableLiveData<>();
            verify_code.setValue("");
        }
        return verify_code;
    }

    public MutableLiveData<Integer> getTime() {
        if(time==null){
            time=new MutableLiveData<>();
            time.setValue(0);
        }
        return time;
    }


}
