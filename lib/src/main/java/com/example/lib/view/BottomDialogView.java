package com.example.lib.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.lib.R;

public class BottomDialogView extends PopupWindow {
    private Context context;
    private String firstValue, secondValue, cancelValue;
    private Button cancelBtn;
    private TextView tv_first, tv_second;
    private View v;
    private DialogClickListenerInterface clickListenerInterface;

    public interface DialogClickListenerInterface {

        //        public void doOk();
        public void doFirst();

        public void doSecond();

        public void doCancel();
    }

    public BottomDialogView(Context context, String firstValue,
                            String secondValue, String cancelValue, View view) {
        this.context = context;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.cancelValue = cancelValue;
        this.v = view;
        init();
    }

    private PopupWindow mPopupWindowAvatar;

    public void init() {
        View view = LayoutInflater.from(v.getContext()).inflate(
                R.layout.assembly_bottom_dialog, null);
        mPopupWindowAvatar = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopupWindowAvatar.setBackgroundDrawable(new BitmapDrawable());
//        mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        view.findViewById(R.id.cancel_pop).setOnClickListener(new clickListener());
        view.findViewById(R.id.take_phone_tv).setOnClickListener(new clickListener());
        view.findViewById(R.id.select_phone_tv).setOnClickListener(new clickListener());
        LinearLayout ll_bg = view.findViewById(R.id.ll_bg);
        ll_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindowAvatar.dismiss();
            }
        });
        mPopupWindowAvatar.showAtLocation(v, Gravity.NO_GRAVITY, x, y + mPopupWindowAvatar.getHeight());
//        mPopupWindowAvatar.showAsDropDown(v);
    }

    public PopupWindow getmPopupWindowAvatar() {
        return mPopupWindowAvatar;
    }

    public void setClicklistener(
            DialogClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            if (id == R.id.cancel_pop) {
                clickListenerInterface.doCancel();
            }
            if (id == R.id.take_phone_tv) {
                clickListenerInterface.doFirst();
            }
            if (id == R.id.select_phone_tv) {
                clickListenerInterface.doSecond();
            }
//            switch (id) {
//                case R.id.ok_btn:
//                    clickListenerInterface.doOk();
//                    break;
//                case R.id.cancel_btn:
//                    clickListenerInterface.doCancel();
//                    break;
//            }
        }

    }

    ;
}
