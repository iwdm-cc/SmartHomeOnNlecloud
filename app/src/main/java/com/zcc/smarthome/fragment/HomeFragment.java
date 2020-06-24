package com.zcc.smarthome.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.zcc.smarthome.R;
import com.zcc.smarthome.activity.WelcomeActivity;
import com.zcc.smarthome.adapter.MyRecyclerAdapter;

import java.util.Arrays;

import cn.bingoogolapple.bgabanner.BGABanner;


public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private ViewMode mViewModel;
    private BGABanner mBanner;
    private TextView textView8;
    private TextView textView9;
    private TextView textView14;
    private RecyclerView mRecyclerView;

    private MyRecyclerAdapter adapter;

    @Override
    protected int setLayoutId() {
        mViewModel = ViewModelProviders.of(requireActivity()).get(ViewMode.class);
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        mBanner = view.findViewById(R.id.banner_main_depth);
        textView8 = view.findViewById(R.id.textView8);
        textView9 = view.findViewById(R.id.textView9);
        textView14 = view.findViewById(R.id.textView14);
        mRecyclerView = view.findViewById(R.id.homere);

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
            textView8.setText(String.format("%s  %s°C", jsonObject.getString("weather"), jsonObject.getString("temp")));
            textView9.setText(String.format("%s %s", jsonObject.getString("date"), jsonObject.getString("week")));
            textView14.setText(String.format("%s", jsonArray.getJSONObject((int) (Math.random() * 10 % 7)).getString("detail")));
        }
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

    private void initrecy(JSONArray objects) {
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //        mRecyclerView.addItemDecoration();//设置分割线
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));//设置RecyclerView布局管理器为2列垂直排布
        adapter = new MyRecyclerAdapter(getContext(), objects);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new MyRecyclerAdapter.OnItemClickListener() {
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
