package com.example.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.lib.R;


/**
 * 个人信息的最上层头
 */
public class PersonalTopView extends RelativeLayout {
    private View view;
    private String name = "";
    private String company_name = "";
    private Drawable header;
    private TextView tv_company_name;
    private TextView tv_name;
    private ImageView iv_header;
    public PersonalTopView(Context context) {
        this(context, null);
    }

    public PersonalTopView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonalTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.assembly_layout_personal_top, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PersonalTopView, defStyleAttr, 0);
        name = array.getString(R.styleable.PersonalTopView_ptv_name);
        company_name = array.getString(R.styleable.PersonalTopView_ptv_company_name);
        header=array.getDrawable(R.styleable.PersonalTopView_ptv_header);
        array.recycle();
        //给输入框设置hint

        initView(context);
        initListener();
    }

    private void initListener() {

    }





    private void initView(Context context) {
        iv_header=view.findViewById(R.id.iv_header);
        tv_company_name=view.findViewById(R.id.tv_company_name);
        tv_name=view.findViewById(R.id.tv_name);
        iv_header.setImageDrawable(header);
        tv_company_name.setText(company_name);
        tv_name.setText(name);
    }



}
