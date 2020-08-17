package com.example.module_homepage.viewmodel;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;


import com.bumptech.glide.Glide;
import com.example.lib.base.BaseMessageObservable;
import com.example.lib.bean.Resource;
import com.example.lib_resource.bean.TokenBean;
import com.example.module_homepage.model.UserModel;

import java.util.ArrayList;

//其实这个就是viewmodel
public class UserBean extends BaseMessageObservable {
    //用于测试使用
    private String name;
    private String note;
    private String icon;

    private LiveData<Resource<ArrayList<UserBean>>> userBeanLiveData;
    private MediatorLiveData<ArrayList<UserBean>> source = new MediatorLiveData<>();
    private UserModel mModel = new UserModel();
    public UserBean(){
    }
    public UserBean(String name,String note,String icon){
        this.name=name;
        this.note=note;
        this.icon=icon;
    }


    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        notifyPropertyChanged(BR.note);
    }
@Bindable
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
    }
    @BindingAdapter("android:image")
    public static void setImageUrl(ImageView imageView,String url){
//        imageView.getContext();
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
    public void click(View view){
        Toast.makeText(view.getContext(),getName(),Toast.LENGTH_SHORT).show();
    }

    public MediatorLiveData<ArrayList<UserBean>> getSource() {
        return source;
    }

    public void request() {
        //请求列表
        if (userBeanLiveData != null) {
            source.removeSource(userBeanLiveData);
        }
        userBeanLiveData = mModel.getTokenInfo();
        source.addSource(userBeanLiveData, resource -> {
            if (resource != null) {
                if (resource.isSuccess()) {
                    sendMessage("success");
                    source.setValue(resource.data);
                } else if (resource.isFailure() || resource.isError()) {
                    sendMessage("fail");
                }

            }

        });
    }
}
