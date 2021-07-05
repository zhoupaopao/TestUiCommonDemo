package com.example.module_login.widget;


import com.example.lib_resource.bean.GridSelectBean;

import java.util.List;

public interface ConfirmListDialogListener {
    void onCheckSelect(List<GridSelectBean> mList);
    void onCheckSingle(GridSelectBean bean, int position);
    void onItemClickListener(GridSelectBean bean, int position);
}

