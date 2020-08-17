package com.example.lib_resource.bean;

import androidx.databinding.ObservableField;

public class LoginBean  {
    //用于测试使用
    private ObservableField<String>password=new ObservableField<>();

    public ObservableField<String> getPassword() {
        return password;
    }

    public void setPassword(ObservableField<String> password) {
        this.password = password;
    }
}
