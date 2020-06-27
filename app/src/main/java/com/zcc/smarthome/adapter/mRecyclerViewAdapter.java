package com.zcc.smarthome.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zcc.smarthome.R;

/**
 * 设备控制界面，适配器
 */

public class mRecyclerViewAdapter extends RecyclerView.Adapter<mRecyclerViewAdapter.ViewHolder> {


    private com.alibaba.fastjson.JSONArray jsonArray;

    private LayoutInflater inflater;


    private OnLaunchClickListener launchOnClickListener;

    private OnItemClickListener itemOnClickListener;

    private OnItemLongClickListener longClickListener;

    public mRecyclerViewAdapter(com.alibaba.fastjson.JSONArray beanList, Context mContext) {
        this.jsonArray = beanList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_dev_list, parent, false);

        return new mRecyclerViewAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        JSONObject jsonObject = jsonArray.getJSONObject(position);
        boolean flog = position % 2 == 0;

        holder.title.setText(String.format("%s", jsonObject.getString("Name")));
        holder.openorclose.setText(jsonObject.getString("Value").equals("false") ? "已关闭" : "已打开");
        holder.time.setText(jsonObject.getString("RecordTime"));
        holder.DeviceID.setText(jsonObject.getString("DeviceID"));

        holder.allListMode.setOnClickListener(v -> {
            if (itemOnClickListener != null) {
                itemOnClickListener.onClick(position);
            }
        });
        holder.allLaunch.setOnClickListener(v -> {
            RotateAnimation rotateAnimation = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(2000);
            rotateAnimation.setFillAfter(true);
            holder.ivLaunch.startAnimation(rotateAnimation);
            if (launchOnClickListener != null) {
                launchOnClickListener.onClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return jsonArray.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout allListMode;
        LinearLayout allLaunch;
        ImageView ivLaunch;

        TextView title; //标题
        TextView openorclose; //开关与否
        TextView time; //开关与否
        TextView DeviceID; //开关与否

        ViewHolder(View view) {
            super(view);
            ivLaunch = view.findViewById(R.id.ivLaunch);
            allListMode = view.findViewById(R.id.allListMode);
            allLaunch = view.findViewById(R.id.allLaunch);

            title = view.findViewById(R.id.title);
            openorclose = view.findViewById(R.id.openorclose);
            time = view.findViewById(R.id.time);
            DeviceID = view.findViewById(R.id.DeviceID);
        }
    }


    public void setOnItemClickListener(mRecyclerViewAdapter.OnItemClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public JSONArray getData() {
        return this.jsonArray;
    }


    public void setOnLaunchClickListener(mRecyclerViewAdapter.OnLaunchClickListener itemOnClickListener) {
        this.launchOnClickListener = itemOnClickListener;
    }

    public void setlongClickListener(mRecyclerViewAdapter.OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }


    public interface OnLaunchClickListener {
        void onClick(int position);
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public interface OnItemLongClickListener {
        void onClick(int position);
    }


}
