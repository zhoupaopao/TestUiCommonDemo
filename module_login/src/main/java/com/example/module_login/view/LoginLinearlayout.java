package com.example.module_login.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.lib.base.BaseApplication;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.Utils;
import com.example.lib_resource.bean.TokenBean;
import com.example.module_login.R;
import com.example.module_login.databinding.LayoutLoginMainBinding;
import com.example.module_login.viewmodel.LoginLinearLayoutViewModel;


public class LoginLinearlayout extends LinearLayout {
//    private EditText etUserName;
//    private EditText etPassword;
    Drawable leftdrawable;//左侧图标
//    private CheckBox cb_rem;
//    private CheckBox cb_autologin;
    public Button  btn_login;
    public TextView tv_forgetpsd;
    public TextView tv_register;
    private LayoutLoginMainBinding binding;
    public LoginLinearLayoutViewModel viewModel;
    public LoginLinearlayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        binding= DataBindingUtil.setContentView(Utils.getActivityContext(context),R.layout.layout_login_main);
        viewModel=new ViewModelProvider(Utils.getActivityContext(context),new ViewModelProvider.NewInstanceFactory()).get(LoginLinearLayoutViewModel.class);
        binding.setData(viewModel);
        binding.setLifecycleOwner(Utils.getActivityContext(context));
        Log.i("LoginLinearlayout: ", SharedPrefUtil.getUsername()+"|"+SharedPrefUtil.getPassword());

        viewModel.getUserName().setValue(SharedPrefUtil.getUsername());
        viewModel.getUserPassword().setValue(SharedPrefUtil.getPassword());
//                viewModel.getUserName().setValue("18800000001");
//        viewModel.getUserPassword().setValue("123456");
        binding.cbRem.setChecked(SharedPrefUtil.getRemPassword());
        btn_login=binding.btnLogin;
        tv_register=binding.tvRegister;
        tv_forgetpsd=binding.tvForgetpsd;
        leftdrawable = BaseApplication.getAppContext().getResources().getDrawable(R.mipmap.user);
        leftdrawable.setBounds(0, 0, leftdrawable.getMinimumWidth(), leftdrawable.getMinimumHeight());
        binding.etUserName.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Drawable drawable = binding.etUserName.getCompoundDrawables()[2];
                hideIcon();
                if (hasFocus) {
                    //获取焦点
                    //判断是否有内容
                    if (!viewModel.getUserName().getValue().equals("")) {
                        //                    显示
                        Drawable drawable1 = BaseApplication.getAppContext().getResources().getDrawable(R.mipmap.cancel_gray_icon);

                        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                        binding.etUserName.setCompoundDrawables(leftdrawable, null, drawable1, null);
                    }
                }
            }
        });
        binding.etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("")) {
                    hideIcon();
                } else {
                    //                    显示
                    Drawable drawable1 = BaseApplication.getAppContext().getResources().getDrawable(R.mipmap.cancel_gray_icon);
                    drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                    binding.etUserName.setCompoundDrawables(leftdrawable, null, drawable1, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etUserName.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = binding.etUserName.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > binding.etUserName.getWidth() - binding.etUserName.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    binding.etUserName.setText("");
                    binding.etPassword.setText("");
//                    hideIcon();
                }
                //删除
                return false;
            }
        });
    }

    public void remember_check(TokenBean mBean, String username, String password) {
        //对勾选的进行记录
        SharedPrefUtil.putRefrshToken(mBean.getRefresh_token());
        SharedPrefUtil.putToken(mBean.getAccess_token());
        SharedPrefUtil.putTokenBean(mBean);
        SharedPrefUtil.putUsername(username);
        SharedPrefUtil.putRemPassword(binding.cbRem.isChecked());
        if (binding.cbRem.isChecked()) {
            SharedPrefUtil.putPassword(password);
        } else {
            SharedPrefUtil.removePassword();
        }
        if (binding.cbAutologin.isChecked()) {
            SharedPrefUtil.putAutoLogin(true);
        } else {
            SharedPrefUtil.removeAutoLogin();
        }
    }

    private void hideIcon() {

//        etUserName.setCompoundDrawables(null,null,null,null);

        binding.etUserName.setCompoundDrawables(leftdrawable, null, null, null);
    }
}
