package com.example.edwardlucci.edwardzhihupaper.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Window;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Objects;

import butterknife.ButterKnife;

/**
 * Created by edwardlucci on 16/5/18.
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTransitionAnimation();
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
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }
    }
}
