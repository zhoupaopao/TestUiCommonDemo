package com.example.module_login.http;


import com.example.lib_resource.bean.TokenBean;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * <p>
 * 注意：如果方法的泛型指定的类不是ResponseBody,retrofit会将返回的string用json转换器自动转换该类的一个对象,转换不成功就报错
 * 如果不需要Gson转换,那么就指定泛型为ResponseBody,只能是ResponseBody,子类都不行.
 * </p>
 *
 * @author 张华洋 2016/12/5 15:22
 * @version V1.0.0
 * @name HttpParams
 */
public interface ApiService {

    //1.1，	获取TOKEN，采用OAUTH2协议

    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "username: web",
            "password: ",
    })
    @POST("oauth/token?grant_type=password&scope=read")
    Observable<JsonObject> token(@Body RequestBody requestBody);

    @POST("upms/oauth/token")
    Call<TokenBean> token1(@Body RequestBody requestBody);
}
