package com.zcc.smarthome.adapter;

import android.content.Context;
import android.graphics.Color;
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
        holder.tvName.setTextColor(flog ? Color.BLACK : Color.BLUE);

        holder.tvName.setText(String.format("%s", jsonObject.getString("Name")));
        holder.vole.setText(String.format("%s %s", jsonObject.getString("Value"), jsonObject.getString("Unit")));
        holder.allListMode.setOnClickListener(v -> {
            if (itemOnClickListener != null) {
                itemOnClickListener.onClick(position);
            }
        });
        holder.allLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation rotateAnimation = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(2000);
                rotateAnimation.setFillAfter(true);
                holder.ivListModeLaunch.startAnimation(rotateAnimation);
                if (launchOnClickListener != null) {
                    launchOnClickListener.onClick(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return jsonArray.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        LinearLayout allListMode;
        LinearLayout allLaunch;
        ImageView ivListModeLaunch;
        TextView vole;
        TextView title; //标题
        TextView openorclose; //开关与否

        ViewHolder(View view) {
            super(view);
            ivListModeLaunch = view.findViewById(R.id.ivLaunch);
            tvName = view.findViewById(R.id.tvName);
            allListMode = view.findViewById(R.id.allListMode);
            allLaunch = view.findViewById(R.id.allLaunch);
            vole = view.findViewById(R.id.vole);
            title = view.findViewById(R.id.title);
            openorclose = view.findViewById(R.id.openorclose);
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
