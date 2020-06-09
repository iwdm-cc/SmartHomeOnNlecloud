package com.zcc.smarthome.utils;

import android.util.Log;

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
     * 获取指定类型的新闻列表
     */

    public void getMyNewsList(String channel, int start, int num, String appkey, Callback callback) {

        RequestBody requestBodyPost = new FormBody.Builder()
                .add("channel", channel)//要获取的新闻频道
                .add("num", Integer.toString(num))//每次请求的数目
                .add("start", Integer.toString(start))//开始条目
                .add("appkey", appkey)//appkey
                .build();

        Request requestPost = new Request.Builder()
                .url("http://api.jisuapi.com/news/get")
                .post(requestBodyPost)
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
    public void setEnable(String ID, String enable, Callback callback) {

        RequestBody requestBodyPost = new FormBody.Builder()
                .add("strategyID", ID)
                .build();

        Request requestPost = new Request.Builder()
                .addHeader("AccessToken", "6EE4C265B6452601B64D990064C52E4EF3B8A61ECE75A4F7D372824529FE77EB8B81A027DF856A72AA6632AF33D219946536061180FB54F7411894ED89FD944A8387239045A241022C79AA97A09DF3182CDA6D766AC99252E1533B4F468C2FD08D8DF2A1B9948B5C3C3C3E5599AEA047C3848A3696094682635BB1460C8146CC829D2F9DE07C1556F8704C83146ABFE8B1465089A4D1C77E00754AFD61B0959C211D9E8594B4B00C554D7A38ECDADEA88F1C07DA4F86D655463B6E19F9607E9082D2CCDD141540571BAD6D6163E72A08696948C95486062DE7101FE7E0F18821")
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
                .addHeader("AccessToken", "6EE4C265B6452601B64D990064C52E4EF3B8A61ECE75A4F7D372824529FE77EB8B81A027DF856A72AA6632AF33D219946536061180FB54F7411894ED89FD944A8387239045A241022C79AA97A09DF3182CDA6D766AC99252E1533B4F468C2FD08D8DF2A1B9948B5C3C3C3E5599AEA047C3848A3696094682635BB1460C8146CC829D2F9DE07C1556F8704C83146ABFE8B1465089A4D1C77E00754AFD61B0959C211D9E8594B4B00C554D7A38ECDADEA88F1C07DA4F86D655463B6E19F9607E9082D2CCDD141540571BAD6D6163E72A08696948C95486062DE7101FE7E0F18821")
                .url("http://api.nlecloud.com/Strategys?ProjectID=39626&DeviceID=50733")
                .get()
                .build();
        client.newCall(requestPost).enqueue(callback);
    }

}
