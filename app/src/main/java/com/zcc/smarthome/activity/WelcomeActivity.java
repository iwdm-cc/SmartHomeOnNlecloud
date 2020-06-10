package com.zcc.smarthome.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.BounceInterpolator;
import android.widget.RelativeLayout;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.zcc.smarthome.MainActivity;
import com.zcc.smarthome.R;
import com.zcc.smarthome.utils.UtilTools;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        startMain();
    }

    private void startMain() {
        Intent intent = new Intent();
        intent.setClass(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        //字体设置
        Shimmer shimmer = new Shimmer();
        ShimmerTextView shimmer_login = findViewById(R.id.shimmer_login);
        UtilTools.setFont(this, shimmer_login);
        shimmer.start(shimmer_login);

        //动画设置
        RelativeLayout all = findViewById(R.id.all);
        ValueAnimator animator = ObjectAnimator.ofFloat(all, "translationY", 0.0f, 100.0f);
        animator.setDuration(3000);
        animator.setInterpolator(new BounceInterpolator());

        ValueAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(all, "scaleX", 0f, 1.5f);
        mAnimatorScaleX.setDuration(3000);

        ValueAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(all, "scaleY", 0f, 1.5f);
        mAnimatorScaleY.setDuration(3000);

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(3000);
        animSet.playTogether(animator, mAnimatorScaleX, mAnimatorScaleY);
        animSet.start();
    }

}
