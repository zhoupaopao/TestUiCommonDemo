package com.example.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.lib.R;


/**
 * 一行信息，勾选
 */
public class LineCheckedIconView extends LinearLayout {
    private View view;
    private String name = "";
    private Boolean icon;
    private TextView tv_name;
    private ImageView iv_icon;
    public LineCheckedIconView(Context context) {
        this(context, null);
    }

    public LineCheckedIconView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineCheckedIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.assembly_layout_line_icon_checked, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineCheckedIconView, defStyleAttr, 0);
        name = array.getString(R.styleable.LineCheckedIconView_lciv_name);
        icon = array.getBoolean(R.styleable.LineCheckedIconView_lciv_icon,false);
        array.recycle();

        initView(context);
        initListener();
    }

    private void initListener() {

    }





    private void initView(Context context) {
        tv_name=view.findViewById(R.id.tv_name);
        iv_icon=view.findViewById(R.id.iv_arrow);
        if(icon){
            iv_icon.setVisibility(VISIBLE);
        }else{
            iv_icon.setVisibility(GONE);
        }
        tv_name.setText(name);
    }
    public  void setChecked(boolean checked){
        if(checked){
            iv_icon.setVisibility(VISIBLE);
        }else{
            iv_icon.setVisibility(GONE);
        }
    }
    public  String getName(){
        return tv_name.getText().toString().trim();
    }


}
