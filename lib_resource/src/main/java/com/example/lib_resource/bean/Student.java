package com.example.lib_resource.bean;

import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class Student extends BaseObservable {
    private String headurl;
    private String name;
    private String gender;

    public Student() {
    }

    public Student(String headurl, String name, String gender) {
        this.headurl = headurl;
        this.name = name;
        this.gender = gender;
    }
@Bindable
    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Bindable
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    @BindingAdapter("headurl")
    public static void setHeadUrl(ImageView iv,String headurl){
        Glide.with(iv.getContext()).load(headurl).into(iv);
        Log.i("test: ", "哈哈哈哈哈: ");
    }
}
