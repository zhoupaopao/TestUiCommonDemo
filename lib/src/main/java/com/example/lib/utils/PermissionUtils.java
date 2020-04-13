package com.example.lib.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.lib.base.BaseActivity;
import com.example.lib.base.BaseActivity1;
import com.example.lib.base.BaseFragmentActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmx on 2017/11/16.
 */

public class PermissionUtils {
    public static BaseActivity context;

    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE=200;
    /**
     * 单例对象实例
     */
    private static PermissionUtils instance = null;

    public static PermissionUtils getInstance(BaseActivity context) {
        if (instance == null) {
            instance = new PermissionUtils(context);
        }
        return instance;
    }

    private PermissionUtils(BaseActivity context) {
        this.context=context;
    }

    public  static boolean requestCamerPermissions( int requestCode)
    {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {//没有权限
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * 判断请求权限是否成功
     * @param grantResults
     * @return
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 找到没有授权的权限
     * @param activity
     * @param permission
     * @return
     */
    public static List<String> findDeniedPermissions(BaseFragmentActivity activity, String... permission)
    {
        //存储没有授权的权限
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission)
        {
            if (ContextCompat.checkSelfPermission(activity,value) != PackageManager.PERMISSION_GRANTED)
            {
                //没有权限 就添加
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }


    public static List<String> findDeniedPermissionsActivity(BaseActivity1 activity, String... permission)
    {
        //存储没有授权的权限
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission)
        {
            if (ContextCompat.checkSelfPermission(activity,value) != PackageManager.PERMISSION_GRANTED)
            {
                //没有权限 就添加
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    /**
     * 检测权限
     * @param activity
     * @param view //随便一个view 来用显示snackbar
     * @param permissions  //请求的权限组
     * @param requestCode  //请求码
     */
    public  static void checkPermission(final BaseFragmentActivity activity, View view, final String[] permissions, final int requestCode, PermissionInterface permissionInterface) {
        //小于23 就什么都不做
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        List<String> deniedPermissions = findDeniedPermissions(activity, permissions);
        if (deniedPermissions!=null&&deniedPermissions.size()>0) {
            //大于0,表示有权限没申请
            requestContactsPermissions(activity, view,  deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
        } else {
            //拥有权限
            permissionInterface.success(requestCode);

        }
    }

    public  static void checkPermissionActivity(final BaseActivity1 activity, View view, final String[] permissions, final int requestCode, PermissionInterface permissionInterface) {
        //小于23 就什么都不做
        if (Build.VERSION.SDK_INT < 23) {
            permissionInterface.success(requestCode);
            return;
        }
        List<String> deniedPermissions = findDeniedPermissionsActivity(activity, permissions);
        if (deniedPermissions!=null&&deniedPermissions.size()>0) {
            //大于0,表示有权限没申请
            requestContactsPermissionsActivity(activity, view,  deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
        } else {
            //拥有权限
            permissionInterface.success(requestCode);

        }
    }

    /**
     * 请求权限
     */
    public static void requestContactsPermissions(final BaseFragmentActivity activity, View view, final String[] permissions, final int requestCode) {
        //默认是false,但是只要请求过一次权限就会为true,除非点了不再询问才会重新变为false
        if (shouldShowPermissions(activity,permissions)) {
            Snackbar.make(view, "需要相关权限",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(activity, permissions,
                                            requestCode);
                        }
                    })
                    .show();
        } else {

            // 无需向用户界面提示，直接请求权限,如果用户点了不再询问,即使调用请求权限也不会出现请求权限的对话框
            ActivityCompat.requestPermissions(activity,
                    permissions,
                    requestCode);
        }
    }

    public static void requestContactsPermissionsActivity(final BaseActivity1 activity, View view, final String[] permissions, final int requestCode) {
        //默认是false,但是只要请求过一次权限就会为true,除非点了不再询问才会重新变为false
        if (shouldShowPermissionsActivity(activity,permissions)) {
            Snackbar.make(view, "需要相关权限",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(activity, permissions,
                                            requestCode);
                        }
                    })
                    .show();
        } else {

            // 无需向用户界面提示，直接请求权限,如果用户点了不再询问,即使调用请求权限也不会出现请求权限的对话框
            ActivityCompat.requestPermissions(activity,
                    permissions,
                    requestCode);
        }
    }

    /**
     * 检测这些权限中是否有 没有授权需要提示的
     * @param activity
     * @param permission
     * @return
     */
    public static boolean shouldShowPermissions(BaseFragmentActivity activity, String... permission)
    {

        for (String value : permission)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,value))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean shouldShowPermissionsActivity(BaseActivity1 activity, String... permission)
    {

        for (String value : permission)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,value))
            {
                return true;
            }
        }
        return false;
    }

}
