package com.zhangyf.loadmanager.listener;

import android.os.Handler;
import android.view.View;

import com.zhangyf.loadmanager.R;
import com.zhangyf.loadmanager.widget.loadingdrawable.LoadingView;
import com.zhangyf.loadmanagerlib.OnLoadingAndRetryListener;


/**
 * Created by zhangyf on 2016/12/21.
 * 默认叶子加载动画Loading页
 */

public abstract class DefaultLoadListener extends OnLoadingAndRetryListener {

    public abstract void onRetryClick(View retryView);

    @Override
    public void onRetryEvent(final View retryView) {
        View view = retryView.findViewById(R.id.btn_reload);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onRetryClick(retryView);
            }
        });
    }

    @Override
    public void onLoadingEvent(View loadingView) {
        super.onLoadingEvent(loadingView);
        final LoadingView view = (LoadingView) loadingView.findViewById(R.id.electric_fan_view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.startAnimation();
            }
        },200);

    }

    @Override
    public void onLoadingEndEvent(View loadingView) {
        super.onLoadingEndEvent(loadingView);
        final LoadingView view = (LoadingView) loadingView.findViewById(R.id.electric_fan_view);
        view.stopAnimation();
    }
}
