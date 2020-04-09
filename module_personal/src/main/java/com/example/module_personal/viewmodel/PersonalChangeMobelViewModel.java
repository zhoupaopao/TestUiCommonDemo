package com.example.module_personal.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.lib.base.BaseViewModel;

public class PersonalChangeMobelViewModel extends BaseViewModel {
    //也是需要请求网络的，获取验证码，暂时先不用
    private MutableLiveData<String> mobil_num;

    public MutableLiveData<String> getMobil_num() {
        if(mobil_num==null){
            mobil_num=new MutableLiveData<>();
            mobil_num.setValue("");
        }
        return mobil_num;
    }
}
