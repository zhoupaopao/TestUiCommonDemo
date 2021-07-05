package com.example.module_login.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class MvvmRecViewHolder<T> extends RecyclerView.ViewHolder {
    //定义一个viewBinding，用它去设置变量
    ViewDataBinding viewDataBinding;

    public MvvmRecViewHolder(@NonNull View itemView) {
        super(itemView);
    }

//    public ViewHolder(@NonNull View itemView) {
//        super(itemView);
//    }

    public void setBinding(ViewDataBinding binding) {
        viewDataBinding = binding;
    }

    public ViewDataBinding getBinding() {
        return viewDataBinding;
    }

    public void setContent(T t) {
//        viewDataBinding.setVariable(variableId, t);
        //必须加上，这会强制绑定操作马上执行，而不是推迟到下一帧刷新时
        //recycleview会在onbindviewholder之后立即测量view
        //如果因为绑定推迟到下一帧绘制时导致错误的数据被绑定到view中，view会被不正确的测量
        viewDataBinding.executePendingBindings();
    }
}
