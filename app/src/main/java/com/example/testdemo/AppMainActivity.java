package com.example.testdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib_resource.utils.ARouterConstants;

public class AppMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SharedPreferences sp=getSharedPreferences("Infrared",MODE_PRIVATE);
//        String isfirst=sp.getString("isfirst","");
//        if(isfirst==""){
        ARouter.getInstance().build(ARouterConstants.Login_First_Activity).navigation();
//        ARouter.getInstance().build(ARouterConstants.Show_Activity).navigation();
        AppMainActivity.this.finish();
//        }else{
//            Intent intent=new Intent(WelcomeActivity.this,ScanQRCodeActivity.class);
//            startActivity(intent);
//            WelcomeActivity.this.finish();
//        }
    }
}
