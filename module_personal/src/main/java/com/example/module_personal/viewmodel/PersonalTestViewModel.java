package com.example.module_personal.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

import com.example.module_personal.R;

public class PersonalTestViewModel extends AndroidViewModel {

    private SavedStateHandle handle;
    private String key = getApplication().getResources().getString(R.string.app_name);
    private String shpname = getApplication().getResources().getString(R.string.app_name);

    public PersonalTestViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        this.handle = handle;
        if (!handle.contains(key)) {
            load();
        }
    }

    public LiveData<Integer> getNumber() {
        return handle.getLiveData(key);
    }

    //读取操作
    private void load() {
        SharedPreferences shp = getApplication().getSharedPreferences(shpname, Context.MODE_PRIVATE);
        int x = shp.getInt(key, 0);
        handle.set(key, x);
    }

    void save() {
        SharedPreferences shp = getApplication().getSharedPreferences(shpname, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(key, getNumber().getValue() == null ? 0 : getNumber().getValue());
        editor.apply();
    }

    public void add(int x) {
        handle.set(key, getNumber().getValue() == null ? 0 : getNumber().getValue() + x);
        //保存操作 当程序被杀死也会从新读取 不会频繁变动 比较好 频繁则消耗时间
        //save();
    }
}
