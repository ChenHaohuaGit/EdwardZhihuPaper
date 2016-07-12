package com.example.edwardlucci.edwardzhihupaper.ui;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.TestSuiteBuilder;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.adapter.ContentAdapter;
import com.example.edwardlucci.edwardzhihupaper.adapter.OnVerticalScrollListener;
import com.example.edwardlucci.edwardzhihupaper.base.BaseActivity;
import com.example.edwardlucci.edwardzhihupaper.bean.DailyStories;
import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.example.edwardlucci.edwardzhihupaper.database.DatabaseHelper;
import com.example.edwardlucci.edwardzhihupaper.network.MemoryCache;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuApi;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuService;
import com.example.edwardlucci.edwardzhihupaper.util.DensityUtil;
import com.example.edwardlucci.edwardzhihupaper.util.ItemOffsetDecoration;
import com.example.edwardlucci.edwardzhihupaper.util.RxUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by edwardlucci on 16/4/23.
 */
public class SplashActivity extends BaseActivity {

    private boolean isLoading = false;
    private String latestDate;//used to record the latest date

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    ArrayList<Story> stories = new ArrayList<>();

    ContentAdapter contentAdapter;
    ZhihuApi zhihuApi;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    DatabaseHelper helper;
    SQLiteDatabase sqliteDatabase;

    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

        helper = new DatabaseHelper(getActivity());
        sqliteDatabase = helper.getWritableDatabase();

        zhihuApi = ZhihuService.getInstance();

        setupToolbar();
        contentAdapter = new ContentAdapter(SplashActivity.this, stories);

        setupRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(this::loadLatestData);

        loadLatestData();

        setupDrawer();

    }

    private void setupDrawer() {
        getFragmentManager().beginTransaction().replace(R.id.drawer_container, new ThemesCategoryFragment()).commit();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupRecyclerView() {
        //setup recyclerView
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(DensityUtil.dpToPx(10)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
        zhihuApi.getLatestStories()
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
                .filter(dailyStories -> dailyStories != null)
                .first()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .doOnNext(dailyStories3 -> {

                    MemoryCache.getInstance().putDailyStories(latestDate, dailyStories3);

                    realm.beginTransaction();
                    dailyStories3.setRealDate(latestDate);
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
                    stories.addAll(dailyStories.getStories());
                    contentAdapter.notifyDataSetChanged();
                });
    }

    private Observable<DailyStories> fromMemoryCache(String date) {
        return Observable.defer(() ->
                Observable.just(MemoryCache.getInstance()
                        .getDailyStories(date))
                        .compose(RxUtil.fromIOtoMainThread()));
    }

    private Observable<DailyStories> fromNetwork(String date) {
        return Observable.defer(() ->
                zhihuApi.getPastStories(date)
                        .compose(RxUtil.fromIOtoMainThread()));
    }

    private Observable<DailyStories> fromRealm(String date) {
        return Observable.defer(() -> {
            DailyStories dailyStories =
                    realm.where(DailyStories.class)
                            .equalTo("realDate", date)
                            .findFirst();

            return Observable.just(dailyStories)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
        });
    }


//    private Observable<DailyStories> fromRealm(String date) {
//        return Observable.defer(() -> {
//            Realm realmInIOThread = Realm.getDefaultInstance();
//            DailyStories dailyStoriesInRealm = realmInIOThread.where(DailyStories.class).equalTo("realDate", date).findFirst();
//            if (dailyStoriesInRealm != null) {
//                DailyStories dailyStories = new DailyStories();
//                dailyStories.setRealDate(dailyStoriesInRealm.getRealDate());
//                dailyStories.setDate(dailyStoriesInRealm.getDate());
//                List<Story> stories = new ArrayList<>();
//
//                for (Story story : dailyStoriesInRealm.getStories()) {
//                    Story s = new Story();
//                    s.setId(story.getId());
//                    s.setImage(story.getImage());
//                    s.setTitle(story.getTitle());
//                    stories.add(s);
//                }
//                dailyStories.getStories().clear();
//                dailyStories.getStories().addAll(stories);
//
//                realmInIOThread.close();
//                return Observable.just(dailyStories);
//            } else {
//                return Observable.just(null);
//            }
//        });
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
