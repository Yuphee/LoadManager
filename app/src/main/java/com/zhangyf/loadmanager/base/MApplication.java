package com.zhangyf.loadmanager.base;

import android.app.Application;

import com.zhangyf.loadmanager.R;
import com.zhangyf.loadmanagerlib.LoadingAndRetryManager;

/**
 * Created by zhangyf on 2016/12/22.
 */

public class MApplication extends Application{

    private static MApplication instance = null;

    public static MApplication getInstance() {
        if (null == instance) {
            instance = new MApplication();
        }
        return instance;
    }

    public MApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initLoadingManager();
    }

    private void initLoadingManager() {
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.layout_reload_view;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.layout_load_view;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.empty_view;
    }
}
