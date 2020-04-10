package com.example.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.lib.R;
import com.example.lib.utils.Utils;


/**
 * 普通的输入框
 */
public class CommonEditView extends LinearLayout {
    private ImageView iv_cancel;
    private EditText et_common;
    private LinearLayout ll_all;
    private View view;
    private String hint = "";
    private int height;

    public CommonEditView(Context context) {
        this(context, null);
    }

    public CommonEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.assembly_layout_edit_common, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CommonEditView, defStyleAttr, 0);
        hint = array.getString(R.styleable.CommonEditView_edittext_hint);
        height = array.getDimensionPixelSize(R.styleable.CommonEditView_cev_height, (int) Utils.dip2px(30, context));
        array.recycle();
        //给输入框设置hint
        if (hint == null) {
            hint = "请输入";
        }
        initView(context);
        initListener();
    }

    private void initListener() {
        iv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et_common.setText("");
                hideAll();
            }
        });
        et_common.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text=s.toString();
                if(text.equals("")){
                    hideAll();
                }else{
                    seeAll();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_common.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    //失去焦点
                    hideAll();
                }else{
                    //获得焦点，判断是否有内容
                    if(!et_common.getText().toString().trim().equals("")){
                        seeAll();
                    }
                }
            }
        });
    }




    public String getContent(){
        String str_edit=et_common.getText().toString().trim();
        return str_edit;
    }
    private void initView(Context context) {
        iv_cancel = view.findViewById(R.id.iv_cancel);
        et_common = view.findViewById(R.id.et_common);
        ll_all=view.findViewById(R.id.ll_all);
        ViewGroup.LayoutParams lp;
        lp= ll_all.getLayoutParams();
        lp.width=LayoutParams.MATCH_PARENT;;
        lp.height=height;
        ll_all.setLayoutParams(lp);
        et_common.setHint(hint);
    }

    //    public CommonEditView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//

    //
//    }

    private void hideAll() {
        iv_cancel.setVisibility(GONE);
    }

    private void seeAll() {
        iv_cancel.setVisibility(VISIBLE);
    }

    public void setContent(String content){
        et_common.setText(content);
    }
}
