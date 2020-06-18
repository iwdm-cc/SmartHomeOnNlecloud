package com.zcc.smarthome.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.zcc.smarthome.MainActivity;
import com.zcc.smarthome.R;
import com.zcc.smarthome.constant.Constant;
import com.zcc.smarthome.utils.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WelcomeActivity extends AppCompatActivity {
    public static String token = null;
    public static String temp = "这是一  dd 题";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
        startMain();
        initView();

    }

    private void startMain() {

        OkHttpUtils.getInstance().Login(Constant.Account, Constant.Password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(WelcomeActivity.this, "网络错误！", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    JSONObject jsonObject = JSONObject.parseObject(response.body().string());
                    token = jsonObject.getJSONObject("ResultObj").getString("AccessToken");
                }
            }
        });
        new OkHttpUtils().getWeatherinfo(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    temp = response.body().string();

                }
            }
        });

    }

    private void initView() {

        //动画设置
        TextView all = findViewById(R.id.all);
        ValueAnimator animator = ObjectAnimator.ofFloat(all, "translationY", 0.0f, 100.0f);
        animator.setDuration(2000);
        animator.setInterpolator(new BounceInterpolator());

        ValueAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(all, "scaleX", 0f, 1.5f);
        mAnimatorScaleX.setDuration(2000);

        ValueAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(all, "scaleY", 0f, 1.5f);
        mAnimatorScaleY.setDuration(2000);

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(2000);
        animSet.playTogether(animator, mAnimatorScaleX, mAnimatorScaleY);
        animSet.start();
    }

}
