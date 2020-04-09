package com.example.module_personal.app.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.example.lib.base.BaseActivity1;
import com.example.lib.utils.ToastUtils;
import com.example.module_personal.R;
import com.example.module_personal.databinding.ActivityPersonalVerifyCodeBinding;
import com.example.module_personal.viewmodel.PersonalChangeMobelViewModel;
import com.example.module_personal.viewmodel.PersonalVerifyCodeViewModel;

import java.lang.ref.WeakReference;

/**
 * 验证码页面
 */
public class PersonalVerifyCodeActivity  extends BaseActivity1<ActivityPersonalVerifyCodeBinding> {
    private PersonalVerifyCodeViewModel viewModel;
    LooperHandler mHandler=new LooperHandler(PersonalVerifyCodeActivity.this);
    static long ONECE_TIME=1000;
    int TOTAL_TIME_SEC=59;
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_verify_code;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(PersonalVerifyCodeViewModel.class);
        mBinding.setData(viewModel);
        mBinding.setLifecycleOwner(this);
        mBinding.tvPhone.setText(getIntent().getStringExtra("mobile"));
        mBinding.btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast(mBinding.codeView.getContent());
                setResult(RESULT_OK);
                finish();
            }
        });
        mBinding.tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行发送操作
                //结束后执行倒计时
                mBinding.tvResend.setClickable(false);
                mBinding.tvResend.setTextColor(PersonalVerifyCodeActivity.this.getResources().getColor(R.color.color_yzm_line));
                mBinding.llTime.setVisibility(View.VISIBLE);
                handlerPostDelayed();
            }
        });
    }
    /**
     * handler 持有当前 Activity 的弱引用防止内存泄露
     */
    private  class LooperHandler extends Handler {
        WeakReference<PersonalVerifyCodeActivity> mWeakReference;

        LooperHandler(PersonalVerifyCodeActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PersonalVerifyCodeActivity verifyCodeActivity = mWeakReference.get();
            switch (msg.what) {
                case 2:
                    verifyCodeActivity.mHandler.postDelayed(verifyCodeActivity.mRunnable, ONECE_TIME);
                    viewModel.getTime().setValue(TOTAL_TIME_SEC);
//                    mBinding.tvLasttime.setText(String.valueOf(TOTAL_TIME_SEC)+"s后 ");
                    if (TOTAL_TIME_SEC <= 0) {
                        verifyCodeActivity.mHandler.removeCallbacks(verifyCodeActivity.mRunnable);
                        mBinding.llTime.setVisibility(View.GONE);
                        mBinding.tvResend.setTextColor(verifyCodeActivity.getResources().getColor(R.color.status_bar_color));
                        mBinding.tvResend.setClickable(true);
                        TOTAL_TIME_SEC=60;
                    }
                    TOTAL_TIME_SEC--;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + msg.what);
            }
        }
    }
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Message msg = mHandler.obtainMessage(2);
            mHandler.sendMessage(msg);
        }

    };
    // handler+postDelayed 方式，反复发送延时消息
    private void handlerPostDelayed() {
        mHandler.post(mRunnable);
    }
}
