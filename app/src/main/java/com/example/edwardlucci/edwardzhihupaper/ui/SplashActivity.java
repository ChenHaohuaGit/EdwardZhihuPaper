package com.example.edwardlucci.edwardzhihupaper.ui;

import android.content.ContentValues;
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
import com.example.edwardlucci.edwardzhihupaper.database.StoryDatabaseContract;
import com.example.edwardlucci.edwardzhihupaper.database.StoryDatabaseHelper;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuApi;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuService;
import com.example.edwardlucci.edwardzhihupaper.util.DensityUtil;
import com.example.edwardlucci.edwardzhihupaper.util.ItemOffsetDecoration;
import com.example.edwardlucci.edwardzhihupaper.util.RxUtil;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by edwardlucci on 16/4/23.
 * load splashView and data
 */
public class SplashActivity extends BaseActivity{


    private boolean isLoading = false;
    private String latestDate;//used to record the latest data

    public static final String DUPLICATE_DATE = "duplicate date";
    public static final String CN_TIMEZONE = "Asia/Hong_Kong";
    public static final String THEME_ID = "theme id";

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    ArrayList<Story> stories = new ArrayList<>();

    ContentAdapter contentAdapter;
    ZhihuApi zhihuApi;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (getActionBar()!=null){
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

        zhihuApi.getPastStories(latestDate)
                .compose(RxUtil.fromIOtoMainThread())
                .compose(bindToLifecycle())
                .doOnNext(latestStories -> latestDate = latestStories.getDate())
                .doOnNext(dailyStories -> putDailyStoriesInputDatabase(dailyStories))
                .flatMap(latestStories -> Observable.from(latestStories.getStories()))
                .subscribe(new Subscriber<Story>() {
                    @Override
                    public void onCompleted() {
                        isLoading = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Story story) {
                        stories.add(story);
                        contentAdapter.notifyItemInserted(stories.size() - 1);
                    }
                });
    }

    private void putDailyStoriesInputDatabase(DailyStories dailyStories) {
        StoryDatabaseHelper helper = new StoryDatabaseHelper(getActivity());
        SQLiteDatabase sqliteDatabase = helper.getWritableDatabase();

        for (Story story : dailyStories.getStories()) {
            sqliteDatabase.insertWithOnConflict(StoryDatabaseContract.StoryTable.TABLE_NAME,null,story.story2contentvalues(dailyStories.getDate()),SQLiteDatabase.CONFLICT_REPLACE);
        }

    }
}
