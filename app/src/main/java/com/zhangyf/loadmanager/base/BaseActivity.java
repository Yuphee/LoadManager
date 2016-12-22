package com.zhangyf.loadmanager.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.zhangyf.loadmanagerlib.LoadingAndRetryManager;

import butterknife.ButterKnife;

/**
 * Created by zhangyf on 2016/12/22.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected LoadingAndRetryManager mLoadingAndRetryManager;
    protected int mLayoutId;
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(mLayoutId,savedInstanceState);
        mContext = this;
    }

    protected void createView(int layoutId, Bundle savedInstanceState) {
        if (layoutId != 0) {
            setContentView(layoutId);
            ButterKnife.bind(this);
        }
        // 页面加载管理器初始化
        initLoadManager();
        onInit(savedInstanceState);
    }

    protected void setLayoutId(int layoutId) {
        this.mLayoutId = layoutId;
    }

    protected abstract void onInit(Bundle savedInstanceState);

    protected void initLoadManager(){};

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
