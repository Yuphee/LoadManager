package com.zhangyf.loadmanagerlib;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhangyf on 16/12/20
 */
public class LoadingAndRetryManager {
    public static final int NO_LAYOUT_ID = 0;
    public static int BASE_LOADING_LAYOUT_ID = NO_LAYOUT_ID;
    public static int BASE_RETRY_LAYOUT_ID = NO_LAYOUT_ID;
    public static int BASE_EMPTY_LAYOUT_ID = NO_LAYOUT_ID;
    private OnLoadingAndRetryListener mListener;
    private Context mContext;

    public LoadingAndRetryLayout mLoadingAndRetryLayout;


    public OnLoadingAndRetryListener DEFAULT_LISTENER = new OnLoadingAndRetryListener() {
        @Override
        public void onRetryEvent(View retryView) {

        }
    };

    public static void initManagerLayout(int loading_layout, int retry_layout, int empty_layout){
        BASE_LOADING_LAYOUT_ID = loading_layout;
        BASE_RETRY_LAYOUT_ID = retry_layout;
        BASE_EMPTY_LAYOUT_ID = empty_layout;
    }


    public LoadingAndRetryManager(Object activityOrFragmentOrView, OnLoadingAndRetryListener listener) {
        mListener = listener;
        if (mListener == null) {
            mListener = DEFAULT_LISTENER;
        }

        ViewGroup contentParent = null;
        if (activityOrFragmentOrView instanceof Activity) {
            Activity activity = (Activity) activityOrFragmentOrView;
            mContext = activity;
            contentParent = (ViewGroup) activity.findViewById(android.R.id.content);
        } else if (activityOrFragmentOrView instanceof Fragment) {
            Fragment fragment = (Fragment) activityOrFragmentOrView;
            mContext = fragment.getActivity();
            contentParent = (ViewGroup) (fragment.getView().getParent());
        } else if (activityOrFragmentOrView instanceof View) {
            View view = (View) activityOrFragmentOrView;
            contentParent = (ViewGroup) (view.getParent());
            mContext = view.getContext();
        } else {
            throw new IllegalArgumentException("the argument's type must be Fragment or Activity: init(context)");
        }
        int childCount = contentParent.getChildCount();
        //get contentParent
        int index = 0;
        View oldContent;
        if (activityOrFragmentOrView instanceof View) {
            oldContent = (View) activityOrFragmentOrView;
            for (int i = 0; i < childCount; i++) {
                if (contentParent.getChildAt(i) == oldContent) {
                    index = i;
                    break;
                }
            }
        } else {
            oldContent = contentParent.getChildAt(0);
        }
        contentParent.removeView(oldContent);
        //setup content layout
        LoadingAndRetryLayout loadingAndRetryLayout = new LoadingAndRetryLayout(mContext);

        ViewGroup.LayoutParams lp = oldContent.getLayoutParams();
        contentParent.addView(loadingAndRetryLayout, index, lp);
        loadingAndRetryLayout.setContentView(oldContent);
        // setup loading,retry,empty layout为了让Loading默认显示，则最后add它
        setupRetryLayout(listener, loadingAndRetryLayout);
        setupEmptyLayout(listener, loadingAndRetryLayout);
        setupLoadingLayout(listener, loadingAndRetryLayout);
        //callback
//        listener.setRetryEvent(loadingAndRetryLayout.getRetryView());
//        listener.setLoadingEvent(loadingAndRetryLayout.getLoadingView());
//        listener.setEmptyEvent(loadingAndRetryLayout.getEmptyView());
        mLoadingAndRetryLayout = loadingAndRetryLayout;
    }

    private void setupEmptyLayout(OnLoadingAndRetryListener listener, LoadingAndRetryLayout loadingAndRetryLayout) {
        if (listener.isSetEmptyLayout()) {
            int layoutId = listener.generateEmptyLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                loadingAndRetryLayout.setEmptyView(layoutId);
            } else {
                loadingAndRetryLayout.setEmptyView(listener.generateEmptyLayout());
            }
        } else {
            if (BASE_EMPTY_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setEmptyView(BASE_EMPTY_LAYOUT_ID);
        }
    }

    private void setupLoadingLayout(OnLoadingAndRetryListener listener, LoadingAndRetryLayout loadingAndRetryLayout) {
        if (listener.isSetLoadingLayout()) {
            int layoutId = listener.generateLoadingLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                loadingAndRetryLayout.setLoadingView(layoutId);
            } else {
                loadingAndRetryLayout.setLoadingView(listener.generateLoadingLayout());
            }
        } else {
            if (BASE_LOADING_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setLoadingView(BASE_LOADING_LAYOUT_ID);
        }
    }

    private void setupRetryLayout(OnLoadingAndRetryListener listener, LoadingAndRetryLayout loadingAndRetryLayout) {
        if (listener.isSetRetryLayout()) {
            int layoutId = listener.generateRetryLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                loadingAndRetryLayout.setLoadingView(layoutId);
            } else {
                loadingAndRetryLayout.setLoadingView(listener.generateRetryLayout());
            }
        } else {
            if (BASE_RETRY_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setRetryView(BASE_RETRY_LAYOUT_ID);
        }
    }

    public static LoadingAndRetryManager generate(Object activityOrFragment, OnLoadingAndRetryListener listener) {
        return new LoadingAndRetryManager(activityOrFragment, listener);
    }

    public void showLoading() {
        showLoading(0);
    }

    public void showLoading(long delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingAndRetryLayout.showLoading();
                mListener.onLoadingEvent(mLoadingAndRetryLayout.getLoadingView());
            }
        }, delay);

    }

    public void showRetry() {
        showRetry(0);
    }

    public void showRetry(long delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingAndRetryLayout.showRetry();
                mListener.onLoadingEndEvent(mLoadingAndRetryLayout.getLoadingView());
                mListener.onRetryEvent(mLoadingAndRetryLayout.getRetryView());
            }
        }, delay);

    }

    public void showContent() {
        showContent(0);
    }

    /**
     * 延时显示内容
     *
     * @param delay
     */
    public void showContent(long delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingAndRetryLayout.showContent();
                mListener.onLoadingEndEvent(mLoadingAndRetryLayout.getLoadingView());
                mListener.onContentEvent(mLoadingAndRetryLayout);
            }
        }, delay);

    }

    public void showEmpty() {
        showEmpty(0);
    }

    public void showEmpty(long delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingAndRetryLayout.showEmpty();
                mListener.onLoadingEndEvent(mLoadingAndRetryLayout.getLoadingView());
                mListener.onEmptyEvent(mLoadingAndRetryLayout.getEmptyView());
            }
        }, delay);

    }


}
