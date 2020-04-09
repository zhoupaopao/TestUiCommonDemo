package com.example.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.example.lib.R;


public class MyRadioButton extends AppCompatRadioButton {
    private Drawable drawable;

    public MyRadioButton(Context context) {
        super(context);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRadioButton);//获取我们定义的属性
        drawable = typedArray.getDrawable(R.styleable.MyRadioButton_drawableTop);
        drawable.setBounds(0, 0, 80, 80);
        setCompoundDrawables(null, drawable, null, null);
    }
}
