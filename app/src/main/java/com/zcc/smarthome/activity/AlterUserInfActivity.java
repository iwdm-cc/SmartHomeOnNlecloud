package com.zcc.smarthome.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.zcc.smarthome.R;


public class AlterUserInfActivity extends BaseActivity {

    //用户名
    private EditText register_et_name;
    //用户邮箱
    private EditText register_et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_user_inf);
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
    }


}
