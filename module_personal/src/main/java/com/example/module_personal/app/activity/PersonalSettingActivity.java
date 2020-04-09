package com.example.module_personal.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.example.lib.base.BaseActivity1;
import com.example.lib.base.ViewManager;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_personal.R;
import com.example.module_personal.databinding.ActivityPersonalSettingBinding;

@Route(path = ARouterConstants.Personal_Setting_Activity)
public class PersonalSettingActivity extends BaseActivity1<ActivityPersonalSettingBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_setting;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
        mBinding.ltvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUIUtils.showAlert(PersonalSettingActivity.this, null, "您将退出登录", "", "", "确认", "取消", false, true, true, new DialogUIListener() {
                    @Override
                    public void onPositive() {
                        ViewManager.finishAllActivity();
                        ARouter.getInstance().build(ARouterConstants.Login_First_Activity).navigation();

                    }

                    @Override
                    public void onNegative() {

                    }

                }).show();
            }
        });
        mBinding.lnivAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalSettingActivity.this,PersonalSettingAboutActivity.class);
                startActivity(intent);
            }
        });
        mBinding.lnivSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalSettingActivity.this,PersonalSettingSafeActivity.class);
                startActivity(intent);
            }
        });
    }
}
