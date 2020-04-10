package com.example.module_personal.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib.base.BaseFragment;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_personal.R;
import com.example.module_personal.app.activity.PersonalAccountMessageActivity;
import com.example.module_personal.databinding.FragmentPersonalMainBinding;

@Route(path = ARouterConstants.Personal_Main_Fragment)
public class PersonalMainFragment extends BaseFragment<FragmentPersonalMainBinding> {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBinding.llivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterConstants.Personal_Setting_Activity).navigation();
            }
        });
        mBinding.lrivPersonalMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), PersonalAccountMessageActivity.class);
                startActivity(intent);
            }
        });

    }

    //    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_personal_main;
//    }
//
//    @Override
//    public void initData(@Nullable Bundle savedInstanceState) {
//
//    }
}
