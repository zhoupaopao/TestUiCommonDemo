package com.example.module_personal.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.lib.base.BaseActivity1;
import com.example.lib.utils.DateUtil;
import com.example.lib_resource.bean.CityListBean;
import com.example.lib_resource.utils.JsonFileReader;
import com.example.module_personal.R;
import com.example.module_personal.databinding.ActivityPersonalMessageMoreBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PersonalMessageMoreActivity extends BaseActivity1<ActivityPersonalMessageMoreBinding> {
    private static final int CHANGE_SEX = 1;
    private ArrayList<CityListBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_message_more;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String JsonData = JsonFileReader.getJson(this, "province_data.json");
//        ArrayList<CityListBean> jsonBean = parseData(JsonData);//用Gson 转成实体
//        cityListBean=  JSONObject.parseObject(JsonData,CityListBean.class);
        ArrayList<CityListBean> jsonBean = new Gson().fromJson(JsonData,  new TypeToken<List<CityListBean>>() {}.getType());
        options1Items= jsonBean;
        for(int i=0;i<options1Items.size();i++){
            ArrayList<String>cityList=new ArrayList<>();//该省的城市列表
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int j=0;j<options1Items.get(i).getCity().size();j++){
                String cityname=options1Items.get(i).getCity().get(j).getName();
                cityList.add(cityname);
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if(options1Items.get(i).getCity().get(j).getArea()==null||options1Items.get(i).getCity().get(j).getArea().size()==0){
                    City_AreaList.add("");
                }else{
                    for(int k=0;k<options1Items.get(i).getCity().get(j).getArea().size();k++){
                        String AreaName=options1Items.get(i).getCity().get(j).getArea().get(k);
                        City_AreaList.add(AreaName);
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据

            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);

        }
    }

    @Override
    public void initListener() {
        mBinding.lnivSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalMessageMoreActivity.this,PersonalMessageSexActivity.class);
                intent.putExtra("name",mBinding.lnivSex.getText());
                startActivityForResult(intent, CHANGE_SEX);
            }
        });
        mBinding.lnivBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(PersonalMessageMoreActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        Toast.makeText(PersonalMessageMoreActivity.this, DateUtil.getTimeByDateFormat(date,"yyyy-MM-dd"), Toast.LENGTH_SHORT).show();
                        mBinding.lnivBirthday.setText(DateUtil.getTimeByDateFormat(date,"yyyy-MM-dd"));
                    }
                }).build();
                Calendar startDate = Calendar.getInstance();
                String time=mBinding.lnivBirthday.getText();
                String[] ti=time.split("-");
                startDate.set(Integer.parseInt(ti[0]),Integer.parseInt(ti[1])-1,Integer.parseInt(ti[2]));
                pvTime.setDate(startDate);
                pvTime.show();
            }
        });
        mBinding.lnivArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area();
            }
        });
        mBinding.lnivSignName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalMessageMoreActivity.this,PersonalSignNameActivity.class);
                intent.putExtra("name",mBinding.lnivSignName.getText());
                startActivityForResult(intent, CHANGE_SEX);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && null != data && requestCode == CHANGE_SEX) {
            String name= data.getStringExtra("name");
            mBinding.lnivSex.setText(name);
        }
    }
    private void area() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);
                mBinding.lnivArea.setText(tx);
            }
        }) .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {
                String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                Toast.makeText(PersonalMessageMoreActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
//                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
//                .setTitleColor(R.color.white)//标题文字颜色
//                .setSubmitColor(R.color.bg_parent)//确定按钮文字颜色
//                .setCancelColor(R.color.bg_parent)//取消按钮文字颜色
//                .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
//                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小

//                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//添加数据源
        pvOptions.show();
    }
}
