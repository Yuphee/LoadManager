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
        initLoadManager();
    }

    private void initLoadManager() {
        LoadingAndRetryManager.initManagerLayout(R.layout.layout_load_view,R.layout.layout_reload_view,
                R.layout.empty_view);
    }
}
