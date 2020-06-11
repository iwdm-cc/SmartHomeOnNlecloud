package com.zcc.smarthome.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.gyf.barlibrary.ImmersionBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zcc.smarthome.R;
import com.zcc.smarthome.utils.L;
import com.zcc.smarthome.utils.SharePreUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class DevicesFragment extends BaseFragment implements OnRefreshLoadmoreListener {


    private Toolbar toolbarl;

    private RecyclerView rVMyDevices;
    private DividerItemDecoration dividerItemDecoration;


    private boolean isFirstBind = false;


    private RefreshLayout mRefreshLayout;
    private ClassicsHeader mClassicsHeader;
    private Drawable mDrawableProgress;
    private static boolean isFirstEnter = true;



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 105) {
                if (tipDialog != null) {
                    tipDialog.dismiss();
                }
            }
        }
    };
    private String uid;
    private String token;
    private QMUITipDialog tipDialog;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(), toolbarl);
        toolbarl.inflateMenu(R.menu.menu_devices_add_login_out);
        toolbarl.setOverflowIcon(getActivity().getResources().getDrawable(R.drawable.ic_toolbar_devices_add));
        toolbarl.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    //手动添加设备
                    case R.id.menu_id_handAddDevices:
                        break;
                    default:
                        tipDialog = new QMUITipDialog.Builder(getActivity())
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                                .setTipWord("sorry，暂未加入此功能！")
                                .create();
                        tipDialog.show();
                        mHandler.sendEmptyMessageDelayed(105, 1500);
                        break;
                }
                return false;
            }
        });

    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_devices;
    }

    @Override
    protected void initView(View view) {
        toolbarl = view.findViewById(R.id.toolbar);
        rVMyDevices = view.findViewById(R.id.recyclerView);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnRefreshLoadmoreListener(this);
        int deta = new Random().nextInt(7 * 24 * 60 * 60 * 1000);

        mClassicsHeader = (ClassicsHeader) mRefreshLayout.getRefreshHeader();
        mClassicsHeader.setLastUpdateTime(new Date(System.currentTimeMillis() - deta));
        mClassicsHeader.setTimeFormat(new SimpleDateFormat("更新于 MM-dd HH:mm", Locale.CHINA));
        mClassicsHeader.setSpinnerStyle(SpinnerStyle.Translate);
        mDrawableProgress = mClassicsHeader.getProgressView().getDrawable();
        if (mDrawableProgress instanceof LayerDrawable) {
            mDrawableProgress = ((LayerDrawable) mDrawableProgress).getDrawable(0);
        }


    }

    @Override
    protected void initData() {

        uid = SharePreUtils.getString(getActivity(), "_uid", null);
        token = SharePreUtils.getString(getActivity(), "_token", null);

        dividerItemDecoration = new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        adapter = new DevicesListAdapter(getActivity(), deviceslist);/**/
        rVMyDevices.setLayoutManager(linearLayoutManager);
        rVMyDevices.addItemDecoration(dividerItemDecoration);


    }


    //长按item
    private void showDevicesInfDialog() {

        final String[] stringItems = {"解绑此设备", "重命名设备", "查看设备信息"};
        final ActionSheetDialog sheetDialog = new ActionSheetDialog(getActivity(), stringItems, null);
        sheetDialog.isTitleShow(false).show();
        sheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }
                sheetDialog.dismiss();
            }
        });
    }

    //下拉刷新回调
    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        L.e("设备列表下拉刷新回调");

        tipDialog = new QMUITipDialog.Builder(getActivity())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在刷新...")
                .create();
        tipDialog.show();

        if (false) {
            mRefreshLayout.finishRefresh();


            tipDialog.dismiss();
            tipDialog = new QMUITipDialog.Builder(getActivity())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                    .setTipWord("刷新成功")
                    .create();
            tipDialog.show();
            mHandler.sendEmptyMessageDelayed(105, 1700);
        } else {
            mRefreshLayout.finishRefresh();


            tipDialog.dismiss();
            tipDialog = new QMUITipDialog.Builder(getActivity())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                    .setTipWord("暂无设备")
                    .create();
            tipDialog.show();
            mHandler.sendEmptyMessageDelayed(105, 1700);
        }

    }





    @Override
    public void onLoadmore(RefreshLayout refreshLayout) {
        mRefreshLayout.finishLoadmore();
    }


}


