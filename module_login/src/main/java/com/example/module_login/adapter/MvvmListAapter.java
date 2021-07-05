package com.example.module_login.adapter;

import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.lib.base.BaseAdapter;
import com.example.lib.base.BindingVH;
import com.example.lib_resource.bean.Student;
import com.example.module_login.BR;
import com.example.module_login.R;

public class MvvmListAapter extends BaseAdapter<Student, BindingVH> {
    @Override
    public BindingVH createVH(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = null;
//        switch (viewType) {
//            case NO_PIC:
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.item_list_test_mvvm, parent, false);
//                break;
//            case ONE_PIC:
//                viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.mv_vm_item_one_pic, parent, false);
//                break;
//            case MORE_PIC:
//                viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.mv_vm_item_more_pic, parent, false);
//                break;
//        }
        return new BindingVH<>(viewDataBinding);
    }

    @Override
    public void bindVH(BindingVH bindingVH, int position) {
//        bindingVH.getBinding().setVariable(BR.newsEntity, data.get(position));
//        bindingVH.getBinding().setVariable(BR.handle, this);
//        bindingVH.getBinding().setVariable(BR.position, position);

        bindingVH.getBinding().executePendingBindings();
    }
}
