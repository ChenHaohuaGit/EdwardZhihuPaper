package com.example.edwardlucci.edwardzhihupaper.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.adapter.TopicPagerAdapter;
import com.example.edwardlucci.edwardzhihupaper.bean.Other;
import com.example.edwardlucci.edwardzhihupaper.bean.Themes;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuApi;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuService;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by edwardlucci on 16/5/6.
 */
public class TopicActivity extends AppCompatActivity{

    @Bind(R.id.viewpager) ViewPager viewPager;

    TopicPagerAdapter pagerAdapter;

    ZhihuApi zhihuApi;

    ArrayList<Other> others = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_activity_layout);

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        zhihuApi = ZhihuService.getInstance();

        ArrayList<View> views = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            TextView textView = new TextView(TopicActivity.this);
            textView.setText("haha");
            views.add(textView);
        }


        pagerAdapter = new TopicPagerAdapter(TopicActivity.this,views);

        loadThemes();

    }

    void loadThemes(){

        others.clear();

        zhihuApi.getThemes()
                .flatMap(new Func1<Themes, Observable<Other>>() {
                    @Override
                    public Observable<Other> call(Themes themes) {
                        return Observable.from(themes.getOthers());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Other>() {
                    @Override
                    public void onCompleted() {
                        viewPager.setAdapter(pagerAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Other other) {
                        others.add(other);
                    }
                });

    }
}
