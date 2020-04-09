package com.example.module_home;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HomeMainModel implements LifecycleObserver {
    ///提供数据
    private MutableLiveData<String> name;


    public LiveData<String> getNewName(String add){
        if (name==null){
            name=new MutableLiveData<>();
            name.setValue("");
        }
        name.setValue(name.getValue()+add);
        return name;
    }

    public MutableLiveData<String> getName() {
        return name;
    }
}
