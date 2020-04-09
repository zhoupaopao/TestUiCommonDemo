package com.example.module_login.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShowViewModel extends ViewModel {
//    public int number=0;
    private MutableLiveData<Integer>number;
//set不写，不允许用户直接把值赋进来
    public MutableLiveData<Integer> getNumber() {
        //要保证对象不为空
        if(number==null){
            number=new MutableLiveData<>();
            number.setValue(0);
        }
        return number;
    }
    public void addNumber(int num){
        number.setValue(number.getValue()+num);
    }
}
