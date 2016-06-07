package com.example.edwardlucci.edwardzhihupaper.model;

import com.example.edwardlucci.edwardzhihupaper.bean.LatestStories;
import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuService;
import com.example.edwardlucci.edwardzhihupaper.presenter.LoadLatestDataPresenter;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by edwardlucci on 16/5/9.
 */
public class LoadLatestDataModel {
    LoadLatestDataPresenter loadLatestDataPresenter;

    public LoadLatestDataModel(LoadLatestDataPresenter loadLatestDataPresenter) {
        this.loadLatestDataPresenter = loadLatestDataPresenter;
    }

    public void loadLatestData(Subscriber<LatestStories> subscriber){
        ZhihuService.getInstance()
                .getLatestStories()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
