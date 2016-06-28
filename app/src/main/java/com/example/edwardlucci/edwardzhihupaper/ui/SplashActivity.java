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
import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.example.edwardlucci.edwardzhihupaper.database.DateDatabaseContract;
import com.example.edwardlucci.edwardzhihupaper.database.StoryDatabaseContract;
import com.example.edwardlucci.edwardzhihupaper.database.DatabaseHelper;
import com.example.edwardlucci.edwardzhihupaper.network.MemoryCache;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuApi;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuService;
import com.example.edwardlucci.edwardzhihupaper.util.DensityUtil;
import com.example.edwardlucci.edwardzhihupaper.util.ItemOffsetDecoration;
import com.example.edwardlucci.edwardzhihupaper.util.RxUtil;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by edwardlucci on 16/4/23.
 * load splashView and data
 */
public class SplashActivity extends BaseActivity {


    private boolean isLoading = false;
    private String latestDate;//used to record the latest data

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
                .doOnNext(latestStories -> latestDate = latestStories.getDate())
                .flatMap(latestStories -> Observable.from(latestStories.getStories()))
                .subscribe(new Subscriber<Story>() {
                    @Override
                    public void onCompleted() {
                        contentAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        isLoading = false;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Story story) {
                        stories.add(story);
                    }
                });
    }

    private void loadPassedData() {
        isLoading = true;

        Observable.concat(
                Observable.just(MemoryCache.getInstance().getDailyStories(latestDate)),
                Observable.just(getDailyStoriesFromDatabase(latestDate)),
                zhihuApi.getPastStories(latestDate))
                .filter(dailyStories -> dailyStories != null)
                .first()
                .compose(RxUtil.fromIOtoMainThread())
                .compose(bindToLifecycle())
                .doOnNext(dailyStories1 -> MemoryCache.getInstance().putDailyStories(latestDate, dailyStories1))
                .doOnNext(dailyStories -> putDailyStoriesIntoDatabase(latestDate,dailyStories))
                .doOnNext(dailyStories2 -> latestDate = dailyStories2.getDate())
                .doOnTerminate(() -> isLoading = false)
                .subscribe(new Subscriber<DailyStories>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DailyStories dailyStories) {
                        stories.addAll(dailyStories.getStories());
                        contentAdapter.notifyDataSetChanged();
                    }
                });
    }

    private DailyStories getDailyStoriesFromDatabase(String date) {
        Cursor dateCursor = sqliteDatabase.query(DateDatabaseContract.DateTable.TABLE_NAME,
                DateDatabaseContract.projection,
                DateDatabaseContract.DateTable.COLUMN_NAME_DATE + "=?",
                new String[]{date},
                null, null, null, null);
        Cursor cursor = sqliteDatabase.query(StoryDatabaseContract.StoryTable.TABLE_NAME,
                StoryDatabaseContract.projection,
                StoryDatabaseContract.StoryTable.COLUMN_NAME_DATE + "=?",
                new String[]{date},
                null, null, null, null);
        DailyStories dailyStories = new DailyStories();
        if (dateCursor.moveToNext()){
            dailyStories.setDate(dateCursor.getString(dateCursor.getColumnIndex(DateDatabaseContract.DateTable.COLUMN_NAME_PREVIOUS_DATE)));
        }
        while (cursor.moveToNext()) {
            Story story = new Story();
            story.setId(cursor.getInt(cursor.getColumnIndex(StoryDatabaseContract.StoryTable.COLUMN_NAME_ID)));
            story.setTitle(cursor.getString(cursor.getColumnIndex(StoryDatabaseContract.StoryTable.COLUMN_NAME_TITLE)));
            story.setImage(cursor.getString(cursor.getColumnIndex(StoryDatabaseContract.StoryTable.COLUMN_NAME_IMAGES)));
            dailyStories.getStories().add(story);
        }
        dateCursor.close();
        cursor.close();
        if (dailyStories.getStories().size() > 0) {
            return dailyStories;
        } else {
            return null;
        }
    }


    private void putDailyStoriesIntoDatabase(String currentDate,DailyStories dailyStories) {
        Observable.just(dailyStories)
                .doOnNext(dailyStories1 ->
                        sqliteDatabase.insertWithOnConflict(
                                DateDatabaseContract.DateTable.TABLE_NAME, null,
                                dailyStories1.dailyStoriesDate2ContentValues(currentDate),
                                SQLiteDatabase.CONFLICT_REPLACE))
                .flatMap(dailyStories1 -> Observable.from(dailyStories1.getStories()))
                .observeOn(Schedulers.io())
                .subscribe(story ->
                        sqliteDatabase.insertWithOnConflict(
                                StoryDatabaseContract.StoryTable.TABLE_NAME, null,
                                story.story2contentvalues(currentDate),
                                SQLiteDatabase.CONFLICT_REPLACE)
                );
    }
}
