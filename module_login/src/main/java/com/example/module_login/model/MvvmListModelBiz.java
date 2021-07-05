package com.example.module_login.model;

import android.os.Handler;

import com.example.lib_resource.bean.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mj
 * on 2017/5/22.
 * 加载的model
 */

public class MvvmListModelBiz implements MvvmListModel {

    @Override
    public void load(final int loadType, int pageNum, final LoadResponse loadResponse) {

        final List<Student> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student entity = new Student();
            entity.setHeadurl("http://img1.gtimg.com/19/1967/196723/19672355_980x640_281.jpg");
            entity.setName("张三");
            entity.setGender("2017年5月23日");
            data.add(entity);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadResponse.loadSuccess(data, loadType);
            }
        }, 2000);

    }
}
