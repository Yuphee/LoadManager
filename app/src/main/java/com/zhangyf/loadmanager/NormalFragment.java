package com.zhangyf.loadmanager;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhangyf.loadmanager.base.BaseLazyFragment;
import com.zhangyf.loadmanager.listener.DefaultLoadListener;
import com.zhangyf.loadmanagerlib.PrePageManager;

import java.util.Random;

/**
 * Created by zhangyf on 16/12/26.
 */
public class NormalFragment extends BaseLazyFragment
{

    private boolean isload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setLayoutId(R.layout.fragment_anim);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void onInit() {
        loadData();
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initPreManager() {
        super.initPreManager();
        mPrePageManager = PrePageManager.generate(this, new DefaultLoadListener()
        {
            @Override
            public void onRetryClick(View retryView) {
                loadData();
            }

        });
    }

    private void loadData() {
        mPrePageManager.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int te = random.nextInt(10);
                if(!isload){
                    mPrePageManager.showRetry();
                    isload = true;
                }else{
                    mPrePageManager.showContent();
                }
//                if (te < 5) {
//                    mLoadingAndRetryManager.showRetry();
//                } else {
//                    // you can set showContent(delay)
//                    mLoadingAndRetryManager.showContent();
//                }

            }
        }, 3000);
    }

}


