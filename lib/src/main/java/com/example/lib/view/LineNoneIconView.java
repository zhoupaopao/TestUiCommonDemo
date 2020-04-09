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
 * 一行信息，没有图标
 */
public class LineNoneIconView extends LinearLayout {
    private View view;
    private String name = "";
    private Drawable icon;
    private String text="";
    private boolean arrow;
    private TextView tv_name;
    private TextView tv_text;
    private ImageView iv_icon;
    private ImageView iv_arrow;
    public LineNoneIconView(Context context) {
        this(context, null);
    }

    public LineNoneIconView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineNoneIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.assembly_layout_line_icon_none, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineNoneIconView, defStyleAttr, 0);
        name = array.getString(R.styleable.LineNoneIconView_lniv_name);
        icon = array.getDrawable(R.styleable.LineNoneIconView_lniv_icon);
        text=array.getString(R.styleable.LineNoneIconView_lniv_text);
        arrow=array.getBoolean(R.styleable.LineNoneIconView_lniv_arrow,true);
        array.recycle();
        //给输入框设置hint

        initView(context);
        initListener();
    }

    private void initListener() {
        tv_name.setText(name);
        if(arrow){
            iv_arrow.setVisibility(VISIBLE);
        }else{
            iv_arrow.setVisibility(GONE);
        }
        if(text==null){
            tv_text.setVisibility(GONE);
        }else{
            tv_text.setVisibility(VISIBLE);
            tv_text.setText(text);
        }
        if(icon==null){
            iv_icon.setVisibility(GONE);
        }else{
            iv_icon.setVisibility(VISIBLE);
            iv_icon.setImageDrawable(icon);
        }
    }





    private void initView(Context context) {
        tv_name=view.findViewById(R.id.tv_name);
        tv_text=view.findViewById(R.id.tv_text);
        iv_icon=view.findViewById(R.id.iv_icon);//右边的图标
        iv_arrow=view.findViewById(R.id.iv_arrow);//右边的箭头
//        iv_icon.setImageDrawable(icon);

    }
    public void setText(String text){
        tv_text.setText(text);
    }

    public String getText(){
        return tv_text.getText().toString().trim();
    }
}
