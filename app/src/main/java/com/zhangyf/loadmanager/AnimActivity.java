package com.zhangyf.loadmanager;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;

import com.zhangyf.loadmanager.base.BaseActivity;

import java.util.Random;

/**
 * Created by zhangyf on 2016/12/23.
 */

public class AnimActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setLayoutId(R.layout.activity_anim);
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onInit(Bundle savedInstanceState) {
        Fade fade = new Fade();
        fade.setInterpolator(new DecelerateInterpolator());
        fade.setDuration(500);//动画持续时间
        getWindow().setEnterTransition(fade);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fm_content);

        if (fragment == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.fm_content, new NormalFragment()).commit();
        }
    }

}
