package com.example.module_login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib_resource.bean.MedicalOrderDTO;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.databinding.ActivityLoginMainBinding;

@Route(path = ARouterConstants.Login_Main_Activity)
public class LoginMainActivity extends AppCompatActivity {
    ActivityLoginMainBinding binding;
    MyViewModel myViewModel;
    final int Request1=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_main);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login_main);
        myViewModel=new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(MyViewModel.class);
        binding.setData(myViewModel);
        binding.setPresent(new Presenter());
        binding.setLifecycleOwner(this);

    }

    /**
     * 点击事件的两种方式
     */
    public class Presenter {

        //方法引用
        public void onTextChangeListener22(CharSequence s, int start, int before, int count) {
            binding.getData().getNumber().setValue(Integer.parseInt(s.toString()));
//            //            binding.setEmployee(employee);
        }

        public void onClick(View v) {
            if(v.getId()==R.id.button2){
//                Intent intent=new Intent(LoginMainActivity.this,LifeCycleActivity.class);
//                startActivity(intent);
                MedicalOrderDTO medicalOrderDTO=new MedicalOrderDTO();
                medicalOrderDTO.setNumber(binding.textView.getText().toString());
//                Postcard postcard = ARouter.getInstance().build(ARouterConstants.Home_Main_Activity);//.withBundle("bundle",bundle).withSerializable("dto",medicalOrderDTO).withString("aaa","传过来的值")
//                LogisticsCenter.completion(postcard);
//                Class<?> destination = postcard.getDestination();
//                Intent intent = new Intent(LoginMainActivity.this, destination);
//                intent.putExtra("dto", medicalOrderDTO);
//                startActivityForResult(intent, Request1);
//也可以用来传值返回
                ARouter.getInstance().build(ARouterConstants.Home_Main_Activity)
                        .withInt("age", 28).withString("name", "cyz").withSerializable("dto",medicalOrderDTO).navigation(LoginMainActivity.this,Request1);
            }

//            Toast.makeText(SimpleActivity.this, "点击了", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Request1&&resultCode==RESULT_OK){
            binding.textView.setText("返回的值："+data.getStringExtra("name"));
        }
    }
}
