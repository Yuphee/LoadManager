package com.zhangyf.loadmanager.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.zhangyf.loadmanagerlib.LoadingAndRetryManager;

import butterknife.ButterKnife;

/**
 * Created by zhangyf on 2016/12/26.
 */
public abstract class BaseLazyFragment extends Fragment {

    private boolean isVisible, isPrepared, isLazyLoaded;
    protected AppCompatActivity mActivity;
    public View root;
    protected int layoutId;
    protected LoadingAndRetryManager mLoadingAndRetryManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layoutId != 0) {
            root = inflater.inflate(layoutId, container, false);
            ButterKnife.bind(this, root);
        }
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 页面加载管理器初始化
        initLoadManager();
        onInit();
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void onDestroyView() {
        isPrepared = false;
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    protected abstract void onInit();

    protected void onRepeatLazyLoad(){}

    protected abstract void onLazyLoad();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    public void lazyLoad() {
        if(isPrepared && isVisible) {
            if(!isLazyLoaded){
                isLazyLoaded = true;
                onLazyLoad();
            }
            else {
                onRepeatLazyLoad();
            }
        }
    }

    protected void initLoadManager(){}
}
