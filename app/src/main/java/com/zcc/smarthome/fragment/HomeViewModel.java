package com.zcc.smarthome.fragment;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONArray;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<JSONArray> jsonArrayMutableLiveData;

    MutableLiveData<JSONArray> getJsonArrayMutableLiveData() {
        if (jsonArrayMutableLiveData == null) {
            jsonArrayMutableLiveData = new MutableLiveData<JSONArray>();
        }
        return jsonArrayMutableLiveData;
    }

}
