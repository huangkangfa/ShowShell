package com.sdk.okhttp;

import com.sdk.okhttp.callback.DownloadCallback;
import com.sdk.okhttp.cookie.SimpleCookieJar;
import com.sdk.okhttp.params.RequestParams;
import com.sdk.okhttp.ssl.HttpsUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by huangkangfa on 2017/12/15.
 */

public class OkHttpUtil {

    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");

    private static final int TIME_OUT = 30;  //超时时间   单位 秒

    private static OkHttpClient mOkHttpClient;

    static {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.followRedirects(true);
        /**
         * trust all the https point
         */
        okHttpClientBuilder.sslSocketFactory(HttpsUtils.getSslSocketFactory());
        mOkHttpClient = okHttpClientBuilder.build();
    }

    /**
     * 指定cilent信任指定证书
     *
     * @param certificates
     */
    public static void setCertificates(InputStream... certificates) {
        mOkHttpClient.newBuilder().sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null)).build();
    }

    /**
     * 指定client信任所有证书
     */
    public static void setCertificates() {
        mOkHttpClient.newBuilder().sslSocketFactory(HttpsUtils.getSslSocketFactory());
    }

    /**
     * get请求  异步
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    public static Call get(String url, RequestParams params, Callback callback){
        StringBuffer sb=new StringBuffer(url);
        if(params!=null){
            sb.append("?");
            for(String key:params.urlParams.keySet()){
                sb.append(key).append("=").append(params.urlParams.get(key)).append("&&");
            }
            int len=sb.length();
            sb.delete(len-2,len);
        }

        Request request = new Request.Builder().url(sb.toString()).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @param callback
     * @return
     */
    public static Call post(String url,RequestParams params,Callback callback){
        FormBody.Builder formBody = new FormBody.Builder();
        if(params!=null){
            for(String key:params.urlParams.keySet()){
                formBody.add(key, params.urlParams.get(key));
            }
        }
        Request request = new Request.Builder().url(url).post(formBody.build()).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }

    /**
     * 下载文件
     * @param url
     * @param callback
     * @return
     */
    public static Call downloadFile(String url,DownloadCallback callback){
        Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }

    /**
     * 文件上传
     * @param url
     * @param params
     * @param callback
     * @return
     */
    public static Call uploadFile(String url,RequestParams params,Callback callback){
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
                if (entry.getValue() instanceof File) {
                    requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(FILE_TYPE, (File) entry.getValue()));
                } else if (entry.getValue() instanceof String) {
                    requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(null, (String) entry.getValue()));
                }
            }
        }
        Call call = mOkHttpClient.newCall(new Request.Builder().url(url).post(requestBody.build()).build());
        call.enqueue(callback);
        return call;
    }
}









