package com.zcc.smarthome.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.squareup.picasso.Picasso;
import com.zcc.smarthome.R;
import com.zcc.smarthome.view.CircleTransform;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AboutAuthorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_author);
        initView();
    }

    private void initView() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImmersionBar.setTitleBar(this, toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView ivMe = findViewById(R.id.ivMe);
        Picasso.with(this)
                .load(R.drawable.ic_about_me)
                .transform(new CircleTransform())
                .into(ivMe);
        ivMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(AboutAuthorActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("我很帅呢亲~")
                        .show();
            }
        });
    }
}
