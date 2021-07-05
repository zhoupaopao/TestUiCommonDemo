package com.example.module_login.adapter;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lib_resource.bean.CustomItemBean;
import com.example.lib_resource.bean.CustomListItem;
import com.example.lib_resource.bean.FromValue;
import com.example.lib_resource.bean.GridSelectBean;
import com.example.module_login.R;
import com.example.module_login.viewholder.BaseMyViewHolder;
import com.example.module_login.widget.ConfirmListDialog;
import com.example.module_login.widget.ConfirmListDialogListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static android.text.InputType.TYPE_NUMBER_VARIATION_NORMAL;

public class CustomListAadpter extends BaseQuickAdapter<CustomListItem, BaseMyViewHolder> {
    /**
     * 是否允许修改
     */
    private boolean isChange;
    private List<CustomListItem> formBeans = new ArrayList<>();

    public CustomListAadpter(int layoutResId, @Nullable List<CustomListItem> data) {
        super(layoutResId, data);
        this.formBeans=data;
    }

//    public CustomListAadpter(int layoutResId) {
//        super(layoutResId);
//    }


    @Override
    protected void convert(@NotNull BaseMyViewHolder baseMyViewHolder, CustomListItem customListItem) {
//        helper.getLayoutPosition()获取当前下标
        baseMyViewHolder.setText(R.id.item_id,customListItem.getId());
        baseMyViewHolder.setText(R.id.item_number,customListItem.getNumber());
        baseMyViewHolder.setText(R.id.item_name,customListItem.getName());
        baseMyViewHolder.setText(R.id.item_title,customListItem.getTitle());
        baseMyViewHolder.setText(R.id.item_message,customListItem.getMessage());
        ((Button)baseMyViewHolder.getView(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formBeans.remove(baseMyViewHolder.getLayoutPosition());
                notifyItemRemoved(baseMyViewHolder.getLayoutPosition());
                if (baseMyViewHolder.getLayoutPosition() != formBeans.size()) {
                    notifyItemRangeChanged(baseMyViewHolder.getLayoutPosition(), formBeans.size() - baseMyViewHolder.getLayoutPosition());
                }
            }
        });

    }
}
