package com.example.module_personal.viewmodel;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.example.lib.base.BaseAndroidViewModel;
import com.example.lib.base.BaseViewModel;

public class PersonalCodeViewModel extends BaseAndroidViewModel {
    private MutableLiveData<Bitmap> code;
    private MutableLiveData<String> code_name;

    public PersonalCodeViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
    }

    public MutableLiveData<String> getCode_name() {
        if(code_name==null){
            code_name=new MutableLiveData<>();
            code_name.setValue("rroajsidnb3232bchasodji");
        }
        return code_name;
    }

    public MutableLiveData<Bitmap> getCode() {
        if(code==null){
            code=new MutableLiveData<>();
            Bitmap bitmap = ((BitmapDrawable)getApplication().getResources().getDrawable(com.example.lib_resource.R.mipmap.icon_dark_code)).getBitmap();
            code.setValue(bitmap);
        }
        return code;
    }

    public void setImageCode(Bitmap bitmap){
        if(code==null){
            code=new MutableLiveData<>();
        }
        code.setValue(bitmap);
    }
}
