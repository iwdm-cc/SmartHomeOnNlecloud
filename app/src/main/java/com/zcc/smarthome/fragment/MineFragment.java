package com.zcc.smarthome.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.zcc.smarthome.R;


public class MineFragment extends BaseFragment {

    private Toolbar toolbarl;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(), toolbarl);
    }

    @Override
    protected void initView(View view) {
        toolbarl = view.findViewById(R.id.toolbar);

    }


}