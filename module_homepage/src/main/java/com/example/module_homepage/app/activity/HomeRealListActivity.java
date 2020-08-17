package com.example.module_homepage.app.activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;

import com.example.module_homepage.viewmodel.UserBean;
import com.example.module_homepage.R;
import com.example.module_homepage.app.adapter.HomeListAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class HomeRealListActivity extends AppCompatActivity {
    ListView listView;
    List<UserBean> list=new ArrayList<>();
    UserBean userBean=new UserBean();
    HomeListAdapter<UserBean> adapter;
    String[]imgurls={"http://qzonestyle.gtimg.cn/qzone/app/weishi/client/testimage/256/1.jpg",
    "https://img-blog.csdnimg.cn/20190323161159133.png",
    "https://img-blog.csdnimg.cn/20190323161242873.png"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_real_list);
//        userBean=b
        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                userBean.request();
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        listView=findViewById(R.id.listview);


         adapter=new HomeListAdapter<>(this,list);
        listView.setAdapter(adapter);
        initData();
    }

    private void initData() {
        userBean.getSource().observe(this, new Observer<ArrayList<UserBean>>() {
            @Override
            public void onChanged(ArrayList<UserBean> userBeans) {
                list.addAll(userBeans);
                adapter.notifyDataSetChanged();
            }
        });
        for(int i=0;i<imgurls.length;i++){
            UserBean userBean1=new UserBean("小命"+i,"note"+i,imgurls[i]);
            list.add(userBean1);
        }
    }
}
