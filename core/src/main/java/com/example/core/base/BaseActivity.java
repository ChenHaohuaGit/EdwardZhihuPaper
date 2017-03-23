package com.example.core.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.transition.Fade;
import android.view.Window;
import android.view.WindowManager;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by edwardlucci on 16/5/18.
 */
public abstract class BaseActivity extends RxAppCompatActivity {
//
//    @Nullable
//    @Bind(R.id.toolbar)
//    public android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTransitionAnimation();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = this.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    public abstract
    @LayoutRes
    int getLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void setupTransitionAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Fade fade = new Fade();
            fade.setDuration(500);
//            getWindow().setEnterTransition(fade);
//            getWindow().setReenterTransition(fade);
            getWindow().setReturnTransition(fade);
            getWindow().setExitTransition(fade);
        }
    }

    protected Activity getActivity(){return this;}
}
