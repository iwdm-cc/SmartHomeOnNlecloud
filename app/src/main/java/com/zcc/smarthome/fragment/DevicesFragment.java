package com.zcc.smarthome.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.gyf.barlibrary.ImmersionBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zcc.smarthome.R;
import com.zcc.smarthome.adapter.mRecyclerViewAdapter;
import com.zcc.smarthome.constant.Constant;
import com.zcc.smarthome.utils.OkHttpUtils;
import com.zcc.smarthome.utils.ToastUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class DevicesFragment extends BaseFragment {


    private Toolbar toolbarl;
    private RecyclerView recyclerView;
    private DividerItemDecoration dividerItemDecoration;
    private QMUITipDialog tipDialog;
    private RefreshLayout mRefreshLayout;
    private ClassicsHeader mClassicsHeader;
    private Drawable mDrawableProgress;
    JSONArray jsonArray;
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


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(), toolbarl);
        getMyNewsList();

    }

    private void getMyNewsList() {
        new OkHttpUtils().getMyNewsList(Constant.ProjectID, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    JSONObject jsonObject = JSONObject.parseObject(response.body().string());
                    jsonArray = jsonObject.getJSONArray("ResultObj");
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            datazc();
                        }
                    });
                }
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
        recyclerView = view.findViewById(R.id.recyclerView);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ToastUtils.showToast(getContext(), "上来刷新");
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                tipDialog = new QMUITipDialog.Builder(getActivity())
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("正在刷新...")
                        .create();
                tipDialog.show();
                getMyNewsList();

                mRefreshLayout.finishRefresh();

                tipDialog.dismiss();
                tipDialog = new QMUITipDialog.Builder(getActivity())
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                        .setTipWord("刷新成功")
                        .create();
                tipDialog.show();
                mHandler.sendEmptyMessageDelayed(105, 1700);

            }
        });
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

    }

    private void datazc() {
        dividerItemDecoration = new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerViewAdapter mScenceAdapter = new mRecyclerViewAdapter(jsonArray, getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(mScenceAdapter);
        mScenceAdapter.setOnItemClickListener(new mRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                showDevicesInfDialog();
            }
        });
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

}


