package com.example.module_homepage.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lib.base.BaseModel;
import com.example.lib.bean.Resource;
import com.example.module_homepage.viewmodel.UserBean;

import java.util.ArrayList;

public class UserModel extends BaseModel {
    private MutableLiveData<Resource<ArrayList<UserBean>>> tokenLiveData = new MutableLiveData<>();
    String[] imgurls = {"http://qzonestyle.gtimg.cn/qzone/app/weishi/client/testimage/256/1.jpg",
            "https://img-blog.csdnimg.cn/20190323161159133.png",
            "https://img-blog.csdnimg.cn/20190323161242873.png"};
    ArrayList<UserBean> list = new ArrayList<>();

    public LiveData<Resource<ArrayList<UserBean>>> getTokenInfo() {
        tokenLiveData.setValue(Resource.loading());
        for (int i = 0; i < imgurls.length; i++) {
            UserBean userBean = new UserBean("小命" + i, "note" + i, imgurls[i]);
            list.add(userBean);
        }
        tokenLiveData.setValue(Resource.success(list));

        return tokenLiveData;
    }
}
