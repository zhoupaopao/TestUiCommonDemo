package com.example.module_personal.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lib.base.BaseActivity1;
import com.example.lib.view.LineCheckedIconView;
import com.example.module_personal.R;

public class PersonalMessageSexActivity extends BaseActivity1 {
    private LineCheckedIconView lniv_man;
    private LineCheckedIconView lniv_woman;
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_message_sex;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initView();
        String name = getIntent().getStringExtra("name");
        if (name.equals("男")) {
            lniv_man.setChecked(true);
            lniv_woman.setChecked(false);
        } else {
            lniv_man.setChecked(false);
            lniv_woman.setChecked(true);
        }
    }

    private void initView() {
        lniv_man = findViewById(R.id.lniv_man);
        lniv_woman = findViewById(R.id.lniv_woman);
    }


    @Override
    public boolean isBinding() {
        return  false;
    }

    @Override
    public void initListener() {
        lniv_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //带回最新数据
                intent.putExtra("name", lniv_man.getName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        lniv_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                lniv_man.setChecked(false);
//                lniv_woman.setChecked(true);
                Intent intent = new Intent();
                //带回最新数据
                intent.putExtra("name", lniv_woman.getName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
