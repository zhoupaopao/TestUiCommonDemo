package com.example.testdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib.utils.SharedPref;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib_resource.utils.ARouterConstants;

public class AppMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedPrefUtil.getString(SharedPref.ip).equals("")){
            SharedPrefUtil.putString(SharedPref.ip,"192.168.1.236");
            SharedPrefUtil.putString(SharedPref.port,"9099");
        }
        SharedPrefUtil.putPassword("123456");
        SharedPrefUtil.putUsername("18800000001");
//        SharedPreferences sp=getSharedPreferences("Infrared",MODE_PRIVATE);
//        String isfirst=sp.getString("isfirst","");
//        if(isfirst==""){
//        ARouter.getInstance().build(ARouterConstants.Login_First_Activity).navigation();Login_New_Activity
//        ARouter.getInstance().build(ARouterConstants.Show_Activity).navigation();

        ARouter.getInstance().build(ARouterConstants.Login_New_Activity).navigation();
        AppMainActivity.this.finish();

//自定义表单
//        ARouter.getInstance().build(ARouterConstants.Login_CustomForm_Activity).navigation();
//        AppMainActivity.this.finish();
//pad列表分页
//        ARouter.getInstance().build(ARouterConstants.Login_Page_Activity).navigation();
//        AppMainActivity.this.finish();
//测试listmvvm
//        ARouter.getInstance().build(ARouterConstants.Login_Test_Activity).navigation();
//        AppMainActivity.this.finish();
//测试recyclelistmvvm
//        ARouter.getInstance().build(ARouterConstants.Login_MvvmRecycle_Activity).navigation();
//        AppMainActivity.this.finish();
        //通用列表
//        ARouter.getInstance().build(ARouterConstants.Login_CustomList_Activity).navigation();
//        AppMainActivity.this.finish();

        //通用平板列表
//        ARouter.getInstance().build(ARouterConstants.Login_CustomPadList_Activity).navigation();
//        AppMainActivity.this.finish();
//        }else{
//            Intent intent=new Intent(WelcomeActivity.this,ScanQRCodeActivity.class);
//            startActivity(intent);
//            WelcomeActivity.this.finish();
//        }
    }
}
