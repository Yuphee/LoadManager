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
public class PrePageManager {
    public static final int NO_LAYOUT_ID = 0;
    public static int BASE_LOADING_LAYOUT_ID = NO_LAYOUT_ID;
    public static int BASE_RETRY_LAYOUT_ID = NO_LAYOUT_ID;
    public static int BASE_EMPTY_LAYOUT_ID = NO_LAYOUT_ID;
    private OnPrePageListener mListener;
    private Context mContext;

    public PreLayout mPreLayout;


    public OnPrePageListener DEFAULT_LISTENER = new OnPrePageListener() {
        @Override
        public void onRetryStart(View retryView) {

        }
    };

    public static void initManagerLayout(int loading_layout, int retry_layout, int empty_layout){
        BASE_LOADING_LAYOUT_ID = loading_layout;
        BASE_RETRY_LAYOUT_ID = retry_layout;
        BASE_EMPTY_LAYOUT_ID = empty_layout;
    }


    public PrePageManager(Object activityOrFragmentOrView, OnPrePageListener listener) {
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
        PreLayout preLayout = new PreLayout(mContext);

        ViewGroup.LayoutParams lp = oldContent.getLayoutParams();
        contentParent.addView(preLayout, index, lp);
        preLayout.setContentView(oldContent);
        // setup loading,retry,empty layout为了让Loading默认显示，则最后add它
        setupRetryLayout(listener, preLayout);
        setupEmptyLayout(listener, preLayout);
        setupLoadingLayout(listener, preLayout);
        //callback
//        listener.setRetryEvent(loadingAndRetryLayout.getRetryView());
//        listener.setLoadingEvent(loadingAndRetryLayout.getLoadingView());
//        listener.setEmptyEvent(loadingAndRetryLayout.getEmptyView());
        mPreLayout = preLayout;
    }

    private void setupEmptyLayout(OnPrePageListener listener, PreLayout preLayout) {
        if (listener.isSetEmptyLayout()) {
            int layoutId = listener.generateEmptyLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                preLayout.setEmptyView(layoutId);
            } else {
                preLayout.setEmptyView(listener.generateEmptyLayout());
            }
        } else {
            if (BASE_EMPTY_LAYOUT_ID != NO_LAYOUT_ID)
                preLayout.setEmptyView(BASE_EMPTY_LAYOUT_ID);
        }
    }

    private void setupLoadingLayout(OnPrePageListener listener, PreLayout preLayout) {
        if (listener.isSetLoadingLayout()) {
            int layoutId = listener.generateLoadingLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                preLayout.setLoadingView(layoutId);
            } else {
                preLayout.setLoadingView(listener.generateLoadingLayout());
            }
        } else {
            if (BASE_LOADING_LAYOUT_ID != NO_LAYOUT_ID)
                preLayout.setLoadingView(BASE_LOADING_LAYOUT_ID);
        }
    }

    private void setupRetryLayout(OnPrePageListener listener, PreLayout preLayout) {
        if (listener.isSetRetryLayout()) {
            int layoutId = listener.generateRetryLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                preLayout.setLoadingView(layoutId);
            } else {
                preLayout.setLoadingView(listener.generateRetryLayout());
            }
        } else {
            if (BASE_RETRY_LAYOUT_ID != NO_LAYOUT_ID)
                preLayout.setRetryView(BASE_RETRY_LAYOUT_ID);
        }
    }

    public static PrePageManager generate(Object activityOrFragment, OnPrePageListener listener) {
        return new PrePageManager(activityOrFragment, listener);
    }

    public void showLoading() {
        showLoading(0);
    }

    public void showLoading(long delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPreLayout.showLoading();
                mListener.onLoadingStart(mPreLayout.getLoadingView());
            }
        }, delay);

    }

    /**
     * default
     */
    public void showRetry() {
        showRetry(0);
    }

    /**
     *
     * @param delay
     */
    public void showRetry(long delay) {
        showRetry(delay,null, PreLayout.DEFAULT_DURATION);
    }

    /**
     *
     * @param listener anim
     */
    public void showRetry(final PreLayout.AnimatorsListener listener) {
        showRetry(0,listener, PreLayout.DEFAULT_DURATION);

    }

    /**
     *
     * @param listener
     * @param duration
     */
    public void showRetry(final PreLayout.AnimatorsListener listener, long duration) {
        showRetry(0,listener,duration);

    }

    /**
     * 设置重载页
     * @param delay
     * @param listener anim
     */
    public void showRetry(long delay, final PreLayout.AnimatorsListener listener, final long duration) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPreLayout.showRetry(listener,duration);
                mListener.onLoadingEnd(mPreLayout.getLoadingView());
                mListener.onRetryStart(mPreLayout.getRetryView());
            }
        }, delay);
    }


    /**
     * default
     */
    public void showContent() {
        showContent(0);
    }

    /**
     * 延时显示内容
     *
     * @param delay
     */
    public void showContent(long delay) {
        showContent(delay,null, PreLayout.DEFAULT_DURATION);
    }

    /**
     *
     * @param listener anim
     */
    public void showContent(final PreLayout.AnimatorsListener listener) {
        showContent(0,listener, PreLayout.DEFAULT_DURATION);

    }

    /**
     *
     * @param listener
     * @param duration
     */
    public void showContent(final PreLayout.AnimatorsListener listener, long duration) {
        showContent(0,listener,duration);

    }

    /**
     *设置内容页
     * @param delay
     * @param listener anim
     */
    public void showContent(long delay, final PreLayout.AnimatorsListener listener, final long duration) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPreLayout.showContent(listener,duration);
                mListener.onLoadingEnd(mPreLayout.getLoadingView());
                mListener.onContentStart(mPreLayout);
            }
        }, delay);

    }

    /**
     * default
     */
    public void showEmpty() {
        showEmpty(0);
    }

    /**
     * 设置空白页
     * @param delay
     */
    public void showEmpty(long delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPreLayout.showEmpty();
                mListener.onLoadingEnd(mPreLayout.getLoadingView());
                mListener.onEmptyStart(mPreLayout.getEmptyView());
            }
        }, delay);

    }


}
