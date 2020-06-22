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


    /**
     * projects/{projectid}/sensors
     */

    public void getMyNewsList(String projectid, Callback callback) {
        if (WelcomeActivity.token == null) {
            return;
        }

        Request requestPost = new Request.Builder()
                .addHeader("AccessToken", WelcomeActivity.token)
                .url(String.format("http://api.nlecloud.com/projects/%s/sensors", projectid))
                .get()
                .build();
        client.newCall(requestPost).enqueue(callback);
    }

    /**
     * 发送命令/控制设备
     */

    public void cmds(String deviceId, String apiTag, String cmd, Callback callback) {

        RequestBody requestBodyPost = new FormBody.Builder()
                .add("", cmd)//要获取的新闻频道
                .build();

        Request requestPost = new Request.Builder()
                .addHeader("AccessToken", WelcomeActivity.token)
                .url(String.format("http://api.nlecloud.com/Cmds?deviceId=%s&apiTag=%s", deviceId, apiTag))
                .post(requestBodyPost)
                .build();
        client.newCall(requestPost).enqueue(callback);
    }

    /**
     * 登录
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
    public void getStrategys(String ProjectID, String DeviceID, Callback callback) {

        Request requestPost = new Request.Builder()
                .addHeader("AccessToken", WelcomeActivity.token)
                .url(String.format("http://api.nlecloud.com/Strategys?ProjectID=%s&DeviceID=%s", ProjectID, DeviceID))
                .get()
                .build();
        client.newCall(requestPost).enqueue(callback);
    }

    /**
     * 获取天气信息
     */

    public void getWeatherinfo(Callback callback) {

        RequestBody requestBody = new FormBody.Builder()
                .add("city", "江津")
                .add("appkey", "d1018de47316f3f32c703aab248c7938")
                .build();
        Request requestPost = new Request.Builder()
                .url("https://way.jd.com/jisuapi/weather")
                .post(requestBody)
                .build();
        client.newCall(requestPost).enqueue(callback);
    }

}
