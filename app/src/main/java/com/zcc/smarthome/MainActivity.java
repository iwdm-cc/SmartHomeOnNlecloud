package com.zcc.smarthome;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.zcc.smarthome.activity.BaseActivity;
import com.zcc.smarthome.adapter.MainViewPagerAdapter;
import com.zcc.smarthome.fragment.DevicesFragment;
import com.zcc.smarthome.fragment.HomeFragment;
import com.zcc.smarthome.fragment.MineFragment;
import com.zcc.smarthome.fragment.ScenceFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ViewPager mViewPager;

    public long exitTime = 0;

    private List<android.support.v4.app.Fragment> fragmentList;

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置为黑色的状态栏
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .init();

        if (getIntent().getBooleanExtra("isLastVersion", false)) {
            //更新APP地逻辑
        }
        bindViews();
    }

    //底部导航栏的 逻辑
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                iconSeletor(0);
                return true;
            case R.id.navigation_dashboard:
                iconSeletor(1);
                return true;
            case R.id.navigation_notifications:
                iconSeletor(2);
                return true;
            case R.id.navigationtions:
                iconSeletor(3);
                return true;
        }
        return false;
    };

    private void bindViews() {
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager = findViewById(R.id.mViewPager);
        fragmentList = new ArrayList<>();

        fragmentList.add(new HomeFragment());
        fragmentList.add(new DevicesFragment());
        fragmentList.add(new ScenceFragment());
        fragmentList.add(new MineFragment());
        MainViewPagerAdapter mAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mListener);
        //默认是显示第一个
        iconSeletor(0);
    }

    private void iconSeletor(int position) {
        mViewPager.setCurrentItem(position, false);
    }


    private ViewPager.OnPageChangeListener mListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    navigation.setSelectedItemId(R.id.navigation_home);
                    break;
                case 1:
                    navigation.setSelectedItemId(R.id.navigation_dashboard);
                    break;
                case 2:
                    navigation.setSelectedItemId(R.id.navigation_notifications);
                    break;
                case 3:
                    navigation.setSelectedItemId(R.id.navigationtions);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
