package com.zhangyf.loadmanager;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zhangyf.loadmanager.base.BaseActivity;
import com.zhangyf.loadmanager.listener.DefaultLoadListener;
import com.zhangyf.loadmanagerlib.LoadingAndRetryLayout;
import com.zhangyf.loadmanagerlib.LoadingAndRetryManager;

import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zhangyf on 16/12/22
 * fragment可能会存在问题建议使用LoadingAndRetryManager.generate(View or Activity, new DefaultLoadListener()
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.rl_main)
    LinearLayout rl_main;
    @Bind(R.id.bt_test)
    Button bt_test;
    private boolean isload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setLayoutId(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onInit(Bundle savedInstanceState) {
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.LEFT);//滑出的方向
        slide.setInterpolator(new DecelerateInterpolator());
        slide.setDuration(500);//动画持续时间
        getWindow().setExitTransition(slide);
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

                if(!isload){
                    mLoadingAndRetryManager.showRetry();
                    isload = true;
                }else{
                    // you can set showContent(delay) or set the showview entering animator
                    mLoadingAndRetryManager.showContent(new LoadingAndRetryLayout.AnimatorsListener(){
                        @Override
                        public Animator[] onAnimators(View view) {
                            return new Animator[] { ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
                                    ,ObjectAnimator.ofFloat(view, "translationY", 2000, 0)};
                        }
                    });
                }
//                if (te < 5) {
//                    mLoadingAndRetryManager.showRetry();
//                } else {
//                    // you can set showContent(delay) or set the showview entering animator
//                    mLoadingAndRetryManager.showContent(new LoadingAndRetryLayout.AnimatorsListener(){
//                        @Override
//                        public Animator[] onAnimators(View view) {
//                            return new Animator[] { ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
//                            ,ObjectAnimator.ofFloat(view, "translationY", 2000, 0)};
//                        }
//                    });
//                }

            }
        }, 3000);
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


    @OnClick(R.id.rl_main)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_main:
                Intent intent = new Intent(this,AnimActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,bt_test,"shareName").toBundle());
                break;
        }
    }
}
