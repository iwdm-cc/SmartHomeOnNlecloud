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

import com.alibaba.fastjson.JSONObject;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.zcc.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 场景界面，适配器
 */

public class mRecyclerViewMyScenceAdapter extends RecyclerView.Adapter<mRecyclerViewMyScenceAdapter.ViewHolder> {


    private List<JSONObject> beanList;

    private LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private int[] imglist = new int[]{R.drawable.food, R.drawable.yingyuan};
    private OnLaunchClickListener launchOnClickListener;

    private OnItemClickListener itemOnClickListener;

    private OnItemLongClickListener longClickListener;

    public mRecyclerViewMyScenceAdapter(List<JSONObject> beanList, Context mContext) {
        this.beanList = beanList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", "吃饭模式");
        map.put("img", R.drawable.food);
        list.add(map);
        map = new HashMap<>();
        map.put("title", "电影模式");
        map.put("img", R.drawable.yingyuan);
        list.add(map);
        map = new HashMap<>();
        map.put("title", "电影模式");
        map.put("img", R.drawable.yingyuan);
        list.add(map);
        map = new HashMap<>();
        map.put("title", "电影模式");
        map.put("img", R.drawable.yingyuan);
        list.add(map);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_mode_scence, parent, false);

        return new mRecyclerViewMyScenceAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.scence_img.setCornerRadius(30);
        holder.scence_img.setBorderColor(Color.WHITE);
        holder.tvName.setText((String) list.get(position).get("title"));
//        holder.tvName.setText(beanList.get(position).getByte("Nullity")==0?"开启":"关闭");
        holder.scence_img.setImageResource((Integer) list.get(position).get("img"));
        boolean nullity = beanList.get(position).getByte("Nullity") == 0;
        holder.allLaunch.setBackgroundResource(nullity ? R.drawable.ic_button_bg_normal : R.drawable.ic_button_bg_press);
        holder.text_launch.setText(nullity ? "打开" : "关闭");

        holder.allLaunch.setOnClickListener(v -> {
            RotateAnimation rotateAnimation = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(2000);
            rotateAnimation.setFillAfter(true);
            holder.ivListModeLaunch.startAnimation(rotateAnimation);
            if (launchOnClickListener != null) {
                launchOnClickListener.onClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return beanList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout allLaunch;
        TextView tvName;
        TextView text_launch;
        ImageView ivListModeLaunch;
        QMUIRadiusImageView scence_img;


        ViewHolder(View view) {
            super(view);
            allLaunch = view.findViewById(R.id.allLaunch);
            ivListModeLaunch = view.findViewById(R.id.ivLaunch);
            tvName = view.findViewById(R.id.tvName);
            text_launch = view.findViewById(R.id.text_launch);

            scence_img = view.findViewById(R.id.scence_img);
        }
    }


    public void setOnItemClickListener(mRecyclerViewMyScenceAdapter.OnItemClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }


    public void setOnLaunchClickListener(mRecyclerViewMyScenceAdapter.OnLaunchClickListener itemOnClickListener) {
        this.launchOnClickListener = itemOnClickListener;
    }

    public void setlongClickListener(mRecyclerViewMyScenceAdapter.OnItemLongClickListener longClickListener) {
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
