package com.example.module_login.widget;

import android.content.Context;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.lib_resource.bean.GridSelectBean;
import com.example.module_login.R;
import com.zhy.adapter.abslistview.CommonAdapter;

import java.util.List;

public class DialogAdapter extends CommonAdapter<GridSelectBean> {

    private boolean isSingle;
    private int flag;

    public DialogAdapter(Context context, int layoutId, List<GridSelectBean> datas, boolean isSingle, int flag) {
        super(context, layoutId, datas);
        this.isSingle = isSingle;
        this.flag = flag;
    }

    public List<GridSelectBean> getData() {
        return mDatas;
    }

    public void setData(List<GridSelectBean> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, GridSelectBean bean, int position) {
        viewHolder.setText(R.id.dialog_item_name,bean.getTitle());
        if (flag == 3) {
            TextView textView = viewHolder.getView(R.id.dialog_item_true);
            textView.setText(bean.getValue());
        } else if (flag == 1) {
            ImageView imageView = viewHolder.getView(R.id.dialog_item_true);
            if (!isSingle) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,
                        bean.isSelectStatus()?R.mipmap.xuanzhongbianhao:R.mipmap.weixuanzhongbianhao));
            } else {
                imageView.setVisibility(View.GONE);
            }
        }
        TextView textView = viewHolder.getView(R.id.dialog_item_line);
        if (position == mDatas.size() - 1) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }
}
