package com.example.module_login.viewmodel;

import android.content.Context;

import com.example.lib.utils.PromptDialog;
import com.example.lib_resource.bean.Student;
import com.example.module_login.adapter.MvvmListAapter;
import com.example.module_login.databinding.ActivityMvvmListBinding;
import com.example.module_login.databinding.ActivityTestMvvmBinding;
import com.example.module_login.model.MvvmListModel;
import com.example.module_login.model.MvvmListModelBiz;

import java.util.List;

public class MvvmListVm implements MvvmListModel.LoadResponse {

    /**
     * 首次加载
     */
    private final int FIRST_LOAD = 0;
    /**
     * 下拉刷新
     */
    private final int REFRESH = 1;
    /**
     * 加载更多
     */
    private final int LOAD_MORE = 2;
    /**
     * binding
     */
    private ActivityMvvmListBinding binding;
    /**
     * adapter
     */
    private MvvmListAapter adapter;
    /**
     * 加载列表数据业务逻辑
     */
    private MvvmListModelBiz newsListModelBiz;
    /**
     * 页数
     */
    private int pageNum = 1;
    /**
     * context
     */
    private Context context;


    public MvvmListVm(Context context, ActivityMvvmListBinding binding, MvvmListAapter adapter) {
        this.context = context;
        this.binding = binding;
        this.adapter = adapter;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        newsListModelBiz = new MvvmListModelBiz();
        firstLoadData();
    }


    /**
     * 首次加载
     */
    private void firstLoadData() {
        PromptDialog.getInstance().show(context, "加载中...");
        newsListModelBiz.load(FIRST_LOAD, pageNum, this);
    }

    @Override
    public void loadSuccess(List<Student> data, int loadType) {

    }

    @Override
    public void loadFailure(String msg) {

    }
}
