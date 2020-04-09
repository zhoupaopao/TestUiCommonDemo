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
 * 一行信息文本信息
 */
public class LineTextView extends LinearLayout {
    private View view;
    private String text="";
    private TextView tv_text;
    public LineTextView(Context context) {
        this(context, null);
    }

    public LineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.assembly_layout_line_text_view, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineTextView, defStyleAttr, 0);
        text=array.getString(R.styleable.LineTextView_ltv_text);
        array.recycle();
        //给输入框设置hint

        initView(context);
    }





    private void initView(Context context) {
        tv_text=view.findViewById(R.id.tv_text);
        tv_text.setText(text);
    }
}
