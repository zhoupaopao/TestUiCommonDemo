package com.example.module_login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.util.List;

/**
 * 这个貌似不能用，会内存溢出，没有回收机制
 * @param <T>
 */
public class TestMvvmAdapter<T> extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    int layoutId;
    int variableId;
    List<T>list;

    public TestMvvmAdapter(Context context, LayoutInflater inflater, int layoutId, int variableId, List<T> list) {
        this.context = context;
        this.inflater = inflater;
        this.layoutId = layoutId;
        this.variableId = variableId;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding viewDataBinding = null;
//        if(convertView==null){
            viewDataBinding= DataBindingUtil.inflate(inflater,layoutId,parent,false);
//        }else{
//            viewDataBinding=DataBindingUtil.getBinding(convertView);
//        }
        viewDataBinding.setVariable(variableId,list.get(position));
        return viewDataBinding.getRoot().getRootView();
    }
}
