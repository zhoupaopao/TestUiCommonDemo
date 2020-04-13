package com.example.module_personal.http;


import com.example.lib_resource.bean.TokenBean;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    //上传头像
    @Multipart
    @POST("upms/patient/upload")
    Observable<JsonObject> upLoadImage(@Part MultipartBody.Part requestBody);
}
