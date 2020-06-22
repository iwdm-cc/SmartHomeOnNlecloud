package com.zcc.smarthome.fragment;

import android.content.Intent;
import android.view.View;

import com.zcc.smarthome.R;
import com.zcc.smarthome.activity.AlterUserInfActivity;


public class MineFragment extends BaseFragment implements View.OnClickListener {


    private com.lqr.optionitemview.OptionItemView mOVUserInf;

    private com.lqr.optionitemview.OptionItemView mOVDayHappy;
    private com.lqr.optionitemview.OptionItemView mOVAbout;
    private com.lqr.optionitemview.OptionItemView OVVegetable;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {


        mOVUserInf = view.findViewById(R.id.OVUserInf);
        mOVUserInf.setOnClickListener(this);

        mOVDayHappy = view.findViewById(R.id.OVDayHappy);
        mOVDayHappy.setOnClickListener(this);
        mOVAbout = view.findViewById(R.id.OVAbout);
        OVVegetable = view.findViewById(R.id.OVWeather);
        mOVAbout.setOnClickListener(this);
        OVVegetable.setOnClickListener(this);


    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.OVUserInf:
                startActivity(new Intent(getActivity(), AlterUserInfActivity.class));
                break;

            case R.id.OVDayHappy:
                break;
            //天气预报
            case R.id.OVWeather:
                break;
            case R.id.OVAbout:
                break;
        }
    }


}