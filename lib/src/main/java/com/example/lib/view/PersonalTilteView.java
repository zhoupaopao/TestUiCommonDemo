package com.example.lib.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.lib.R;


public class PersonalTilteView extends LinearLayout {
    private View view;
    private String name = "";
    private Drawable icon;
    private TextView tv_name;
    private ImageView iv_icon;
    private  LineRightIconView lriv_personalMessage;
    public PersonalTilteView(Context context) {
        this(context, null);
    }

    public PersonalTilteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonalTilteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.assembly_layout_personal_title, this);

//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineRightIconView, defStyleAttr, 0);
//        name = array.getString(R.styleable.LineRightIconView_lriv_name);
//        icon = array.getDrawable(R.styleable.LineRightIconView_lriv_icon);
//        array.recycle();
        //给输入框设置hint

        initView();
//        initListener();
    }

    private void initListener() {

    }





    private void initView() {
        lriv_personalMessage=view.findViewById(R.id.lriv_personalMessage);
    }
    public View getLine(){
        return  lriv_personalMessage;
    }
}
