package com.zcc.smarthome.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class ScencesListBean {
    private String StrategyId;
    private String title;
    private Drawable drawable;
    private boolean isShowGo;

    public String getStrategyId() {
        return StrategyId;
    }

    public ScencesListBean setStrategyId(String strategyId) {
        StrategyId = strategyId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ScencesListBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public boolean isShowGo() {
        return isShowGo;
    }

    public void setShowGo(boolean showGo) {
        isShowGo = showGo;
    }
}
