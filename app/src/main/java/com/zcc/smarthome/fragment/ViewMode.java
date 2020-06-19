package com.zcc.smarthome.fragment;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONArray;

public class ViewMode extends ViewModel {
    private MutableLiveData<JSONArray> jsonArrayMutableLiveData;
    private MutableLiveData<JSONArray> homejsonArrayMutableLiveData;

    MutableLiveData<JSONArray> getJsonArrayMutableLiveData() {
        if (jsonArrayMutableLiveData == null) {
            jsonArrayMutableLiveData = new MutableLiveData<>();
        }
        return jsonArrayMutableLiveData;
    }

    MutableLiveData<JSONArray> gethomejsonArrayMutableLiveData() {
        if (homejsonArrayMutableLiveData == null) {
            homejsonArrayMutableLiveData = new MutableLiveData<>();
        }
        return homejsonArrayMutableLiveData;
    }

}
