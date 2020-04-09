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
 * 一行信息，左侧文字，右侧有两个图标
 */
public class LineRightIconView extends LinearLayout {
    private View view;
    private String name = "";
    private Drawable icon;
    private TextView tv_name;
    private ImageView iv_icon;
    public LineRightIconView(Context context) {
        this(context, null);
    }

    public LineRightIconView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineRightIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.assembly_layout_line_icon_right, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineRightIconView, defStyleAttr, 0);
        name = array.getString(R.styleable.LineRightIconView_lriv_name);
        icon = array.getDrawable(R.styleable.LineRightIconView_lriv_icon);
        array.recycle();
        //给输入框设置hint

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
