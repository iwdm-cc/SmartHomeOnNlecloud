package com.zcc.smarthome.fragment;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.qmuiteam.qmui.layout.QMUIRelativeLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.zcc.smarthome.R;


public class MineFragment extends BaseFragment {


    private QMUICommonListItemView item_1;
    private QMUICommonListItemView item_2;
    private QMUICommonListItemView item_3;
    private QMUICommonListItemView item_4;
    private QMUIRelativeLayout mini_head;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void initView(View view) {
        mini_head = view.findViewById(R.id.mini_head);
        mini_head.setRadius(40);

        item_1 = view.findViewById(R.id.item_1);

        item_1.setText("个人资料");
        item_1.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.peron, null));

        item_2 = view.findViewById(R.id.item_2);
        item_2.setText("我的消息");
        item_2.showNewTip(true);
        item_2.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.infopng, null));

        item_3 = view.findViewById(R.id.item_3);
        item_3.setText("会员中心");
        item_3.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.vip, null));

        item_4 = view.findViewById(R.id.item_4);
        item_4.setText("设置");
        item_4.showNewTip(true);
        item_4.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.setting, null));
    }


}