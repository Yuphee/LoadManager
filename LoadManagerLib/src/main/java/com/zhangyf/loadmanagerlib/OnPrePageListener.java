package com.zhangyf.loadmanagerlib;

import android.view.View;

/**
 * Created by zhangyf on 16/12/20.
 */
public abstract class OnPrePageListener {
    public abstract void onRetryStart(View retryView);

    public void onLoadingStart(View loadingView) {
    }

    public void onLoadingEnd(View loadingView) {

    }

    public void onEmptyStart(View emptyView) {
    }

    public void onContentStart(View allView) {

    }

    public int generateLoadingLayoutId() {
        return PrePageManager.NO_LAYOUT_ID;
    }

    public int generateRetryLayoutId() {
        return PrePageManager.NO_LAYOUT_ID;
    }

    public int generateEmptyLayoutId() {
        return PrePageManager.NO_LAYOUT_ID;
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
        if (generateLoadingLayoutId() != PrePageManager.NO_LAYOUT_ID || generateLoadingLayout() != null)
            return true;
        return false;
    }

    public boolean isSetRetryLayout() {
        if (generateRetryLayoutId() != PrePageManager.NO_LAYOUT_ID || generateRetryLayout() != null)
            return true;
        return false;
    }

    public boolean isSetEmptyLayout() {
        if (generateEmptyLayoutId() != PrePageManager.NO_LAYOUT_ID || generateEmptyLayout() != null)
            return true;
        return false;
    }


}