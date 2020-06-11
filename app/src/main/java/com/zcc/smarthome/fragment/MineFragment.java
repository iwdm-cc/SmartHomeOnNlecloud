package com.zcc.smarthome.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcc.smarthome.R;
import com.zcc.smarthome.activity.AlterUserInfActivity;
import com.zcc.smarthome.utils.TakePictureManager;
import com.zcc.smarthome.utils.ToastUtils;
import com.zcc.smarthome.view.AnimotionPopupWindow;
import com.zcc.smarthome.view.PullScrollView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MineFragment extends BaseFragment implements View.OnClickListener {


    private ImageView ivHeaderBg;
    private ImageView ivmeIcon;
    private PullScrollView pullView;
    private TextView tvName;
    private TextView mTvDevices;
    private TextView mTvShareDevices;
    private TextView mTvDevicesLog;
    private com.lqr.optionitemview.OptionItemView mOVUserInf;
    private com.lqr.optionitemview.OptionItemView mOVCarText;
    private com.lqr.optionitemview.OptionItemView mOVDayHappy;
    private com.lqr.optionitemview.OptionItemView mOVAbout;
    private com.lqr.optionitemview.OptionItemView OVVegetable;
    //上传图片用到
    private TakePictureManager takePictureManager;
    //拍照完图片保存的路径

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {

        mTvDevices = view.findViewById(R.id.tvDevices);
        mTvDevices.setOnClickListener(this);
        mTvShareDevices = view.findViewById(R.id.tvShareDevices);
        mTvShareDevices.setOnClickListener(this);
        mTvDevicesLog = view.findViewById(R.id.tvDevicesLog);
        mTvDevicesLog.setOnClickListener(this);

        mOVUserInf = view.findViewById(R.id.OVUserInf);
        mOVUserInf.setOnClickListener(this);
        mOVCarText = view.findViewById(R.id.OVCarText);
        mOVCarText.setOnClickListener(this);
        mOVDayHappy = view.findViewById(R.id.OVDayHappy);
        mOVDayHappy.setOnClickListener(this);
        mOVAbout = view.findViewById(R.id.OVAbout);
        OVVegetable = view.findViewById(R.id.OVWeather);
        mOVAbout.setOnClickListener(this);
        OVVegetable.setOnClickListener(this);


        ivHeaderBg = view.findViewById(R.id.ivHeaderBg);
        ivmeIcon = view.findViewById(R.id.ivIcon);
        tvName = view.findViewById(R.id.tvName);
        ivmeIcon.setOnClickListener(this);
        pullView = view.findViewById(R.id.pullView);
        pullView.setZoomView(ivHeaderBg);


    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()) {

            //我的设备
            case R.id.tvDevices:
                break;

            case R.id.ivIcon:
                    List<String> list = new ArrayList<>();
                    list.add("更改头像");
                    list.add("切换账号");
                    list.add("退出登录");
                    AnimotionPopupWindow popupWindow = new AnimotionPopupWindow(getActivity(), list);
                    popupWindow.show();
                    popupWindow.setAnimotionPopupWindowOnClickListener(new AnimotionPopupWindow.AnimotionPopupWindowOnClickListener() {
                        @Override
                        public void onPopWindowClickListener(int position) {
                            switch (position) {
                                //更改头像
                                case 0:
                                    changeMyIcon();
                                    break;
                                //切换账号
                                case 1:
                                    startActivity(new Intent(getActivity(), AlterUserInfActivity.class));
                                    break;
                                //退出登录
                                case 2:
                                    ToastUtils.showToast(getActivity(), "退出成功！");
//                                    getUserInf();
                                    break;

                            }
                        }
                    });
                break;
            //设备分享
            case R.id.tvShareDevices:
                break;
            //设备日志
            case R.id.tvDevicesLog:
                break;

            case R.id.OVUserInf:
                startActivity(new Intent(getActivity(), AlterUserInfActivity.class));
                break;
            case R.id.OVCarText:
                break;
            case R.id.OVDayHappy:
                break;
            //天气预报
            case R.id.OVWeather:
                break;
            case R.id.OVAbout:
                break;
        }
    }




    private void changeMyIcon() {

        List<String> list = new ArrayList<>();
        list.add("拍照");
        list.add("相册");

        AnimotionPopupWindow animotionPopupWindow = new AnimotionPopupWindow(getActivity(), list);
        animotionPopupWindow.show();
        animotionPopupWindow.setAnimotionPopupWindowOnClickListener(new AnimotionPopupWindow.AnimotionPopupWindowOnClickListener() {
            @Override
            public void onPopWindowClickListener(int position) {
                switch (position) {
                    case 0:
                        openCamera();
                        break;
                    case 1:
                        openAlbun();
                        break;
                }
            }
        });

    }

    private void openAlbun() {
        takePictureManager = new TakePictureManager(this);
        takePictureManager.setTailor(1, 1, 350, 350);
        takePictureManager.startTakeWayByAlbum();
        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
            @Override
            public void successful(boolean isTailor, File outFile, Uri filePath) {

            }

            @Override
            public void failed(int errorCode, List<String> deniedPermissions) {

            }

        });
    }

    private void openCamera() {

        takePictureManager = new TakePictureManager(this);
        takePictureManager.setTailor(1, 1, 350, 350);
        takePictureManager.startTakeWayByCarema();
        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
            @Override
            public void successful(boolean isTailor, File outFile, Uri filePath) {

            }

            @Override
            public void failed(int errorCode, List<String> deniedPermissions) {

            }
        });

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        takePictureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
    }

}