package com.example.module_login.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib.base.BaseActivity1;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.R;
import com.example.module_login.databinding.ActivityShowBinding;
import com.example.module_login.viewmodel.LoginRegisterViewModel;
import com.example.module_login.viewmodel.ShowViewModel;
@Route(path = ARouterConstants.Show_Activity)
public class ShowActivity extends AppCompatActivity {
    private TextView tv1;
    private Button bt1;
    private Button bt2;
    private ShowViewModel showViewModel;
    ActivityShowBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_show);
        showViewModel=new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory())
                .get(ShowViewModel.class);
        binding.setData(showViewModel);
        binding.setLifecycleOwner(this);


//        showViewModel.getNumber().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                tv1.setText(String.valueOf(integer));
//            }
//        });
//        bt1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showViewModel.addNumber(1);
//            }
//        });
//        bt2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showViewModel.addNumber(2);
//            }
//        });
    }
}
