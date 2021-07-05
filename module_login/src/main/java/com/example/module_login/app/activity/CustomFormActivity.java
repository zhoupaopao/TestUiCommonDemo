package com.example.module_login.app.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib.base.BaseActivity;
import com.example.lib_resource.bean.CustomItemBean;
import com.example.lib_resource.bean.ExpListenerBean;
import com.example.lib_resource.bean.FromValue;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.R;
import com.example.module_login.adapter.CustomFormAadpter;

import java.util.ArrayList;
import java.util.List;


@Route(path = ARouterConstants.Login_CustomForm_Activity)
public class CustomFormActivity extends BaseActivity {
    private RecyclerView recyclerView;
    CustomFormAadpter customFormAadpter;
    /**
     * 表单构建
     */
    private List<CustomItemBean> formBeans = new ArrayList<>();
    /**
     * 各选项值
     */
    private List<FromValue> values1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_form);
        initView();
        initData();
        // 动态表单
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customFormAadpter = new CustomFormAadpter(formBeans, true);
        recyclerView.setAdapter(customFormAadpter);
    }

    private void initData() {
        // 输入框 字符串 整数 小数
        formBeans.add(new CustomItemBean(CustomItemBean.TITLE, "1", "个人情况", "个人情况", 1, ""));
        formBeans.add(new CustomItemBean(CustomItemBean.EDIT, "2", "情况描述", "ssss", 1, "请输入情况描述"));
        formBeans.add(new CustomItemBean(CustomItemBean.EDIT, "3", "身高(cm)", "222", 2, "请输入身高"));//editexp控制输入框的可输入类型
        formBeans.add(new CustomItemBean(CustomItemBean.EDIT, "4", "体重(kg)", "222.22", 3, "请输入体重"));
        formBeans.add(new CustomItemBean(CustomItemBean.TEXT, "5", "性格描述", "性格很好"));
        formBeans.add(new CustomItemBean(CustomItemBean.TEXT, "6", "爱好", "打游戏"));

        formBeans.add(new CustomItemBean(CustomItemBean.TITLE, "7", "更多", "更多", 1, ""));
        formBeans.add(new CustomItemBean(CustomItemBean.TEXT, "8", "生肖", "狗"));
        values1 = new ArrayList<>();
        values1.add(new FromValue("男(值)", "男"));
        values1.add(new FromValue("女(值)", "女"));
        values1.add(new FromValue("中性(值)", "中性"));
        values1.add(new FromValue("未知(值)", "未知"));
        formBeans.add(new CustomItemBean(CustomItemBean.SWITCHMORE, "9", "性别", "未知(值)", values1));
        // 多选
        values1 = new ArrayList<>();
        values1.add(new FromValue("发烧(值)", "发烧"));
        values1.add(new FromValue("咳嗽(值)", "咳嗽"));
        values1.add(new FromValue("头晕(值)", "头晕"));
        values1.add(new FromValue("头痛(值)", "头痛"));
        values1.add(new FromValue("鼻塞(值)", "鼻塞"));
        values1.add(new FromValue("全身乏力(值)", "全身乏力"));
        values1.add(new FromValue("胸闷气短(值)", "胸闷气短"));
        values1.add(new FromValue("呼吸困难(值)", "呼吸困难"));
        values1.add(new FromValue("味觉失灵(值)", "味觉失灵"));
        values1.add(new FromValue("嗅觉失灵(值)", "嗅觉失灵"));
        values1.add(new FromValue("其他见备注(值)", "其他见备注"));
        formBeans.add(new CustomItemBean(CustomItemBean.TEXTMORE, "10", "其他情况", "", values1));

        ExpListenerBean.ExpChangeBean.ExpParamsBean expParamsBean=new ExpListenerBean.ExpChangeBean.ExpParamsBean("5","");
        ExpListenerBean.ExpChangeBean expChangeBean=new ExpListenerBean.ExpChangeBean("setVisible",expParamsBean);

        ExpListenerBean expListenerBean=new ExpListenerBean("value",expChangeBean);
        formBeans.add(new CustomItemBean(CustomItemBean.CHANGE, "11", "性格描述", "设置性格描述可见不可见",expListenerBean));
        CustomItemBean customItemBeanList=new CustomItemBean(CustomItemBean.LISTVIEW,"11", "性格描述", "设置性格描述可见不可见");

        ArrayList<CustomItemBean>lists=new ArrayList<>();
        lists.add(new CustomItemBean(CustomItemBean.TITLE, "1", "扩展列表", "扩展列表", 1, ""));
        lists.add(new CustomItemBean(CustomItemBean.EDIT, "2", "扩展列表1", "ssss", 1, "请输入情况描述"));
        lists.add(new CustomItemBean(CustomItemBean.EDIT, "3", "扩展列表2", "222", 2, "请输入身高"));//editexp控制输入框的可输入类型
        lists.add(new CustomItemBean(CustomItemBean.EDIT, "4", "扩展列表3", "222.22", 3, "请输入体重"));
        lists.add(new CustomItemBean(CustomItemBean.TEXT, "5", "扩展列表4", "性格很好"));
        customItemBeanList.setLists(lists);
        formBeans.add(customItemBeanList);
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerview);
    }
}
