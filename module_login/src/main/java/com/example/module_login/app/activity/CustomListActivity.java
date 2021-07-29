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
import com.example.lib.base.MvvmActivity;
import com.example.lib.intface.OnCheckClickListener;
import com.example.lib.utils.Tips;
import com.example.lib.utils.Utils;
import com.example.lib_resource.bean.CustomListItem;
import com.example.lib_resource.bean.CustomTopBean;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.BR;
import com.example.module_login.R;
import com.example.module_login.adapter.CustomFormAadpter;
import com.example.module_login.adapter.CustomListAadpter;
import com.example.module_login.adapter.CustomTopAadpter;
import com.example.module_login.databinding.ActivityCustomListBinding;
import com.example.module_login.viewmodel.CustomListViewModel;

import java.util.ArrayList;
import java.util.Iterator;


@Route(path = ARouterConstants.Login_CustomList_Activity)
public class CustomListActivity extends MvvmActivity<ActivityCustomListBinding, CustomListViewModel> implements CustomListViewModel.CallBack{
    CustomListAadpter customListAadpter;
    CustomTopAadpter customTopAadpter;





    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected CustomListViewModel getViewModel() {
        return new CustomListViewModel();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_list;
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
       LinearLayout  ll_msg=view.findViewById(R.id.ll_msg);
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
            viewDataBinding.recyclerviewShaixuan.scrollBy(-rect.left, 0);
        } else if (rect.right != viewWidth) {
            Tips.show("" + rect.right);
            viewDataBinding.recyclerviewShaixuan.scrollBy(viewWidth - rect.right, 0);
        } else {
            Tips.show("在中间");
        }
        if (viewModel.getNowClick() != position) {
            if (viewModel.getNowClick() != -1) {
                viewModel.getArrayList_top().get(viewModel.getNowClick()).setClicked(false);
            }
            viewModel.getArrayList_top().get(position).setClicked(true);
            customTopAadpter.notifyDataSetChanged();
            viewModel.setNowClick(position);
        }
    }

    @Override
    protected void initListener() {
        viewDataBinding.llDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        viewDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        customListAadpter = new CustomListAadpter(R.layout.item_custom_list, viewModel.getArrayList());
        viewDataBinding.recyclerview.setAdapter(customListAadpter);
        //设置横向滚动
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        viewDataBinding.recyclerviewShaixuan.setLayoutManager(layoutManager);
        customTopAadpter = new CustomTopAadpter(R.layout.item_custom_top, viewModel.getArrayList_top());
        viewDataBinding.recyclerviewShaixuan.setAdapter(customTopAadpter);
        viewDataBinding.recyclerviewShaixuan.setItemAnimator(new DefaultItemAnimator());
        Log.i("TAG", "initView: ");
//        customTopAadpter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                Tips.show("OnItemClick");
//            }
//        });
        viewDataBinding.tvFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(viewDataBinding.llFirst);
            }
        });
        viewDataBinding.tvSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(viewDataBinding.llFirst);
            }
        });
        viewDataBinding.tvShaixuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDataBinding.llDrawer.openDrawer(viewDataBinding.llLeft);
            }
        });
        viewDataBinding.tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ArrayList<CustomListItem>lastList=new ArrayList<>();
                for(CustomListItem customListItem:viewModel.getArrayList()){
                    if(customListItem.isChecked()){
                        customListItem.setName("11111111");
                    }
                }
                customListAadpter.notifyDataSetChanged();
//                viewModel.getArrayList().remove()
            }
        });
        viewDataBinding.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterator<CustomListItem> it = viewModel.getArrayList().iterator();
                while(it.hasNext()){
                    CustomListItem x = it.next();
                    if(x.isChecked()){
                        it.remove();
                    }
                }
                customListAadpter.notifyDataSetChanged();
            }
        });
        customTopAadpter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
//                Tips.show("onItemChildClick");
                showPop(viewDataBinding.llSec);
                //点击后ui的反应
                checkItem(view, position);

            }
        });
        viewDataBinding.twllTitle.getTv_edit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去编辑
                customListAadpter.setVisible(0);
            }
        });
        customListAadpter.setOnCheckClickListener(new OnCheckClickListener() {
            @Override
            public void onCheckClick(int position,boolean check) {

            }
        });
    }
    @Override
    protected void initData() {
        viewModel.addList();
        viewModel.addTopList();

    }

    @Override
    public void listAdapter() {
        customListAadpter.notifyDataSetChanged();
    }

    @Override
    public void topAdapter() {
customTopAadpter.notifyDataSetChanged();
    }
}
