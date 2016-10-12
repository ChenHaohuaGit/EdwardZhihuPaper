package com.example.edwardlucci.edwardzhihupaper.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.adapter.ContentAdapter;
import com.example.edwardlucci.edwardzhihupaper.adapter.OnVerticalScrollListener;
import com.example.edwardlucci.edwardzhihupaper.base.BaseActivity;
import com.example.edwardlucci.edwardzhihupaper.base.MyApp;
import com.example.edwardlucci.edwardzhihupaper.bean.DailyStories;
import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.example.edwardlucci.edwardzhihupaper.data.DataManager;
import com.example.edwardlucci.edwardzhihupaper.util.DensityUtil;
import com.example.edwardlucci.edwardzhihupaper.util.ItemOffsetDecoration;
import com.example.edwardlucci.edwardzhihupaper.util.RxUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import io.realm.Realm;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by edwardlucci on 16/4/23.
 */
public class MainActivity extends BaseActivity {

    private boolean isLoading = false;
    private String latestDate;//used to record the latest date

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    ArrayList<Story> stories = new ArrayList<>();

    ContentAdapter contentAdapter;

    @Inject
    DataManager dataManager;

    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApp) getApplication()).getDataComponent().inject(this);

        realm = Realm.getDefaultInstance();

        checkDeeplinkLaunch();

        setupToolbar();
        contentAdapter = new ContentAdapter(getActivity(), stories);

        setupRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(this::loadLatestData);

        loadLatestData();

        setupDrawer();
    }

    private void checkDeeplinkLaunch() {
        Intent intent = getIntent();
        if (intent != null) {
            Uri uri = intent.getData();
            if (uri != null) {
                Log.i("slack", uri.toString());
                String host = uri.getHost();
                Log.i("slack", host);
            }
        }
    }

    private void setupDrawer() {
        getFragmentManager().beginTransaction().replace(R.id.drawer_container, new ThemesCategoryFragment()).commit();
    }

    private void setupToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setTitleTextColor(Color.WHITE);

            if (getActionBar() != null) {
                getActionBar().setDisplayHomeAsUpEnabled(true);
                getActionBar().setDisplayShowHomeEnabled(true);
            }
        }
    }

    private void setupRecyclerView() {
        //setup recyclerView
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(DensityUtil.dpToPx(10)));
        recyclerView.setItemAnimator(new SlideInDownAnimator());
        recyclerView.setAdapter(contentAdapter);
        recyclerView.addOnScrollListener(new OnVerticalScrollListener() {
            @Override
            public void onScrolledToBottom() {
                super.onScrolledToBottom();
                if (!isLoading) loadPassedData();
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.base_drawer_layout;
    }

    private void loadLatestData() {
        isLoading = true;
        stories.clear();
        dataManager.getZhihuApi().getLatestStories()
                .compose(RxUtil.fromIOtoMainThread())
                .doOnTerminate(() -> isLoading = false)
                .subscribe(dailyStories -> {
                    stories.addAll(dailyStories.getStories());
                    latestDate = dailyStories.getDate();
                    contentAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                });
    }

    private void loadPassedData() {
        isLoading = true;

        Observable.concat(
                fromMemoryCache(latestDate),
                fromRealm(latestDate),
                fromNetwork(latestDate))
                .first(dailyStories -> dailyStories != null && dailyStories.isLoaded())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .doOnNext(dailyStories3 -> {

                    dataManager.getMemoryCache().putDailyStories(latestDate, dailyStories3);

                    realm.beginTransaction();
                    Logger.i(dailyStories3.toString());
                    DailyStories dailyStoriesInRealm = realm.where(DailyStories.class).equalTo("realDate", latestDate).findFirst();

                    if (dailyStoriesInRealm == null) {
                        realm.copyToRealm(dailyStories3);
                    }

                    latestDate = dailyStories3.getDate();
                    realm.commitTransaction();
                })
                .doOnTerminate(() -> isLoading = false)
                .subscribe(dailyStories -> {
                    int positionStarted = stories.size();
                    stories.addAll(dailyStories.getStories());
                    contentAdapter.notifyItemRangeInserted(positionStarted, dailyStories.getStories().size());
                });
    }

    private Observable<DailyStories> fromMemoryCache(String date) {
        return Observable.create((Observable.OnSubscribe<DailyStories>) subscriber -> {
            DailyStories dailyStories = dataManager.getMemoryCache().getDailyStories(date);
            if (dailyStories != null) {
                dailyStories.setSource(DailyStories.SOURCE_TYPE.MEMORY);
                subscriber.onNext(dailyStories);
            } else {
                subscriber.onCompleted();
            }

        }).compose(RxUtil.fromIOtoMainThread());
    }

    private Observable<DailyStories> fromNetwork(String date) {
        return Observable.defer(() ->
                dataManager.getZhihuApi().getPastStories(date)
                        .compose(RxUtil.filterNullPointer())
                        .flatMap(dailyStories -> {
                            dailyStories.setSource(DailyStories.SOURCE_TYPE.NETWORK);
                            dailyStories.setRealDate(latestDate);
                            return Observable.just(dailyStories);
                        })
                        .compose(RxUtil.fromIOtoMainThread()));
    }


    private Observable<DailyStories> fromRealm(String date) {

        return Observable.create(new Observable.OnSubscribe<DailyStories>() {
            @Override
            public void call(Subscriber<? super DailyStories> subscriber) {
                DailyStories dailyStories =
                        realm.where(DailyStories.class).equalTo("realDate", date).findFirst();

                if (dailyStories != null) {
                    dailyStories.setSource(DailyStories.SOURCE_TYPE.DATABASE);
                    subscriber.onNext(dailyStories);
                } else {
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }
}
