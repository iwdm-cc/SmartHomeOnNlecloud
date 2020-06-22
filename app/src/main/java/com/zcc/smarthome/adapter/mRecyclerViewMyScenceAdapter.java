package com.zcc.smarthome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcc.smarthome.R;
import com.zcc.smarthome.bean.ScencesListBean;

import java.util.List;


public class mRecyclerViewMyScenceAdapter extends RecyclerView.Adapter<mRecyclerViewMyScenceAdapter.ViewHolder> {


    private List<ScencesListBean> beanList;

    private LayoutInflater inflater;


    private OnLaunchClickListener launchOnClickListener;

    private OnItemClickListener itemOnClickListener;

    private OnItemLongClickListener longClickListener;

    public mRecyclerViewMyScenceAdapter(List<ScencesListBean> beanList, Context mContext) {
        this.beanList = beanList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_mode_scence, parent, false);

        return new mRecyclerViewMyScenceAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

//        holder.tvName.setText(beanList.get(position).getTitle());
        holder.allListMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOnClickListener != null) {
                    itemOnClickListener.onClick(position);
                }
            }
        });

        holder.allListModeLaunch.setOnClickListener(new View.OnClickListener() {
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
        return beanList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout allListModeLaunch;
        TextView tvName;
        LinearLayout allListMode;

        ImageView ivListModeLaunch;


        ViewHolder(View view) {
            super(view);
            allListModeLaunch = view.findViewById(R.id.allLaunch);
            ivListModeLaunch = view.findViewById(R.id.ivLaunch);
            tvName = view.findViewById(R.id.tvName);
            allListMode = view.findViewById(R.id.allListMode);
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
