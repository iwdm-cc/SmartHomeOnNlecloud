package com.zcc.smarthome.utils;

import android.util.Log;

import com.zcc.smarthome.activity.WelcomeActivity;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class OkHttpUtils {

    //联网工具类对象，主要是联网进行一些操作
    public static OkHttpUtils instance;

    //1 创建一个OkHttp对象
    private static OkHttpClient client;

    public OkHttpUtils() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        Log.d("==w", "verify: 设置默认验证通过");
                        return true;
                    }
                })
                .build();
    }

    //网络请求的单例模式 一般在有3个线程以上时应用...
    public static OkHttpUtils getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtils.class) {
                if (instance == null) {
                    instance = new OkHttpUtils();
                }
            }
        }
        return instance;
    }

    //通用的根据url发送json数据 get请求
    public void sendCommon(String jsonUrl, Callback callback) {
        //2 创建一个网络请求
        Request request = new Request.Builder()
                //json数据的网址
                .url(jsonUrl)
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }

    /**
     * projects/{projectid}/sensors
     */

    public void getMyNewsList(String projectid, Callback callback) {

        Request requestPost = new Request.Builder()
                .addHeader("AccessToken", WelcomeActivity.token)
                .url(String.format("http://api.nlecloud.com/projects/%s/sensors", projectid))
                .get()
                .build();
        client.newCall(requestPost).enqueue(callback);
    }

    /**
     * 获取关键字的新闻列表
     */

    public void getKeyList(String key, String appkey, Callback callback) {

        RequestBody requestBodyPost = new FormBody.Builder()
                .add("keyword", key)//要获取的新闻频道
                .add("appkey", appkey)//appkey
                .build();

        Request requestPost = new Request.Builder()
                .url("http://api.jisuapi.com/news/search")
                .post(requestBodyPost)
                .build();
        client.newCall(requestPost).enqueue(callback);
    }

    /**
     * 启用/禁用策略
     */
    public void Login(String Account, String Password, Callback callback) {

        RequestBody requestBodyPost = new FormBody.Builder()
                .add("Account", Account)
                .add("Password", Password)
                .build();

        Request requestPost = new Request.Builder()
                .url("http://api.nlecloud.com/Users/Login")
                .post(requestBodyPost)
                .build();
        client.newCall(requestPost).enqueue(callback);

    }

    /**
     * 启用/禁用策略
     */
    public void setEnable(String ID, String enable, Callback callback) {

        RequestBody requestBodyPost = new FormBody.Builder()
                .add("strategyID", ID)
                .build();

        Request requestPost = new Request.Builder()
                .addHeader("AccessToken", WelcomeActivity.token)
                .url(String.format("http://api.nlecloud.com/Strategys/Enable/%s?strategyID=%s&enable=%s", ID, ID, enable))
                .post(requestBodyPost)
                .build();
        client.newCall(requestPost).enqueue(callback);

    }

    /**
     * 查询策略
     */
    public void getStrategys(String key, Callback callback) {

        Request requestPost = new Request.Builder()
                .addHeader("AccessToken", WelcomeActivity.token)
                .url("http://api.nlecloud.com/Strategys?ProjectID=39626&DeviceID=50733")
                .get()
                .build();
        client.newCall(requestPost).enqueue(callback);
    }

}
