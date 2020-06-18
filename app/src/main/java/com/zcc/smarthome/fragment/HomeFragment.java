package com.zcc.smarthome.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zcc.smarthome.R;
import com.zcc.smarthome.activity.WelcomeActivity;

import java.util.Arrays;

import cn.bingoogolapple.bgabanner.BGABanner;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private BGABanner mBanner;
    private TextView textView;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        mBanner = view.findViewById(R.id.banner_main_depth);
//        textView = view.findViewById(R.id.text);
    }

    @Override
    protected void initData() {
        String we = WelcomeActivity.temp;
//        JSONObject jsonObject = JSONObject.parseObject(we);
//        String weather = jsonObject.getJSONObject("result").getJSONObject("result").getString("weather");
//        textView.setText(weather);
        mBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                if (getActivity() != null) {
                    Glide.with(getActivity())
                            .load(model)
                            .placeholder(R.mipmap.holder)
                            .error(R.mipmap.holder)
                            .centerCrop()
                            .dontAnimate()
                            .into(itemView);
                }
            }
        });
        mBanner.setData(Arrays.asList("http://app.chinagtop.com/app/img/nvc/1.png", "http://app.chinagtop.com/app/img/nvc/2.png", "http://app.chinagtop.com/app/img/nvc/3.png"), Arrays.asList("新版厨具", "提示文字2", "提示文字3"));
    }


    @Override
    public void onClick(View v) {

    }
}
