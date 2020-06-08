package com.zcc.smarthome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.text.SimpleDateFormat;


public class Application extends android.app.Application {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setTimeFormat(new SimpleDateFormat("更新于 %s"));
            }
        });
    }

    private Context mContext;


    public Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        // 上下文
        mContext = getApplicationContext();
        //bomb初始化
//        Bmob.initialize(this, Constant.BMOB_APPLICATION_ID);


    }


}
