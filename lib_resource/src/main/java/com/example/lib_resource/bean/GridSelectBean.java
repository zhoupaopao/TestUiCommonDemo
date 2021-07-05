package com.example.lib_resource.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by lyf on 2018/8/21 15:50
 *
 * @author lyf
 * desc：
 */
public class GridSelectBean<T> implements MultiItemEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 选中图标
     */
    private int selectedIcon;

    /**
     * 未选中图标
     */
    private int unSelectedIcon;

    /**
     * 是否选中
     */
    private boolean selectStatus;

    /**
     * 值
     */
    private String value;

    /**
     * 其他值 可以传递一些需要的其他值，比如json传等  可以考虑用泛型哟 拓展用的泛型
     */
    private T other;

    public GridSelectBean() {
        super();
    }

    public GridSelectBean(String title, int selectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
    }

    public GridSelectBean(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public GridSelectBean(String title, int selectedIcon, String value) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.value = value;
    }

    public GridSelectBean(String title, String value, T other) {
        this.title = title;
        this.value = value;
        this.other = other;
    }

    public GridSelectBean(String title, boolean selectStatus, String value) {
        this.title = title;
        this.selectStatus = selectStatus;
        this.value = value;
    }

    public GridSelectBean(String title, int selectedIcon, int unSelectedIcon, boolean selectStatus) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
        this.selectStatus = selectStatus;
    }

    public T getOther() {
        return other;
    }

    public void setOther(T other) {
        this.other = other;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(int selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public int getUnSelectedIcon() {
        return unSelectedIcon;
    }

    public void setUnSelectedIcon(int unSelectedIcon) {
        this.unSelectedIcon = unSelectedIcon;
    }

    public boolean isSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(boolean selectStatus) {
        this.selectStatus = selectStatus;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "GridSelectBean{" +
                "title='" + title + '\'' +
                ", selectedIcon=" + selectedIcon +
                ", unSelectedIcon=" + unSelectedIcon +
                ", selectStatus=" + selectStatus +
                ", value='" + value + '\'' +
                ", other='" + other + '\'' +
                '}';
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
