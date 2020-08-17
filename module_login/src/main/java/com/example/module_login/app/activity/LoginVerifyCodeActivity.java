package com.example.module_login.app.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib.base.BaseActivity1;
import com.example.lib.utils.ToastUtils;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_login.R;
import com.example.module_login.databinding.ActivityLoginRegisterBinding;
import com.example.module_login.databinding.ActivityLoginVerifycodeBinding;
import com.example.module_login.viewmodel.LoginRegisterViewModel;

import java.lang.ref.WeakReference;

@Route(path = ARouterConstants.Login_VerifyCode_Activity)
public class LoginVerifyCodeActivity extends BaseActivity1<ActivityLoginVerifycodeBinding> {
    @Autowired
    String mobile_num;
    @Autowired
    String type;
    private LoginRegisterViewModel viewModel;
    LooperHandler mHandler=new LooperHandler(this);
    static long ONECE_TIME=1000;
    int TOTAL_TIME_SEC=59;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_verifycode;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mBinding.tvPhone.setText(mobile_num);
        mBinding.btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast(mBinding.codeView.getContent());
                ARouter.getInstance().build(ARouterConstants.Login_SetPassword_Activity).withString("type",type).navigation();

            }
        });
        mBinding.tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.tvResend.setClickable(false);
                mBinding.tvResend.setTextColor(mContext.getResources().getColor(R.color.color_yzm_line));
                mBinding.tvLasttime.setVisibility(View.VISIBLE);
                handlerPostDelayed();
            }
        });
    }
    /**
     * handler 持有当前 Activity 的弱引用防止内存泄露
     */
    private  class LooperHandler extends Handler {
        WeakReference<LoginVerifyCodeActivity> mWeakReference;

        LooperHandler(LoginVerifyCodeActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoginVerifyCodeActivity verifyCodeActivity = mWeakReference.get();
            switch (msg.what) {
                case 0:

                    break;
                case 1:

                    break;
                case 2:
                    verifyCodeActivity.mHandler.postDelayed(verifyCodeActivity.mRunnable, ONECE_TIME);
                    mBinding.tvLasttime.setText(String.valueOf(TOTAL_TIME_SEC)+"s后 ");
                    if (TOTAL_TIME_SEC <= 0) {
                        verifyCodeActivity.mHandler.removeCallbacks(verifyCodeActivity.mRunnable);
                        mBinding.tvLasttime.setVisibility(View.GONE);
                        mBinding.tvResend.setTextColor(verifyCodeActivity.getResources().getColor(R.color.bg_main));
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
    private void handlerPostDelayed() {
        mHandler.post(mRunnable);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);//这段只能终止这一次的handle，再次进来会继续的
        mHandler.removeCallbacksAndMessages(null);
    }
}
