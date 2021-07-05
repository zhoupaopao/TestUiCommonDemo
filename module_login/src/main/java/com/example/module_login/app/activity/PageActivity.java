package com.example.module_login.app.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.lib.base.BaseActivity;
import com.example.lib.view.PageControl;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.R;

/**
 * 测试分页
 */
@Route(path = ARouterConstants.Login_Page_Activity)
public class PageActivity extends BaseActivity {
    private PageControl pageControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        pageControl=findViewById(R.id.pageControl);
        pageControl.setPageChangeListener(new PageControl.OnPageChangeListener() {
            @Override
            public void pageChanged(PageControl pageControl, int numPerPage) {

            }
        });
        pageControl.setTotalPage(20);
    }
}
