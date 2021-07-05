package com.example.lib.base;


import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mj
 * on 2017/5/22.
 * binding view holder
 */

public class BindingVH<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    /**
     * viewDataBinding
     */
    private B mBinding;

    /**
     * constructor
     *
     * @param binding viewDataBinding
     */
    public BindingVH(B binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    /**
     * @return viewDataBinding
     */
    public B getBinding() {
        return mBinding;
    }

}
