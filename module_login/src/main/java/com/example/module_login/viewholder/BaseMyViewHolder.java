package com.example.module_login.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;


/**
 * Created by lyf on 2018/8/3 16:37
 *
 * @author lyf
 * desc：通用ViwHolder
 */
public class BaseMyViewHolder extends BaseViewHolder {

    public BaseMyViewHolder(View view) {
        super(view);
    }

    public void setText(@IdRes int idRes, String value) {
        ((TextView) getView(idRes)).setText(value);

    }
}
