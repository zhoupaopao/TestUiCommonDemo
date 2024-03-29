package com.example.module_login.adapter;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lib.intface.OnCheckClickListener;
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
    private int visible=1;//gone


    public CustomListAadpter(int layoutResId, @Nullable List<CustomListItem> data) {
        super(layoutResId, data);
        this.formBeans=data;

    }

//    public CustomListAadpter(int layoutResId) {
//        super(layoutResId);
//    }


    public void setVisible(int visible) {
        this.visible = visible;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NotNull BaseMyViewHolder baseMyViewHolder, CustomListItem customListItem) {
//        helper.getLayoutPosition()获取当前下标
        baseMyViewHolder.setText(R.id.item_id,customListItem.getId());
        baseMyViewHolder.setText(R.id.item_number,customListItem.getNumber());
        baseMyViewHolder.setText(R.id.item_name,customListItem.getName());
        baseMyViewHolder.setText(R.id.item_title,customListItem.getTitle());
        baseMyViewHolder.setText(R.id.item_message,customListItem.getMessage());
        baseMyViewHolder.setVisible(R.id.checkbox,visible);
        baseMyViewHolder.setCheck(R.id.checkbox,customListItem.isChecked());
//        Log.i("onClick:out ", ((CheckBox)baseMyViewHolder.getView(R.id.checkbox)).isChecked()+"|"+baseMyViewHolder.getLayoutPosition());
        ((CheckBox)baseMyViewHolder.getView(R.id.checkbox)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick: ", ((CheckBox)baseMyViewHolder.getView(R.id.checkbox)).isChecked()+"|"+baseMyViewHolder.getLayoutPosition());
                customListItem.setChecked(((CheckBox)baseMyViewHolder.getView(R.id.checkbox)).isChecked());

                if(onCheckClickListener!=null){
                    onCheckClickListener.onCheckClick(baseMyViewHolder.getLayoutPosition(),((CheckBox)baseMyViewHolder.getView(R.id.checkbox)).isChecked());
                }
            }
        });
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
    private OnCheckClickListener onCheckClickListener;

    public void setOnCheckClickListener(OnCheckClickListener onCheckClickListener) {
        this.onCheckClickListener = onCheckClickListener;
    }
}
