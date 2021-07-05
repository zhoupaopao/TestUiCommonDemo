package com.example.module_login.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MvvmRecycleAdapter<T> extends RecyclerView.Adapter<MvvmRecycleAdapter.ViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    int layoutId;
    int variableId;
    List<T> lists;

    public MvvmRecycleAdapter(Context context, LayoutInflater layoutInflater, int layoutId, int variableId, List<T> lists) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.layoutId = layoutId;
        this.variableId = variableId;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //先得到viewdatabinding对象，这个对象中会持有条目item布局的应用
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(layoutInflater, layoutId, parent, false);
        //*****切记，一定要使用viewdatabinding做你哥的view的引用去实例化viewholder对象，否则将不会有任何效果
        MvvmRecycleAdapter.ViewHolder viewHolder = new MvvmRecycleAdapter.ViewHolder(viewDataBinding.getRoot().getRootView());
        viewHolder.setBinding(viewDataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MvvmRecycleAdapter.ViewHolder holder, int position) {
        holder.setContent(lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //定义一个viewBinding，用它去设置变量
        ViewDataBinding viewDataBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setBinding(ViewDataBinding binding) {
            viewDataBinding = binding;
        }

        public ViewDataBinding getBinding() {
            return viewDataBinding;
        }

        public void setContent(T t) {
            viewDataBinding.setVariable(variableId, t);
            //必须加上，这会强制绑定操作马上执行，而不是推迟到下一帧刷新时
            //recycleview会在onbindviewholder之后立即测量view
            //如果因为绑定推迟到下一帧绘制时导致错误的数据被绑定到view中，view会被不正确的测量
            viewDataBinding.executePendingBindings();
        }
    }
}
