package com.example.module_login.viewmodel;

import com.example.lib.base.IBaseView;
import com.example.lib.viewmodel.MvvmBaseViewModel;
import com.example.lib_resource.bean.CustomListItem;
import com.example.lib_resource.bean.CustomTopBean;

import java.util.ArrayList;

public class CustomListViewModel extends MvvmBaseViewModel<CustomListViewModel.CallBack> {

    private ArrayList<CustomListItem> arrayList = new ArrayList<>();
    private ArrayList<CustomTopBean> arrayList_top = new ArrayList<>();
    private int num = 1;
    private int limit = 20;
    private int nowClick = -1;//当前选中的

    public int getNowClick() {
        return nowClick;
    }

    public void setNowClick(int nowClick) {
        this.nowClick = nowClick;
    }

    public int getLimit() {
        return limit;
    }

    public int getNum() {
        return num;
    }

    public ArrayList<CustomTopBean> getArrayList_top() {
        return arrayList_top;
    }

    public ArrayList<CustomListItem> getArrayList() {
        return arrayList;
    }

    public interface CallBack extends IBaseView {
        void listAdapter();
        void topAdapter();
    }

    public void addTopList() {
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

    }
    public void addList() {
        for (int i = num; i < limit; i++) {
            arrayList.add(new CustomListItem("id" + i, "number" + i, "name" + i, "title" + i, "message" + i));
        }
        num = num + limit;
        getPageView().listAdapter();
    }

}
