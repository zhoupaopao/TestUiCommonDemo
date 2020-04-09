package com.example.lib.http;


import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
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

    @GET
    Call<ResponseBody> executeGet(@Url String url);

    /**
     * POST方式将以表单的方式传递键值对作为请求体发送到服务器
     * 其中@FormUrlEncoded 以表单的方式传递键值对
     * 其中 @Path：所有在网址中的参数（URL的问号前面）
     * 另外@FieldMap 用于POST请求，提交多个表单数据，@Field：用于POST请求，提交单个数据
     * 使用@url是为了防止URL被转义为https://10.33.31.200:8890/msp%2Fmobile%2Flogin%3
     */
    @FormUrlEncoded
    @POST
    Call<ResponseBody> executePost(@Url String url, @FieldMap Map<String, String> map);


    /**
     * 流式下载,不加这个注解的话,会整个文件字节数组全部加载进内存,可能导致oom
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String fileUrl, @HeaderMap Map<String, String> headers);


//    //1.1，	获取TOKEN，采用OAUTH2协议
//
//    @Headers({
//            "Content-Type: application/x-www-form-urlencoded",
//            "Authorization: Basic d2ViOg==",
//            "Type: Basic Auth",
//            "username: 13333333333",
//            "password: 123456",
//    })
//    @POST("oauth/token?grant_type=password&scope=read")
//    Observable<JsonObject> token(@Body RequestBody requestBody);

    @POST("upms/oauth/token")
    Observable<JsonObject> rfresh_token(@Body RequestBody requestBody);

    @POST("upms/oauth/token")
    Call<JsonObject> rrfresh_token(@Body RequestBody requestBody);

//    @GET("upms/dictionaryItems/{code}")
//    Observable<ArrayList<MainUnitBean>> Get_dictionaryItems(@Path("code") String code);
}
