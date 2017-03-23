package com.example.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.RxFragment;

import butterknife.ButterKnife;

/**
 * Created by edwardlucci on 16/5/22.
 */
public abstract class BaseFragment extends RxFragment{

    View layoutView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutView = inflater.inflate(getLayout(),container,false);
        ButterKnife.bind(this,layoutView);
        init();
        return layoutView;
    }

    protected abstract void init();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected abstract int getLayout();
}
