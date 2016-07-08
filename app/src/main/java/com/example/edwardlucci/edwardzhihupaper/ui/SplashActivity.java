package com.example.edwardlucci.edwardzhihupaper.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.adapter.ContentAdapter;
import com.example.edwardlucci.edwardzhihupaper.adapter.OnVerticalScrollListener;
import com.example.edwardlucci.edwardzhihupaper.base.BaseActivity;
import com.example.edwardlucci.edwardzhihupaper.bean.DailyStories;
import com.example.edwardlucci.edwardzhihupaper.bean.DateMatcher;
import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.example.edwardlucci.edwardzhihupaper.database.DatabaseHelper;
import com.example.edwardlucci.edwardzhihupaper.database.DateDatabaseContract;
import com.example.edwardlucci.edwardzhihupaper.database.StoryDatabaseContract;
import com.example.edwardlucci.edwardzhihupaper.network.MemoryCache;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuApi;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuService;
import com.example.edwardlucci.edwardzhihupaper.util.DensityUtil;
import com.example.edwardlucci.edwardzhihupaper.util.ItemOffsetDecoration;
import com.example.edwardlucci.edwardzhihupaper.util.RxUtil;

import java.util.ArrayList;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmObject;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new DatabaseHelper(getActivity());
        sqliteDatabase = helper.getWritableDatabase();

//        realm = realm.getDefaultInstance();

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
//                fromRealm(latestDate),
                fromNetwork(latestDate))
                .filter(dailyStories -> dailyStories != null)
                .first()
                .compose(RxUtil.fromIOtoMainThread())
                .compose(bindToLifecycle())
                .doOnNext(dailyStories3 -> {
                    Realm realm1 = Realm.getDefaultInstance();

                    realm1.beginTransaction();
                    MemoryCache.getInstance().putDailyStories(latestDate, dailyStories3);
                    dailyStories3.setRealDate(latestDate);
                    latestDate = dailyStories3.getDate();
                    System.out.println(dailyStories3.toString());
                    DailyStories dailyStoriesInRealm = realm1.where(DailyStories.class).equalTo("date", latestDate).findFirst();
                    if (dailyStoriesInRealm==null){
                        realm1.copyToRealm(dailyStories3);
                    }
                    realm1.commitTransaction();
                    realm1.close();
//                    if (dailyStoriesInRealm == null) {
//                        realm1.executeTransaction(realm -> {
//                                    realm.copyToRealm(dailyStories3);
//                                    realm1.close();
//                                }
//                        );
//                    }
                })
                .doOnTerminate(() -> isLoading = false)
                .subscribe(new Subscriber<DailyStories>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e);
                    }

                    @Override
                    public void onNext(DailyStories dailyStories) {
                        stories.addAll(dailyStories.getStories());
                        contentAdapter.notifyDataSetChanged();
                    }
                });
    }

    private Observable<DailyStories> fromMemoryCache(String date) {
        return Observable.defer(() -> Observable.just(MemoryCache.getInstance().getDailyStories(date)));
    }

    private Observable<DailyStories> fromNetwork(String date) {
        return Observable.defer(() -> zhihuApi.getPastStories(date));
    }

    private Observable<DailyStories> fromRealm(String date) {
        return Observable.defer(() -> {
            Realm realmInNewThread = Realm.getDefaultInstance();
            realmInNewThread.beginTransaction();
            DailyStories dailyStoriesInRealm = realmInNewThread.where(DailyStories.class).equalTo("date", date).findFirst();
            realmInNewThread.commitTransaction();
            realmInNewThread.close();
            return Observable.just(dailyStoriesInRealm);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
