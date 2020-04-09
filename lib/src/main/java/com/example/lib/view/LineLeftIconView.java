package com.example.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.lib.R;


/**
 * 一行信息，中间文字，左侧有个图标
 */
public class LineLeftIconView extends LinearLayout {
    private View view;
    private String name = "";
    private Drawable icon;
    private TextView tv_name;
    private ImageView iv_icon;
    public LineLeftIconView(Context context) {
        this(context, null);
    }

    public LineLeftIconView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineLeftIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.assembly_layout_line_icon_left, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineLeftIconView, defStyleAttr, 0);
        name = array.getString(R.styleable.LineLeftIconView_lliv_name);
        icon = array.getDrawable(R.styleable.LineLeftIconView_lliv_icon);
        array.recycle();

        initView(context);
        initListener();
    }

    private void initListener() {

    }





    private void initView(Context context) {
        tv_name=view.findViewById(R.id.tv_name);
        iv_icon=view.findViewById(R.id.iv_icon);
        iv_icon.setImageDrawable(icon);
        tv_name.setText(name);
    }



}
