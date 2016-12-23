package com.zhangyf.loadmanager;

import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhangyf.loadmanager.base.BaseActivity;
import com.zhangyf.loadmanager.listener.DefaultLoadListener;
import com.zhangyf.loadmanagerlib.LoadingAndRetryManager;

import java.util.Random;

import butterknife.Bind;

/**
 * Created by zhangyf on 16/12/22
 * fragment可能会存在问题建议使用LoadingAndRetryManager.generate(View or Activity, new DefaultLoadListener()
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.rl_main)
    RelativeLayout rl_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onInit(Bundle savedInstanceState) {
        // init your data and call mLoadingAndRetryManager.xxx to control the view show and gone
        loadData();
    }

    private void loadData() {
        mLoadingAndRetryManager.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int te = random.nextInt(10);
                if(te < 5) {
                    mLoadingAndRetryManager.showRetry();
                }else {
                    // you can set showContent(delay)
                    mLoadingAndRetryManager.showContent();
                }

            }
        },3000);
    }

    @Override
    protected void initLoadManager() {
        super.initLoadManager();
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, new DefaultLoadListener() {
            @Override
            public void onRetryClick(View retryView) {
                // Do Something
                loadData();
            }

        });
    }
}
