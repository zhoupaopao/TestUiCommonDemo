package com.example.module_login.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.lib.http.Constants;
import com.example.lib.utils.SharedPref;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.Tips;
import com.example.lib.viewmodel.MvvmBaseViewModel;

public class LoginIpSettingViewModel extends MvvmBaseViewModel {
    private MutableLiveData<String>ip;
    private MutableLiveData<String>port;
    private MutableLiveData<String>finishPage=new MutableLiveData<>();

    public MutableLiveData<String> getIp() {
        if(ip==null){
            //添加奥啊啊啊
            ip=new MutableLiveData<>();
            ip.setValue(SharedPrefUtil.getString(SharedPref.ip));
        }
        return ip;
    }
    public MutableLiveData<String> getPort() {
        if(port==null){
            port=new MutableLiveData<>();
            port.setValue(SharedPrefUtil.getString(SharedPref.port));
        }
        return port;
    }

    public MutableLiveData<String> getFinishPage() {
        return finishPage;
    }

    public void setIpPort(){
        if(port.getValue().equals("")||ip.getValue().equals("")){
            Tips.show("请输入正确的ip地址和端口号");
        }else{
            SharedPrefUtil.putString(SharedPref.ip,ip.getValue());
            SharedPrefUtil.putString(SharedPref.port,port.getValue());
            //通知关闭页面
            finishPage.setValue("1");
        }
    }
}
