package com.zcc.smarthome.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zcc.smarthome.R;

public class ToastUtils {

    /**
     * 带有图片的Toast
     * @param mContext 上下文
     * @param picID    左边图标的id
     * @param title    标题
     */

    public static void showPhotoToast(Context mContext, int picID, String title) {

        try {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.view_toast, null);
            Toast toast = new Toast(mContext);

            TextView textView = view.findViewById(R.id.tv_toast);
            textView.setText(title);

            ImageView ivToast = view.findViewById(R.id.ivToast);
            ivToast.setBackground(mContext.getResources().getDrawable(picID));

            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);
            toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
            toast.setMargin(0, 0);
            toast.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showToast(Context mContext, String title) {
        Toast.makeText(mContext, title, Toast.LENGTH_SHORT).show();
    }
}
