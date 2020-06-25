package com.zcc.smarthome.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qmuiteam.qmui.layout.IQMUILayout;
import com.qmuiteam.qmui.layout.QMUIRelativeLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.zcc.smarthome.R;
import com.zcc.smarthome.activity.WelcomeActivity;
import com.zcc.smarthome.adapter.homeRecyclerAdapter;


public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private ViewMode mViewModel;
    private TextView textView8;
    private TextView textView9;
    private TextView textView14;
    private RecyclerView mRecyclerView;
    private QMUIRelativeLayout home_head;
    private QMUIPullRefreshLayout home_pull;

    private homeRecyclerAdapter adapter;

    @Override
    protected int setLayoutId() {
        mViewModel = ViewModelProviders.of(requireActivity()).get(ViewMode.class);
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        textView8 = view.findViewById(R.id.textView8);
        textView9 = view.findViewById(R.id.textView9);
        textView14 = view.findViewById(R.id.textView14);
        mRecyclerView = view.findViewById(R.id.homere);
        home_head = view.findViewById(R.id.home_head);
        home_pull = view.findViewById(R.id.home_pull);

        home_head.setRadius(88, IQMUILayout.HIDE_RADIUS_SIDE_TOP);

        home_pull.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                home_pull.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mActivity, "刷新成功！", Toast.LENGTH_SHORT).show();
                        home_pull.finishRefresh();
                    }
                }, 2000);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initData() {
        final Observer<JSONArray> jsonArrayObserver = objects -> {
            initrecy(objects);
            JSONObject jsonObject = new JSONObject(objects.getJSONObject(0));
//            textView.setText(String.format("%s %s", jsonObject.getString("Value"), jsonObject.getString("Unit")));
        };

        mViewModel.gethomejsonArrayMutableLiveData().observe(getViewLifecycleOwner(), jsonArrayObserver);
        if (WelcomeActivity.jsonWeather != null) {
            JSONObject jsonObject = WelcomeActivity.jsonWeather.getJSONObject("result").getJSONObject("result");
            JSONArray jsonArray = WelcomeActivity.jsonWeather.getJSONObject("result").getJSONObject("result").getJSONArray("index");
            textView8.setText(String.format("%s %s°C", jsonObject.getString("weather"), jsonObject.getString("temp")));
            textView9.setText(String.format("%s %s", jsonObject.getString("date"), jsonObject.getString("week")));
            textView14.setText(String.format("%s", jsonArray.getJSONObject((int) (Math.random() * 10 % 7)).getString("detail")));
        }

    }

    private void initrecy(JSONArray objects) {
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //        mRecyclerView.addItemDecoration();//设置分割线
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));//设置RecyclerView布局管理器为2列垂直排布
        adapter = new homeRecyclerAdapter(getContext(), objects);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new homeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int postion) {
                Toast.makeText(getContext(), "点击了：" + postion, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void ItemLongClickListener(View view, int postion) {
                //长按删除
//                adapter.notifyItemRemoved(postion);
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
