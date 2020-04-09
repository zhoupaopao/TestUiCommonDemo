package com.example.lib.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.multidex.MultiDexApplication;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib.http.ApiService;
import com.example.lib.http.HttpClient;
import com.example.lib.http.IProgressDialogAction;
import com.example.lib.utils.ClassUtils;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.StringUtils;
import com.example.lib.utils.Utils;
import com.example.lib_resource.bean.TokenBean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;


/**
 * 要想使用BaseApplication，必须在组件中实现自己的Application，并且继承BaseApplication；
 * 组件中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 组件的Application需置于java/debug文件夹中，不得放于主代码；
 * 组件中获取Context的方法必须为:Utils.getContext()，不允许其他写法；
 *
 * @author 2016/12/2 17:02
 * @version V1.0.0
 * @name BaseApplication
 */
public class BaseApplication extends MultiDexApplication implements IProgressDialogAction {

    public static final String ROOT_PACKAGE = "com.emr_pad";

    private static BaseApplication sInstance;

    private static Context mContext;

    private List<IApplicationDelegate> mAppDelegateList;

    public static Context getAppContext() {
        return mContext;
    }
    public static BaseApplication getIns() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mContext=this;
        Utils.init(this);
        mAppDelegateList = ClassUtils.getObjectsWithInterface(this, IApplicationDelegate.class, ROOT_PACKAGE);
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onCreate();
        }
        if (isAppDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
//        //回调接口初始化完成接口回调
//        QbSdk.PreInitCallback pcb=new QbSdk.PreInitCallback() {
//            @Override
//            public void onCoreInitFinished() {
//
//            }
//            @Override
//            public void onViewInitFinished(boolean b) {
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.e("myApplication", " x5内核加载成功？" + b);
//            }
//        };
//
//        //x5内核预加载，异步初始化x5 webview所需环境
//        QbSdk.initX5Environment(getApplicationContext(), pcb);

        }

    private static List<Activity> activityList = new ArrayList<>();
    private static List<Activity> activitySubmitList = new ArrayList<>();
    /**
     * 添加Activity到容器中
     */
    public static void addActivity(Activity activity) {
        if (activityList != null && activityList.size() > 0) {
            if (!activityList.contains(activity)) {
                activityList.add(activity);
            }
        } else {
            activityList.add(activity);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTerminate();
        }
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onLowMemory();
        }
    }
    public static boolean isAppDebug() {
        if (StringUtils.isSpace(mContext.getPackageName())) return false;
        try {
            PackageManager pm = mContext.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(mContext.getPackageName(), 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTrimMemory(level);
        }
    }
    public static String refresh_Token(){
        final String[] re_tok = {""};
        Log.i("refresh_Token: ", re_tok[0]);
        RequestBody requestBody1 = new FormBody.Builder()
                .add("grant_type", "refresh_token")
                .add("refresh_token", SharedPrefUtil.getRefrshToken())
                .build();

        final Call<JsonObject> categories= HttpClient.createApi(ApiService.class)
                .rrfresh_token(requestBody1);
//        new Thread(new Runnable() { // 子线程执行
//            @Override
//            public void run() {

                try {
                    // 4. call对象执行同步请求，请求数据在子线程中执行
                    Response<JsonObject> execute = categories.execute();
                    TokenBean mBean = new Gson().fromJson(execute.body().toString(), TokenBean.class);
                    SharedPrefUtil.putRefrshToken(mBean.getRefresh_token());
                    SharedPrefUtil.putToken(mBean.getAccess_token());
                    SharedPrefUtil.putTokenBean(mBean);
                    re_tok[0]=mBean.getToken_type() + " " + mBean.getAccess_token();
                } catch (Exception e) {
                    //退出登录
                    doReLogin();
                    e.printStackTrace();

                }


//            }
//        }).start();
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ProgressDialogSubscriber<JsonObject>(sInstance, true) {
//                               @Override
//                               public void onNext(JsonObject data) {
//                                   super.onNext(data);
//                                   TokenBean mBean = new Gson().fromJson(data.toString(), TokenBean.class);
//                                   re_tok[0] ="13231";
//
//                               }
//
//                               @Override
//                               public void onError(Throwable e) {
//                                   super.onError(e);
//                                   e.printStackTrace();
//                               }
//                           }
//                );
        Log.i("refresh_Token: ", re_tok[0]);
        return re_tok[0];
    }
//    public static void refresh_Token(){
//        Log.i("refresh_Token: ", "refresh_Token: ");
//        RequestBody requestBody1 = new FormBody.Builder()
//                .add("grant_type", "refresh_token")
//                .add("refresh_token", SharedPrefUtil.getRefrshToken())
//                .build();
//        HttpClient.createApi(ApiService.class)
//                .rfresh_token(requestBody1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ProgressDialogSubscriber<JsonObject>(sInstance, true) {
//                               @Override
//                               public void onNext(JsonObject data) {
//                                   super.onNext(data);
//                                   TokenBean mBean = new Gson().fromJson(data.toString(), TokenBean.class);
//
//                               }
//
//                               @Override
//                               public void onError(Throwable e) {
//                                   super.onError(e);
//                                   e.printStackTrace();
//                               }
//                           }
//                );
//    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void dismissProgressDialog() {

    }

    public static void doReLogin() {

        Log.e("zzz", "执行了dorelogin");

        if (activityList != null && activityList.size() > 0) {
            for (Activity activity : activityList) {
                activity.finish();
            }
        }
//        Intent intent = new Intent(getAppContext(), LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        getAppContext().startActivity(intent);

        SharedPrefUtil.exitLogin();

    }
}
