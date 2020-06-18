package com.zcc.smarthome.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import com.zcc.smarthome.R;
import com.zcc.smarthome.activity.AlterUserInfActivity;
import com.zcc.smarthome.utils.TakePictureManager;
import com.zcc.smarthome.view.AnimotionPopupWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MineFragment extends BaseFragment implements View.OnClickListener {


    private com.lqr.optionitemview.OptionItemView mOVUserInf;

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


        mOVUserInf = view.findViewById(R.id.OVUserInf);
        mOVUserInf.setOnClickListener(this);

        mOVDayHappy = view.findViewById(R.id.OVDayHappy);
        mOVDayHappy.setOnClickListener(this);
        mOVAbout = view.findViewById(R.id.OVAbout);
        OVVegetable = view.findViewById(R.id.OVWeather);
        mOVAbout.setOnClickListener(this);
        OVVegetable.setOnClickListener(this);


    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.OVUserInf:
                startActivity(new Intent(getActivity(), AlterUserInfActivity.class));
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