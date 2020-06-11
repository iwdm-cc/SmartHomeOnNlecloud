package com.zcc.smarthome.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.zcc.smarthome.R;
import com.zcc.smarthome.adapter.mRecyclerViewMyScenceAdapter;
import com.zcc.smarthome.bean.ScencesListBean;
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
                    beanList.add(new ScencesListBean().setStrategyId(jsonObject.getString("StrategyId"))
                            .setTitle(jsonObject.getString("ConditionCn")));
                }
                rV_Mode();
            } else if (msg.what == TOASTED) {
                Toast.makeText(mActivity, " " + msg.obj, Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };
    private mRecyclerViewMyScenceAdapter mScenceAdapter;

    private List<ScencesListBean> beanList = new ArrayList<>();

    private Toolbar toolbarl;

    private RecyclerView rVMyScences;

    private DividerItemDecoration dividerItemDecoration;

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
//        toolbarl.inflateMenu(R.menu.menu_scence_add);
//        toolbarl.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.menu_id_list_mode:
//                        rV_Mode(false);
//                        break;
//                    case R.id.menu_id_grid_mode:
//                        rV_Mode(true);
//                        mScenceAdapter.notifyDataSetChanged();
//                        break;
//                }
//                return false;
//            }
//        });


        OkHttpUtils.getInstance().getStrategys("1", new Callback() {
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

        dividerItemDecoration = new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL);

    }

    private void rV_Mode() {
            rVMyScences.removeItemDecoration(dividerItemDecoration);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mScenceAdapter = new mRecyclerViewMyScenceAdapter(beanList, getActivity());
            mScenceAdapter.setOnLaunchClickListener(new mRecyclerViewMyScenceAdapter.OnLaunchClickListener() {
                @Override
                public void onClick(int position) {
                    String id = beanList.get(position).getStrategyId();
                    OkHttpUtils.getInstance().setEnable(id, Math.random() < 0.5 ? "true" : "false", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            handler.obtainMessage(TOASTED, "请检查网络！").sendToTarget();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            handler.obtainMessage(TOASTED, "开启成功").sendToTarget();

                        }
                    });
                }
            });
        mScenceAdapter.setOnItemClickListener(new mRecyclerViewMyScenceAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                handler.obtainMessage(TOASTED, "开启成功" + position).sendToTarget();
            }
        });
            rVMyScences.setLayoutManager(linearLayoutManager);
            rVMyScences.addItemDecoration(dividerItemDecoration);
            rVMyScences.setAdapter(mScenceAdapter);
        }


}
