package com.example.module_login.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lib.base.BaseActivity;
import com.example.lib.base.BaseViewModel;
import com.example.lib.bean.Resource;
import com.example.lib_resource.bean.TokenBean;
import com.example.module_login.model.LoginLinearLayoutModel;

import okhttp3.RequestBody;

public class LoginRegisterViewModel extends BaseViewModel {
    //也是需要请求网络的，获取验证码，暂时先不用
    private MutableLiveData<String>mobil_num;

    public MutableLiveData<String> getMobil_num() {
        if(mobil_num==null){
            mobil_num=new MutableLiveData<>();
            mobil_num.setValue("");
        }
        return mobil_num;
    }
}
