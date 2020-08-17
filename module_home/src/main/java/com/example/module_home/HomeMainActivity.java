package com.example.module_home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib.base.BaseActivityWhiteTitle;
import com.example.lib_resource.bean.MedicalOrderDTO;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_home.databinding.ActivityHomeMainBinding;

@Route(path = ARouterConstants.Home_Main_Activity)
public class HomeMainActivity extends BaseActivityWhiteTitle<ActivityHomeMainBinding> {
    private ActivityHomeMainBinding binding;
    private MyHomeViewModel viewModel;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this,new SavedStateViewModelFactory(getApplication(),this)).get(MyHomeViewModel.class);

        binding.setData(viewModel);
        binding.setPresent(new Presenter());
        binding.setLifecycleOwner(this);
        getLifecycle().addObserver(binding.textView3);
        viewModel.getName("231");
        viewModel.getName().observe(this,s -> {
            Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        });
        MedicalOrderDTO ddd= (MedicalOrderDTO) getIntent().getSerializableExtra("dto");
        viewModel.addAll(ddd);
    }


    /**
     * 点击事件的两种方式
     */
    public class Presenter {



        public void onClick(View v) {
            if(v.getId()==R.id.button){
//                Intent intent=new Intent(LoginMainActivity.this,LifeCycleActivity.class);
//                startActivity(intent);
                Intent intent = new Intent();
                //带回最新数据
                intent.putExtra("name", binding.textView2.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }

//            Toast.makeText(SimpleActivity.this, "点击了", Toast.LENGTH_SHORT).show();
        }


    }
}
