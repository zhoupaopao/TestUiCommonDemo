package com.example.lib.view;


import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.example.lib.R;
import com.example.lib_resource.bean.CustomTopBean;

import java.util.ArrayList;

//顶部筛选项的弹出框
public class PriorityPopupWindow extends PopupWindow {
    /**布局加载器**/
    private LayoutInflater inflater;
    /** 上下文 **/
    private Activity activity;
    /** PopupWindow对象 **/
    public PopupWindow popupWindow = null;
    /**优先筛选条件列表**/
    private GridView gvCoditionList;
    /** 判断gridview是否进行滚动事件 **/
    protected boolean isScroll = false;
    /** 绑定显示视图的view **/
    protected View view = null;
    /**属性值列表**/
    private ArrayList<CustomTopBean> valueBeanList;
    /**适配器**/
//    private CoditionAdapter coditionAdapter;
    /**重置**/
    private Button btReset;
    /**确认**/
    private Button btConfirm;
    /**优先筛选**/
    private SharedPreferences priorityPre;
    /**搜索类型**/
    private int searchType;
    /**弹窗阴影部分**/
    private View vBg;
    /**构造方法**/
    public PriorityPopupWindow(){
    }
    /**初始化弹窗界面**/
    public void initPopupWindow(final Activity activity, View view, int layoutID, ArrayList<CustomTopBean> valueBeanList, final SharedPreferences priorityPre, final int searchType){
        this.view = view;
        this.activity = activity;
        this.valueBeanList = valueBeanList;
        this.priorityPre = priorityPre;
        this.searchType = searchType;
        inflater = LayoutInflater.from(activity);
        View dialogView = inflater.inflate(layoutID, null);
        createPopupWindow(dialogView);
        popupWindow = new PopupWindow(dialogView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        // 此处必须设置，否则点击事件无效，选择不了
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置显示动画
//    popupWindow.setAnimationStyle(R.style.PopupWindowAinmation);
        // 设置边缘点击可消失
        popupWindow.setOutsideTouchable(true);
        // 设置pop出来后，软键盘的属性，避免pop挡住软键盘，以及pop获取焦点后软键盘会自动隐藏问题
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        // 使用该属性时，在滑动pop的时候不会自动弹出软键盘
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
        // 为了设置返回按钮关闭弹层
        popupWindow.setFocusable(true);
        dialogView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    hidden();
                    popupWindow.setFocusable(false);
                    popupWindow.update();
                    return true; // 消费掉该事件
                }
                return false;
            }
        });
        /**
         * PopupWindow消失时事件
         */
//    popupWindow.setOnDismissListener(new OnDismissListener() {
//      @Override
//      public void onDismiss() {
//        //还原优先筛选控件显示状态
//        if(activity instanceof SearchResultActivity){
//          ((SearchResultActivity)activity).setSelectState(4);
//        }
//
//        if(null != priorityPre){
//          boolean isClickConfirm = priorityPre.getBoolean("isClickConfirm", false);
//          Editor editor = priorityPre.edit();
//          editor.putInt("searchType", searchType);
//          editor.putBoolean("isClickConfirm", isClickConfirm);
//          editor.commit();
//        }
//        hidden();
//        popupWindow.setFocusable(false);
//        popupWindow.update();
//      }
//    });
        // 手触碰到pop时，获取焦点，以实现点击事件
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 刚进入pop界面，listveiw滚动标识设为false，pop界面焦点设为true。
                isScroll = false;
                popupWindow.setFocusable(true);
                popupWindow.update();
                return false;
            }
        });
    }
    /**
     * 创建弹窗
     * @Description
     * @author XiongJie
     * @param dialogView
     */
    private void createPopupWindow(View dialogView){
        initView(dialogView);
        initData();
        setListener();
    }
    /**
     * @Description 初始化界面控件
     * @author XiongJie
     * @param dialogView
     */
    private void initView(View dialogView) {
//        gvCoditionList = (GridView) dialogView.findViewById(R.id.gv_codition_list);
//        btReset = (Button) dialogView.findViewById(R.id.bt_reset);
//        btConfirm = (Button) dialogView.findViewById(R.id.bt_confirm);
//        vBg = dialogView.findViewById(R.id.v_bg);
    }
    /**
     * @Description 初始化数据
     * @author XiongJie
     */
    public void initData() {
//        coditionAdapter = new CoditionAdapter(activity,gvCoditionList);
//        coditionAdapter.setData(valueBeanList);
//        gvCoditionList.setAdapter(coditionAdapter);
//        btConfirm.setBackgroundColor(activity.getResources().getColor(R.color.c_d82828));
    }
    /**
     * @Description 事件处理
     * @author XiongJie
     */
    private void setListener() {
        btReset.setOnClickListener(new ViewClickListener());
        btConfirm.setOnClickListener(new ViewClickListener());
        vBg.setOnClickListener(new ViewClickListener());
    }
    /**
     * 返回当前popupWindow是否显示状态
     */
    public boolean hasShowing() {
        return null == popupWindow ? false : popupWindow.isShowing();
    }
    /**
     * 显示PopupWindow界面
     */
    public void show() {
        if (hasShowing()) {
            return;
        }
        if (null != activity && !activity.isFinishing()) {
            if (null == view) {
                view = activity.getWindow().getDecorView();
            }
            popupWindow.showAsDropDown(view);
        }
    }
    /**
     * 隐藏PopupWindow界面
     */
    public void hidden() {
        if (null == popupWindow) {
            return;
        }
        popupWindow.dismiss();
    }
    /**
     * 按钮点击事件
     * @Description
     * @author XiongJie
     * @date 2016年11月6日 下午4:12:27
     */
    class ViewClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
//                case R.id.bt_reset:
//                    //重置
//                    if(null != valueBeanList && valueBeanList.size() > 0){
//                        for(ValueBean valueBean : valueBeanList){
//                            valueBean.setIsChoose("1002");
//                        }
//                        coditionAdapter.setData(valueBeanList);
//                    }
//                    break;
//                case R.id.bt_confirm:
//                    //确认
//                    Editor editor = priorityPre.edit();
//                    editor.putInt("searchType", searchType);
//                    editor.putBoolean("isClickConfirm", true);
//                    editor.commit();// 提交修改
//                    hidden();
//                    break;
//                case R.id.v_bg:
//                    hidden();
//                    break;
            }
        }
    }
    /**
     * 获取PopupWindow对象
     * @Description
     * @author XiongJie
     * @return
     */
    public PopupWindow getPopupWindow() {
        return popupWindow;
    }
}
