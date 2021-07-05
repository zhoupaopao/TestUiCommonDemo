package com.example.module_login.app.activity;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.lib.base.BaseActivity;
import com.example.lib.utils.Tips;
import com.example.lib.utils.Utils;
import com.example.lib_resource.bean.CustomListItem;
import com.example.lib_resource.bean.CustomTopBean;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.R;
import com.example.module_login.adapter.CustomFormAadpter;
import com.example.module_login.adapter.CustomListAadpter;
import com.example.module_login.adapter.CustomTopAadpter;

import java.util.ArrayList;



@Route(path = ARouterConstants.Login_CustomList_Activity)
public class CustomListActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ArrayList<CustomListItem> arrayList = new ArrayList<>();
    private ArrayList<CustomTopBean> arrayList_top = new ArrayList<>();
    private int num = 1;
    private int limit = 20;
    CustomListAadpter customListAadpter;
    CustomTopAadpter customTopAadpter;
    DrawerLayout ll_drawer;
    private TextView tv_shaixuan;//筛选按钮
    private LinearLayout llLeft;
    private LinearLayout ll_sec;
    private LinearLayout ll_first;
    private LinearLayout ll_title;
    private LinearLayout ll_msg;
    private TextView tv_second;
    private RecyclerView recyclerview_shaixuan;//第三层筛选列表（横向）
    private int nowClick = -1;//当前选中的
    private TextView tv_first;
    private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        tv_shaixuan.setOnClickListener(this);
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerview);
        ll_drawer = findViewById(R.id.ll_drawer);
        tv_name=findViewById(R.id.tv_name);
        tv_name.setText("手机通用列表");
        ll_drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        llLeft = findViewById(R.id.llLeft);
        ll_sec = $(R.id.ll_sec);
        ll_title = $(R.id.ll_title);
        ll_first = $(R.id.ll_first);
        tv_first = $(R.id.tv_first);
        tv_second = $(R.id.tv_second);
        tv_shaixuan = findViewById(R.id.tv_shaixuan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customListAadpter = new CustomListAadpter(R.layout.item_custom_list, arrayList);
        recyclerView.setAdapter(customListAadpter);
        recyclerview_shaixuan = findViewById(R.id.recyclerview_shaixuan);
        //设置横向滚动
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerview_shaixuan.setLayoutManager(layoutManager);
        customTopAadpter = new CustomTopAadpter(R.layout.item_custom_top, arrayList_top);
        recyclerview_shaixuan.setAdapter(customTopAadpter);
        recyclerview_shaixuan.setItemAnimator(new DefaultItemAnimator());
        Log.i("TAG", "initView: ");
//        customTopAadpter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                Tips.show("OnItemClick");
//            }
//        });
        tv_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(ll_first);
            }
        });
        tv_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(ll_first);
            }
        });
        customTopAadpter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
//                Tips.show("onItemChildClick");
                showPop(ll_sec);
                //点击后ui的反应
                checkItem(view, position);

            }
        });
    }

    PopupWindow pop;

    private void showPop(View topView) {
        int[] location = new int[2];
//        if(pop==null){
        if (pop != null) {
            pop = null;
        }
        View view = LayoutInflater.from(this).inflate(
                R.layout.popupwindow_shaixuan, null);
        ll_msg=view.findViewById(R.id.ll_msg);
        Integer[] widHeight = Utils.getWidthAndHeight(getWindow());
        topView.getLocationOnScreen(location);
        int hh = location[1];
        float hhpx = Utils.dip2px(hh, mContext);
        //系统的屏幕高度-控件的y坐标-控件本身的高度-减去标题栏高度
        pop = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, (int) (widHeight[1] - hh-topView.getHeight()-Utils.getStatus(mContext)), true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        ll_msg.setAnimation(AnimationUtils.loadAnimation(view.getContext(),
                R.anim.in_like_drop_down));
//            pop.setWidth(topView.getWidth());
//            pop.setHeight(280);
//            pop.setAnimationStyle(R.style.PopupWindowAnimStyle);
//            pop.setFocusable(true);
//            pop.setTouchable(true);
//            pop.setBackgroundDrawable(
//                    new ColorDrawable(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary)));

//            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT));
//            pop.setContentView(view);
//        }
        if (!pop.isShowing()) {
            int[] position = new int[2];
            topView.getLocationOnScreen(position);
            pop.showAtLocation(topView, Gravity.NO_GRAVITY, position[0],
                    position[1] + topView.getHeight());
        }
    }

    private void checkItem(View view, int position) {
        int viewWidth = ((TextView) view).getWidth();
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        //判断当前view是否在界面当中
        /**
         * 有两种情况
         * 1。view位于左侧，说明left不是0
         * 2.view位于右侧，width不等于right
         */
        Log.d("onItemChildClick: ", viewWidth + "");
        Log.d("onItemChildClick: ", rect + "");
        if (rect.left != 0) {
            Tips.show("" + rect.left);
            recyclerview_shaixuan.scrollBy(-rect.left, 0);
        } else if (rect.right != viewWidth) {
            Tips.show("" + rect.right);
            recyclerview_shaixuan.scrollBy(viewWidth - rect.right, 0);
        } else {
            Tips.show("在中间");
        }
        if (nowClick != position) {
            if (nowClick != -1) {
                arrayList_top.get(nowClick).setClicked(false);
            }
            arrayList_top.get(position).setClicked(true);
            customTopAadpter.notifyDataSetChanged();
            nowClick = position;
        }
    }

    private void initData() {
        addList();
        addTopList();

    }

    private void addTopList() {
        arrayList_top.add(new CustomTopBean("销量排名"));
        arrayList_top.add(new CustomTopBean("产量排名"));
        arrayList_top.add(new CustomTopBean("营业额"));
        arrayList_top.add(new CustomTopBean("品种"));
        arrayList_top.add(new CustomTopBean("销售质量"));
        arrayList_top.add(new CustomTopBean("其他"));
        arrayList_top.add(new CustomTopBean("产量排名"));
        arrayList_top.add(new CustomTopBean("营业额"));
        arrayList_top.add(new CustomTopBean("品种"));
        arrayList_top.add(new CustomTopBean("销售质量"));
        arrayList_top.add(new CustomTopBean("其他"));
        customTopAadpter.notifyDataSetChanged();
    }

    private void addList() {
        for (int i = num; i < limit; i++) {
            arrayList.add(new CustomListItem("id" + i, "number" + i, "name" + i, "title" + i, "message" + i));
        }
        num = num + limit;
        customListAadpter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_shaixuan) {
            ll_drawer.openDrawer(llLeft);
        }
    }
}
