package com.zcc.smarthome.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.zcc.smarthome.R;
import com.zcc.smarthome.adapter.mRecyclerViewMyScenceAdapter;
import com.zcc.smarthome.constant.Constant;
import com.zcc.smarthome.utils.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ScenceFragment extends BaseFragment {
    final private static int GDATA = 1;
    final private static int TOASTED = 2;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GDATA) {
                beanList.clear();
                JSONArray jsonArray = (JSONArray) msg.obj;
                Toast.makeText(mActivity, "数量" + jsonArray.size(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    beanList.add(jsonObject);
                }
                rV_Mode();
            } else if (msg.what == TOASTED) {
                Toast.makeText(mActivity, " " + msg.obj, Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };
    private mRecyclerViewMyScenceAdapter mScenceAdapter;

    private List<JSONObject> beanList = new ArrayList<>();

    private Toolbar toolbarl;

    private RecyclerView rVMyScences;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(), toolbarl);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_scence;
    }

    @Override
    protected void initView(View view) {
        toolbarl = view.findViewById(R.id.toolbar);


        OkHttpUtils.getInstance().getStrategys(Constant.ProjectID, Constant.DeviceID, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.obtainMessage(TOASTED, "请检查网络！").sendToTarget();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    JSONObject jsonObject = JSONObject.parseObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONObject("ResultObj").getJSONArray("PageSet");
                    handler.obtainMessage(GDATA, jsonArray).sendToTarget();
                }
            }
        });

        rVMyScences = view.findViewById(R.id.rVMyScences);


    }

    private void rV_Mode() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mScenceAdapter = new mRecyclerViewMyScenceAdapter(beanList, getActivity());
        mScenceAdapter.setOnLaunchClickListener(position -> {
            String id = beanList.get(position).getString("StrategyId");
            String open = beanList.get(position).getByte("Nullity") == 1 ? "true" : "false";
            OkHttpUtils.getInstance().setEnable(id, open, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    handler.obtainMessage(TOASTED, "请检查网络！").sendToTarget();
                }

                @Override
                public void onResponse(Call call, Response response) {
                    handler.obtainMessage(TOASTED, "开启成功").sendToTarget();

                }
            });
        });
        mScenceAdapter.setOnItemClickListener(position -> {
//                handler.obtainMessage(TOASTED, "开启成功" + position).sendToTarget();
        });
        rVMyScences.setLayoutManager(layoutManager);
        rVMyScences.setAdapter(mScenceAdapter);
    }


}
