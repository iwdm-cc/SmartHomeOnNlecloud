package com.zcc.smarthome.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.api.GizWifiSDK;
import com.gyf.barlibrary.ImmersionBar;
import com.zcc.smarthome.R;
import com.zcc.smarthome.utils.L;
import com.zcc.smarthome.view.ShadowContainer;

import java.util.ArrayList;
import java.util.List;


public class MyDevicesListActivity extends BaseActivity {


    private RecyclerView mRV_my_devices;
    private List<GizWifiDevice> deviceslist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_devices_list);
        initView();
    }

    private void initView() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImmersionBar.setTitleBar(this, toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        deviceslist = GizWifiSDK.sharedInstance().getDeviceList();
        L.e("deviceslist :" + deviceslist.size());
        for (int i = 0; i < deviceslist.size(); i++) {
            L.e("deviceslist :" + deviceslist.get(i).getIPAddress());
        }
        mRV_my_devices = findViewById(R.id.recyclerView_my_devices);
        mRV_my_devices.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        EntranceAdapter adapter = new EntranceAdapter(deviceslist);
        adapter.setOnItemClickListener(new EntranceAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int index) {
                L.e("MyDevicesListActivity onItemClicked index:" + index);
            }
        });
        mRV_my_devices.setAdapter(adapter);
    }


    //内部类适配器
    private static class EntranceViewHolder extends RecyclerView.ViewHolder {

        ShadowContainer shadowContainer;

        TextView nameTv;
        TextView mTvMac;
        TextView mTvProduceName;
        TextView mTvIPAdress;
        ImageView mIvState;
        TextView mTvState;

        public EntranceViewHolder(View itemView, final EntranceAdapter.OnItemClickListener listener) {
            super(itemView);
            shadowContainer = itemView.findViewById(R.id.container);
            nameTv = itemView.findViewById(R.id.entrance_name_tv);

            mTvMac = itemView.findViewById(R.id.tvMac);
            mTvProduceName = itemView.findViewById(R.id.tvProduceName);
            mTvIPAdress = itemView.findViewById(R.id.tvIPAdress);

            mIvState = itemView.findViewById(R.id.ivState);
            mTvState = itemView.findViewById(R.id.tvState);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClicked(getAdapterPosition());
                    }
                }
            });
        }
    }

    private static class EntranceAdapter extends RecyclerView.Adapter<EntranceViewHolder> {

        private List<GizWifiDevice> deviceslist;
        private int[][] sPalettes;
        private List<Integer> shadowColorList = new ArrayList<>();

        public EntranceAdapter(List<GizWifiDevice> deviceslist) {
            this.deviceslist = deviceslist;
            this.sPalettes = new int[][]{
                    {0xFFF98989, 0xFFE03535},
                    {0xFFC1E480, 0xFF67CC34},
                    {0xFFEDF179, 0xFFFFB314},
                    {0xFF80DDE4, 0xFF286EDC},
                    {0xFFE480C6, 0xFFDC285E},
            };

            shadowColorList.add(0xFFF98989);
            shadowColorList.add(0xFFC1E480);
            shadowColorList.add(0xFFEDF179);
            shadowColorList.add(0xFF80DDE4);
            shadowColorList.add(0xFFE480C6);
        }

        interface OnItemClickListener {
            void onItemClicked(int index);
        }

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @Override
        public EntranceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_devices_list, parent, false);
            return new EntranceViewHolder(view, mOnItemClickListener);
        }

        @Override
        public void onBindViewHolder(EntranceViewHolder holder, int position) {

            int randow = (int) (0 + Math.random() * 5);
            L.e("EntranceAdapter randow ：" + randow);
            //设置阴影颜色
            holder.shadowContainer.setShadowColor(shadowColorList.get(randow));
            holder.shadowContainer.setShadowRadius((int) (holder.shadowContainer.getResources().getDisplayMetrics().density * 6));
            //设置渐变颜色
            ShadowContainer.ShadowDrawable shadowDrawable = holder.shadowContainer.getShadowDrawable();
            shadowDrawable.setCornerRadius((int) (holder.shadowContainer.getResources().getDisplayMetrics().density * 4));
            shadowDrawable.setColors(sPalettes[randow]);
        }

        @Override
        public int getItemCount() {
            return sPalettes.length;
        }
    }
}
