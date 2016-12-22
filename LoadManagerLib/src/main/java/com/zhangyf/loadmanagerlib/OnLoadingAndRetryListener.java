package com.zhangyf.loadmanagerlib;

import android.view.View;

/**
 * Created by zhangyf on 16/12/20.
 */
public abstract class OnLoadingAndRetryListener {
    public abstract void onRetryEvent(View retryView);

    public void onLoadingEvent(View loadingView) {
    }

    public void onLoadingEndEvent(View loadingView) {

    }

    public void onEmptyEvent(View emptyView) {
    }

    public void onContentEvent(View allView) {

    }

    public int generateLoadingLayoutId() {
        return LoadingAndRetryManager.NO_LAYOUT_ID;
    }

    public int generateRetryLayoutId() {
        return LoadingAndRetryManager.NO_LAYOUT_ID;
    }

    public int generateEmptyLayoutId() {
        return LoadingAndRetryManager.NO_LAYOUT_ID;
    }

    public View generateLoadingLayout() {
        return null;
    }

    public View generateRetryLayout() {
        return null;
    }

    public View generateEmptyLayout() {
        return null;
    }

    public boolean isSetLoadingLayout() {
        if (generateLoadingLayoutId() != LoadingAndRetryManager.NO_LAYOUT_ID || generateLoadingLayout() != null)
            return true;
        return false;
    }

    public boolean isSetRetryLayout() {
        if (generateRetryLayoutId() != LoadingAndRetryManager.NO_LAYOUT_ID || generateRetryLayout() != null)
            return true;
        return false;
    }

    public boolean isSetEmptyLayout() {
        if (generateEmptyLayoutId() != LoadingAndRetryManager.NO_LAYOUT_ID || generateEmptyLayout() != null)
            return true;
        return false;
    }


}