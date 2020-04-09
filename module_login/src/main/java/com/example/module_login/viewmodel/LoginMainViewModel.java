package com.example.module_login.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginMainViewModel extends ViewModel {
    private MutableLiveData<String> userName;
    private MutableLiveData<String> userPassword;

    public MutableLiveData<String> getUserName() {
        if(userName==null){
            userName=new MutableLiveData<>();
            userName.setValue("");
        }
        return userName;
    }

    public MutableLiveData<String> getUserPassword() {
        if(userPassword==null){
            userPassword=new MutableLiveData<>();
            userPassword.setValue("");
        }
        return userPassword;
    }
}
