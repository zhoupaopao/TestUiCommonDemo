package com.example.module_login.adapter;

import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.lib_resource.bean.CustomItemBean;
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

public class CustomFormAadpter extends BaseMultiItemQuickAdapter<CustomItemBean, BaseMyViewHolder> {
    /**
     * 是否允许修改
     */
    private boolean isChange;
    private List<CustomItemBean> formBeans = new ArrayList<>();

    public CustomFormAadpter(@Nullable List<CustomItemBean> data, boolean isChange) {
        super(data);
        this.isChange = isChange;
        formBeans = data;
        addItemType(CustomItemBean.TEXT, R.layout.layout_custom_text);
        addItemType(CustomItemBean.EDIT, R.layout.layout_custom_edit);
        addItemType(CustomItemBean.SWITCHMORE, R.layout.layout_custom_switch);
        addItemType(CustomItemBean.TEXTMORE, R.layout.layout_custom_switch);
        addItemType(CustomItemBean.TITLE, R.layout.layout_custom_title);
        addItemType(CustomItemBean.CHANGE, R.layout.layout_custom_text);
        addItemType(CustomItemBean.LISTVIEW, R.layout.layout_custom_listview);
    }

    @Override
    protected void convert(@NotNull BaseMyViewHolder baseMyViewHolder, CustomItemBean customItemBean) {
        switch (baseMyViewHolder.getItemViewType()) {
            case CustomItemBean.EDIT:
                EditText editText = baseMyViewHolder.getView(R.id.et_right);
                TextView tv_edit_left = baseMyViewHolder.getView(R.id.tv_left);
                tv_edit_left.setText(customItemBean.getName());
                editText.setHint(customItemBean.getEditHint());
                editText.setText(customItemBean.getValue());
                if (customItemBean.getEditExp() == 1) {
                    //文本
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                } else if (customItemBean.getEditExp() == 2) {
                    //整数
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | TYPE_NUMBER_VARIATION_NORMAL);
                } else if (customItemBean.getEditExp() == 3) {
                    //小数
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                }
                break;
            case CustomItemBean.TEXT:
                TextView tv_right = baseMyViewHolder.getView(R.id.tv_right);
                TextView tv_text_left = baseMyViewHolder.getView(R.id.tv_left);
                tv_text_left.setText(customItemBean.getName());
                tv_right.setText(customItemBean.getValue());
                LinearLayout ll_text_all=baseMyViewHolder.getView(R.id.ll_text_all);
                ViewGroup.LayoutParams layoutParams = ll_text_all.getLayoutParams();
                if(customItemBean.isVisible()){
                    //可见
                    ll_text_all.setVisibility(View.VISIBLE);
                    layoutParams.height=ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
                }else{
                    //不看见
                    ll_text_all.setVisibility(View.GONE);
                    layoutParams.height=0;
                    layoutParams.width=0;
                }
                ll_text_all.setLayoutParams(layoutParams);
//                if(customItemBean.isVisible()){
//                    //可见
//                    tv_right.setVisibility(View.VISIBLE);
//                }else{
//                    //不看见
//                    tv_right.setVisibility(View.GONE);
//                }

                break;
            case CustomItemBean.TEXTMORE:
                TextView tv_textmore_right = baseMyViewHolder.getView(R.id.tv_switch_one);
                TextView tv_textmore_left = baseMyViewHolder.getView(R.id.tv_left);
                tv_textmore_left.setText(customItemBean.getName());
                if (isChange) {
                    tv_textmore_right.setOnClickListener(v -> {
                        List<GridSelectBean> beans = new ArrayList<>();
                        for (FromValue value : customItemBean.getValues()) {
                            beans.add(new GridSelectBean(value.getValue(), value.getId()));
                        }
                        new ConfirmListDialog(getContext())
                                .setTitleStr("请选择")
                                .setList(beans)
                                .setSingle(false)
                                .setConfirmListDialogListener(new ConfirmListDialogListener() {
                                    @Override
                                    public void onCheckSelect(List<GridSelectBean> mList) {
                                        StringBuilder stringBuffer = new StringBuilder();
                                        StringBuilder names = new StringBuilder();
                                        for (int i = 0; i < mList.size(); i++) {
                                            GridSelectBean b = mList.get(i);
                                            stringBuffer.append(b.getValue());
                                            names.append(b.getTitle());
                                            if (mList.size() - 1 != i) {
                                                stringBuffer.append("-");
                                                names.append(",");
                                            }
                                        }
                                        // 赋值
                                        customItemBean.setValue(stringBuffer.toString());
                                        tv_textmore_right.setText(names.toString());
                                    }

                                    @Override
                                    public void onCheckSingle(GridSelectBean bean, int position) {
                                    }

                                    @Override
                                    public void onItemClickListener(GridSelectBean bean, int position) {
                                    }
                                }).show();
                    });
                    // 多选默认赋值
                    if (!StringUtils.isTrimEmpty(customItemBean.getValue())) {
                        StringBuilder names = new StringBuilder();
                        String[] va = customItemBean.getValue().split("-");
                        for (int i = 0; i < customItemBean.getValues().size(); i++) {
                            for (String aVa : va) {
                                if (customItemBean.getValues().get(i).getId().equals(aVa)) {
                                    names.append(customItemBean.getValues().get(i).getValue());
                                    if (va.length - 1 != i) {
                                        names.append(",");
                                    }
                                }
                            }
                        }
                        tv_textmore_right.setText(names.toString());
                    }
                }
                break;
            case CustomItemBean.SWITCHMORE:
                TextView tv_switch_right = baseMyViewHolder.getView(R.id.tv_switch_one);
                TextView tv_switch_left = baseMyViewHolder.getView(R.id.tv_left);
                tv_switch_left.setText(customItemBean.getName());
                tv_switch_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<GridSelectBean> beans = new ArrayList<>();
                        for (FromValue fromValue : customItemBean.getValues()) {
                            beans.add(new GridSelectBean(fromValue.getValue(), fromValue.getId()));
                        }
                        new ConfirmListDialog(getContext())
                                .setTitleStr("请选择")
                                .setList(beans)
                                .setConfirmListDialogListener(new ConfirmListDialogListener() {
                                    @Override
                                    public void onCheckSelect(List<GridSelectBean> mList) {

                                    }

                                    @Override
                                    public void onCheckSingle(GridSelectBean bean, int position) {

                                    }

                                    @Override
                                    public void onItemClickListener(GridSelectBean bean, int position) {
                                        customItemBean.setValue(bean.getValue());
                                        tv_switch_right.setText(bean.getTitle());
                                    }
                                }).show();
                    }
                });
                // 多选一默认赋值
                if (!StringUtils.isTrimEmpty(customItemBean.getValue())) {
                    for (int i = 0; i < customItemBean.getValues().size(); i++) {
                        if (customItemBean.getValue().equals(customItemBean.getValues().get(i).getId())) {
                            tv_switch_right.setText(customItemBean.getValues().get(i).getValue());
                        }
                    }
                }
                break;
            case CustomItemBean.TITLE:
                TextView tv_title_left = baseMyViewHolder.getView(R.id.tv_left);
                tv_title_left.setText(customItemBean.getValue());
                LinearLayout ll_title_layout = baseMyViewHolder.getView(R.id.ll_title_layout);
                ll_title_layout.setBackgroundColor(android.graphics.Color.parseColor("#87CEFA"));
                break;
            case CustomItemBean.CHANGE:
                TextView tv_change_left = baseMyViewHolder.getView(R.id.tv_left);
                tv_change_left.setText(customItemBean.getName());
                TextView tv_change_right = baseMyViewHolder.getView(R.id.tv_right);
                tv_change_right.setText(customItemBean.getValue());
                tv_change_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (customItemBean.getExpListenerBean().getChange().getType()) {
                            case "setVisible":
                                String toId = customItemBean.getExpListenerBean().getChange().getParams().getToId();
                                for (CustomItemBean customItemBean1 : formBeans) {
                                    if (customItemBean1.getId().equals(toId)) {
                                        //设置隐藏
                                        customItemBean1.setVisible(!customItemBean1.isVisible());
                                        break;
                                    }
                                }
                                notifyDataSetChanged();
                                break;

                            case "setValue":
                                break;
                        }
                    }
                });

                break;
                case CustomItemBean.LISTVIEW:
                    // 动态表单
                    RecyclerView recyclerView=baseMyViewHolder.getView(R.id.recyclerView1);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    CustomFormAadpter  customFormAadpter = new CustomFormAadpter(customItemBean.getLists(), true);
                    recyclerView.setAdapter(customFormAadpter);
                    break;

        }
    }
}
