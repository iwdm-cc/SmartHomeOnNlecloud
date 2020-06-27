package com.zcc.smarthome.fragment;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class DevicesFragment extends BaseFragment {

    private ViewMode mViewModel;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private QMUITipDialog tipDialog;
    private RefreshLayout mRefreshLayout;
    private JSONArray jsonArray;


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
        ImmersionBar.setTitleBar(getActivity(), toolbar);
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
                    JSONObject jsonObject = JSONObject.parseObject(Objects.requireNonNull(response.body()).string());
                    jsonArray = jsonObject.getJSONArray("ResultObj");
                    JSONArray mjsonArray = new JSONArray();
                    JSONArray mjsonArray1 = new JSONArray();
                    for (Object aJsonArray : jsonArray) {
                        JSONObject jsonObject1 = (JSONObject) aJsonArray;
                        if (jsonObject1.getString("Groups").equals("2")) {
                            mjsonArray.add(jsonObject1);
                        } else {
                            mjsonArray1.add(jsonObject1);
                        }
                    }

                    mHandler.post(() -> {
                        mViewModel.getJsonArrayMutableLiveData().setValue(mjsonArray);
                        mViewModel.gethomejsonArrayMutableLiveData().setValue(mjsonArray1);
                    });
                }
            }
        });

    }


    @Override
    protected int setLayoutId() {
        mViewModel = ViewModelProviders.of(requireActivity()).get(ViewMode.class);
        return R.layout.fragment_devices;
    }

    @Override
    protected void initView(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        recyclerView = view.findViewById(R.id.recyclerView);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Log.i("once", "xxx");
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

        ClassicsHeader mClassicsHeader = (ClassicsHeader) mRefreshLayout.getRefreshHeader();
        mClassicsHeader.setLastUpdateTime(new Date(System.currentTimeMillis() - deta));
        mClassicsHeader.setTimeFormat(new SimpleDateFormat("更新于 MM-dd HH:mm", Locale.CHINA));
        mClassicsHeader.setSpinnerStyle(SpinnerStyle.Translate);


    }

    @Override
    protected void initData() {

        final Observer<JSONArray> jsonArrayObserver = this::datazc;

        mViewModel.getJsonArrayMutableLiveData().observe(getViewLifecycleOwner(), jsonArrayObserver);


    }

    private void datazc(JSONArray objects) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewAdapter mScenceAdapter = new mRecyclerViewAdapter(objects, getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(mScenceAdapter);
        mScenceAdapter.setOnItemClickListener(position -> showDevicesInfDialog());
        mScenceAdapter.setOnLaunchClickListener(position -> {
            JSONObject jsonObject = objects.getJSONObject(position);
            new OkHttpUtils().cmds(Constant.DeviceID, jsonObject.getString("ApiTag"), jsonObject.getString("Value").equals("false") ? "1" : "0", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) {
                    mRefreshLayout.autoRefresh();
                }
            });
        });
    }


    //长按item
    private void showDevicesInfDialog() {

        final String[] stringItems = {"解绑此设备", "重命名设备", "查看设备信息"};
    }

}


