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
import com.example.lib.utils.Utils;


/**
 * 标题组件
 */
public class TitleArrowLinearlayout extends LinearLayout {
    private View view;
    private String str_title;
    private TextView tv_title;
    private Drawable header;
    private ImageView iv_arrow;
    public TitleArrowLinearlayout(Context context) {
        this(context, null);
    }

    public TitleArrowLinearlayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleArrowLinearlayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 初始化view
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        view = LayoutInflater.from(context).inflate(R.layout.assembly_layout_title_arrow, this);
        tv_title=view.findViewById(R.id.tv_title);
        iv_arrow=view.findViewById(R.id.iv_arrow);
        TypedArray array = context.obtainStyledAttributes(attrs,com.example.lib_resource.R.styleable.CommonTitleView, defStyleAttr, 0);
        str_title = array.getString(com.example.lib_resource.R.styleable.CommonTitleView_ctv_title);
        header=array.getDrawable(com.example.lib_resource.R.styleable.CommonTitleView_ctv_icon);
        array.recycle();
        findViewById(R.id.ll_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getActivityContext(context).finish();
            }
        });
        if (str_title == null) {
            tv_title.setVisibility(GONE);
        }else{
            tv_title.setVisibility(VISIBLE);
            tv_title.setText(str_title);

        }
        if (header == null) {
//            iv_arrow.setImageDrawable(R.);
        }else{
            iv_arrow.setImageDrawable(header);

        }

    }
}
