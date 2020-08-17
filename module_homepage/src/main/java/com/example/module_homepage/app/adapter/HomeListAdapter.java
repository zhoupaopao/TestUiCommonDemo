package com.example.module_homepage.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.example.module_homepage.R;
import com.example.module_homepage.viewmodel.UserBean;

import java.util.List;

public class HomeListAdapter<T> extends BaseAdapter {
    Context context;
    List<UserBean>list;
    private LayoutInflater layoutInflater;
    public HomeListAdapter(Context context,List<UserBean>list){
        this.context=context;
        this.list=list;
        this.layoutInflater = LayoutInflater.from(context);
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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.layout_home_item, null);
            viewHolder.item_name = view.findViewById(R.id.item_name);
            viewHolder.item_note=view.findViewById(R.id.item_note);
            viewHolder.item_iv=view.findViewById(R.id.item_iv);
//            viewHolder.deviceAddress = view.findViewById(R.id.device_address);
//            viewHolder.deviceType = view.findViewById(R.id.device_type);
//            viewHolder.deviceState = view.findViewById(R.id.device_state);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        UserBean bean=list.get(position);
        viewHolder.item_name.setText(bean.getName());
        viewHolder.item_note.setText(bean.getNote());
        Glide.with(context).load(bean.getIcon()).into(viewHolder.item_iv);
        return view;
    }
    private class ViewHolder {
        TextView item_name;
        ImageView item_iv;
        TextView item_note;
//        TextView deviceAddress;
//        TextView deviceType;
//        TextView deviceState;
    }
}
