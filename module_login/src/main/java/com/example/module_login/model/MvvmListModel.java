package com.example.module_login.model;

import com.example.lib_resource.bean.Student;

import java.util.List;

public interface MvvmListModel {
    /**
     * 加载新闻数据
     *
     * @param loadType     firstLoad首次加载 refresh 刷新 loadMore 加载更多
     * @param pageNum 页数
     * @param loadResponse 加载结果回调
     */
    void load(int loadType,int pageNum ,LoadResponse loadResponse);

    /**
     * 加载结果回调
     */
    interface LoadResponse {
        /**
         * 加载成功
         *
         * @param data     data
         * @param loadType 加载类型
         */
        void loadSuccess(List<Student> data, int loadType);

        /**
         * 加载失败
         *
         * @param msg message
         */
        void loadFailure(String msg);
    }
}
