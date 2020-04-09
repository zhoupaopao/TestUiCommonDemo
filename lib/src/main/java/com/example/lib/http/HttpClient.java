package com.example.lib.http;


import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;


import com.example.lib.R;
import com.example.lib.base.BaseApplication;
import com.example.lib.utils.DataParseUtil;
import com.example.lib.utils.NetworkUtils;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.StringUtils;
import com.example.lib.utils.ToastUtils;
import com.example.lib.utils.Utils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2016/12/5 14:09
 * @version V1.0.0
 * @name HttpClient
 */
public class HttpClient {

    /*The certificate's password*/
    private static final String STORE_PASS = "6666666";
    private static final String STORE_ALIAS = "666666";
    /*用户设置的BASE_URL*/
    private static String BASE_URL = "";
    /*本地使用的baseUrl*/
    private String baseUrl = "";
    private static OkHttpClient okHttpClient;
    private Builder mBuilder;
    private Retrofit retrofit;
    private Call<ResponseBody> mCall;
    private static final Map<String, Call> CALL_MAP = new HashMap<>();


    static {
        initOkHttpClient();
    }

    private static void initOkHttpClient() {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (okHttpClient == null) {
            synchronized (HttpClient.class) {
                if (okHttpClient == null) {
                    //设置Http缓存
                    okHttpClient = new OkHttpClient.Builder()
                            .followRedirects(false).followSslRedirects(false)
//                            .cache(new Cache(new File(getExternalStorePath(), "HttpCache"), 1024 * 1024 * 10))
//                            .addInterceptor(new CommonInterceptor())
//                            .addNetworkInterceptor(new CacheInterceptor())
//                            .addNetworkInterceptor(new StethoInterceptor())
                            .addInterceptor(new AuthorizationInterceptor())
                            .addInterceptor(new TokenInterceptor())
//                            .addNetworkInterceptor(logging)
                            .authenticator((route, response) -> {
                                String credential = Credentials.basic("web", "");

//                                if (response.code() == 401) {//返回为token失效
//                                    Log.d("RetrofitHelper", "401401401401401401");
////                                    BaseApplication.getInstance().doReLogin();
////
//                                    Request newRequest = response.request().newBuilder()
//                                            .header("Authorization", credential)
//                                            .addHeader("Connection", "close")
//                                            .build();
////                                    Request newRequest = response.newBuilder()
////                                            .header("Authorization", SharedPrefUtil.getHeaderToken())
////                                            .build();
////
//                                    return newRequest;
//                                }

                                if (credential.equals(response.request().header("Authorization"))) {
                                    return null; // If we already failed with these credentials, don't retry.
                                }
                                return response.request().newBuilder()
                                        .header("Authorization", credential)
                                        .addHeader("Connection", "close")
                                        .build();
//                                return null;
                            })
                            .retryOnConnectionFailure(false)

                            .connectTimeout(16, TimeUnit.SECONDS)
                            .writeTimeout(8, TimeUnit.SECONDS)
                            .readTimeout(8, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }
    /**
     * 拦截器使用，修改Authorization
     */
    private static class TokenInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            okhttp3.Response response = chain.proceed(request);

            if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
                //同步请求方式，获取最新的Token
                String newSession = BaseApplication.refresh_Token();
                //使用新的Token，创建新的请求
                Request newRequest = chain.request()
                        .newBuilder()
                        .removeHeader("Authorization")
                        .header("Authorization", newSession)
                        .build();
                //重新请求
                return chain.proceed(newRequest);
            }
            return response;
        }
    }
    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private static boolean isTokenExpired(okhttp3.Response response) {
        if (response.code() == 403) {
            return true;
        }
        return false;
    }

//    private String getNewToken() {
//        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
//        IndexService service = IndexService.Builder.getServer();
//        Call<BaseObjResult<UserBean>> call = service.getToke(
//                UserInfo.getInstance().getPhone(),
//                UserInfo.getInstance().getPwd(),
//                0);
//
//        //要用retrofit的同步方式
//        BaseObjResult<UserBean> newToken = null;
//        try {
//            newToken = call.execute().body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return newToken.getResult().getPHPSESSID();
//    }


    /**
     * 拦截器使用，修改Authorization
     */
    private static class AuthorizationInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent;
            List<String> paths= originalRequest.url().pathSegments();
            StringBuffer str_path=new StringBuffer();
            for(String item:paths){
                str_path.append(item).append("/");
            }
            if(str_path.toString().equals("upms/oauth/token/")){
                 requestWithUserAgent = originalRequest.newBuilder()
                        .removeHeader("Authorization")
                        .header("Authorization", Credentials.basic("web", ""))
                        .build();
            }else{
                 requestWithUserAgent = originalRequest.newBuilder()
                        .removeHeader("Authorization")
                        .header("Authorization", SharedPrefUtil.getHeaderToken())
                        .build();
            }
//            Log.i("intercept: ", str_path.toString());

            return chain.proceed(requestWithUserAgent);
        }
    }

    /**
     * 获取HttpClient的单例
     *
     * @return HttpClient的唯一对象
     */
    private static HttpClient getIns() {
        return HttpClientHolder.sInstance;
    }

    /**
     * 单例模式中的静态内部类写法
     */
    private static class HttpClientHolder {
        private static final HttpClient sInstance = new HttpClient();
    }

    private HttpClient() {
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(Utils.getContext()));
        //HttpsUtil.SSLParams sslParams = HttpsUtil.getSslSocketFactory(Utils.getContext(), R.raw.cer,STORE_PASS , STORE_ALIAS);
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                //.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                // .hostnameVerifier(HttpsUtil.getHostnameVerifier())
//                .addInterceptor(new LoggerInterceptor(null, true))///先不要
//                .authenticator((route, response) -> {
//                    String credential = Credentials.basic("web", "");
//                    if (credential.equals(response.request().header("Authorization"))) {
//                        return null; // If we already failed with these credentials, don't retry.
//                    }
//                    return response.request().newBuilder()
//                            .header("Authorization", credential)
//                            .addHeader("Connection", "close")
//                            .build();
////                                return null;
//                })
                .cookieJar(cookieJar)
                .build();
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    private void setBuilder(Builder builder) {
        this.mBuilder = builder;
    }

    /**
     * 获取的Retrofit的实例，
     * 引起Retrofit变化的因素只有静态变量BASE_URL的改变。
     */
    private void getRetrofit() {
        if (!BASE_URL.equals(baseUrl) || retrofit == null) {
            baseUrl = BASE_URL;
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .build();
        }
    }
    private void getRetrofitClass() {
        if (!BASE_URL.equals(baseUrl) || retrofit == null) {
            baseUrl = BASE_URL;
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();
        }
    }

    public void post(final OnResultListener onResultListener) {
        Builder builder = mBuilder;
        mCall = retrofit.create(ApiService.class)
                .executePost(builder.url, builder.params);
        putCall(builder, mCall);
        request(builder, onResultListener);
    }

    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    public static <T> T createApi(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Server.online)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                //注册自定义的工厂类
//                .addConverterFactory(ResponseConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
    public void get(final OnResultListener onResultListener) {
        Builder builder = mBuilder;
        if (!builder.params.isEmpty()) {
            String value = "";
            for (Map.Entry<String, String> entry : builder.params.entrySet()) {
                String mapKey = entry.getKey();
                String mapValue = entry.getValue();
                String span = value.equals("") ? "" : "&";
                String part = StringUtils.buffer(span, mapKey, "=", mapValue);
                value = StringUtils.buffer(value, part);
            }
            builder.url(StringUtils.buffer(builder.url, "?", value));
        }
        mCall = retrofit.create(ApiService.class).executeGet(builder.url);
        putCall(builder, mCall);
        request(builder, onResultListener);
    }


    private void request(final Builder builder, final OnResultListener onResultListener) {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showLongToastSafe(R.string.current_internet_invalid);
            onResultListener.onFailure(Utils.getString(R.string.current_internet_invalid));
            return;
        }
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (200 == response.code()) {
                    try {
                        String result = response.body().string();
                        parseData(result, builder.clazz, builder.bodyType, onResultListener);
                    } catch (IOException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
                if (!response.isSuccessful() || 200 != response.code()) {
                    onResultListener.onError(response.code(), response.message());
                }
                if (null != builder.tag) {
                    removeCall(builder.url);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                onResultListener.onFailure(t.getMessage());
                if (null != builder.tag) {
                    removeCall(builder.url);
                }
            }

        });
    }


    /**
     * 添加某个请求
     */
    private synchronized void putCall(Builder builder, Call call) {
        if (builder.tag == null)
            return;
        synchronized (CALL_MAP) {
            CALL_MAP.put(builder.tag.toString() + builder.url, call);
        }
    }


    /**
     * 取消某个界面都所有请求，或者是取消某个tag的所有请求;
     * 如果要取消某个tag单独请求，tag需要传入tag+url
     *
     * @param tag 请求标签
     */
    public synchronized void cancel(Object tag) {
        if (tag == null)
            return;
        List<String> list = new ArrayList<>();
        synchronized (CALL_MAP) {
            for (String key : CALL_MAP.keySet()) {
                if (key.startsWith(tag.toString())) {
                    CALL_MAP.get(key).cancel();
                    list.add(key);
                }
            }
        }
        for (String s : list) {
            removeCall(s);
        }

    }

    /**
     * 移除某个请求
     *
     * @param url 添加的url
     */
    private synchronized void removeCall(String url) {
        synchronized (CALL_MAP) {
            for (String key : CALL_MAP.keySet()) {
                if (key.contains(url)) {
                    url = key;
                    break;
                }
            }
            CALL_MAP.remove(url);
        }
    }

    /**
     * Build a new HttpClient.
     * url is required before calling. All other methods are optional.
     */
    public static final class Builder {
        private String builderBaseUrl = "";
        private String url;
        private Object tag;
        private Map<String, String> params = new HashMap<>();
        /*返回数据的类型,默认是string类型*/
        @DataType.Type
        private int bodyType = DataType.STRING;
        /*解析类*/
        private Class clazz;

        public Builder() {
        }

        /**
         * 请求地址的baseUrl，最后会被赋值给HttpClient的静态变量BASE_URL；
         *
         * @param baseUrl 请求地址的baseUrl
         */
        public Builder baseUrl(String baseUrl) {
            this.builderBaseUrl = baseUrl;
            return this;
        }

        /**
         * 除baseUrl以外的部分，
         * 例如："mobile/login"
         *
         * @param url path路径
         */
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * 给当前网络请求添加标签，用于取消这个网络请求
         *
         * @param tag 标签
         */
        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        /**
         * 添加请求参数
         *
         * @param key   键
         * @param value 值
         */
        public Builder params(String key, String value) {
            this.params.put(key, value);
            return this;
        }

        /**
         * 响应体类型设置,如果要响应体类型为STRING，请不要使用这个方法
         *
         * @param bodyType 响应体类型，分别:STRING，JSON_OBJECT,JSON_ARRAY,XML
         * @param clazz    指定的解析类
         * @param <T>      解析类
         */
        public <T> Builder bodyType(@DataType.Type int bodyType, @NonNull Class<T> clazz) {
            this.bodyType = bodyType;
            this.clazz = clazz;
            return this;
        }

        public HttpClient build() {
            if (!TextUtils.isEmpty(builderBaseUrl)) {
                BASE_URL = builderBaseUrl;
            }
            HttpClient client = HttpClient.getIns();
            client.getRetrofit();
            client.setBuilder(this);
            return client;
        }
        public HttpClient createApi() {
            if (!TextUtils.isEmpty(builderBaseUrl)) {
                BASE_URL = builderBaseUrl;
            }
            HttpClient client = HttpClient.getIns();
            client.getRetrofit();
            client.setBuilder(this);
            return client;
        }
    }

    /**
     * 数据解析方法
     *
     * @param data             要解析的数据
     * @param clazz            解析类
     * @param bodyType         解析数据类型
     * @param onResultListener 回调方数据接口
     */
    @SuppressWarnings("unchecked")
    private void parseData(String data, Class clazz, @DataType.Type int bodyType, OnResultListener onResultListener) {
        switch (bodyType) {
            case DataType.STRING:
                onResultListener.onSuccess(data);
                break;
            case DataType.JSON_OBJECT:
                onResultListener.onSuccess(DataParseUtil.parseObject(data, clazz));
                break;
            case DataType.JSON_ARRAY:
                onResultListener.onSuccess(DataParseUtil.parseToArrayList(data, clazz));
                break;
            case DataType.XML:
                onResultListener.onSuccess(DataParseUtil.parseXml(data, clazz));
                break;
            default:
                Log.e("http parse tip:", "if you want return object, please use bodyType() set data type");
                break;
        }
    }

}
