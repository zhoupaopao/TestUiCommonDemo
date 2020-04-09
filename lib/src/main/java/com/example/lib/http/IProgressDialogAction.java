package com.example.lib.http;

/**
 * @Description: 定义了进度条对话框的操作
 * @author: zhh
 * @date: 2017/3/7 10:54
 */

public interface IProgressDialogAction {
    /**
     * 显示进度条对话框
     */
    void showProgressDialog();

    /**
     * 隐藏进度条对话框
     */
    void dismissProgressDialog();
}
