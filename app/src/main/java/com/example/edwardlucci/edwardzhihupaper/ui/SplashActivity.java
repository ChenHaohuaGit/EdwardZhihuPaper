package com.example.edwardlucci.edwardzhihupaper.ui;

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
import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuApi;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuService;
import com.example.edwardlucci.edwardzhihupaper.util.DensityUtil;
import com.example.edwardlucci.edwardzhihupaper.util.ItemOffsetDecoration;
import com.example.edwardlucci.edwardzhihupaper.util.RxUtil;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by edwardlucci on 16/4/23.
 * load splashView and data
 */
public class SplashActivity extends BaseActivity {

    private String latestDate;//used to record the latest data

    public static final String DUPLICATE_DATE = "duplicate date";

    public static final String CN_TIMEZONE = "Asia/Hong_Kong";

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    ArrayList<Story> stories = new ArrayList<>();

    ContentAdapter contentAdapter;
    ZhihuApi zhihuApi;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    Calendar mCalendar;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zhihuApi = ZhihuService.getInstance();

        setupToolbar();
        contentAdapter = new ContentAdapter(SplashActivity.this, stories);

        setupRecyclerView();

        moveMCalendarToToday();

        swipeRefreshLayout.setOnRefreshListener(this::loadLatestData);

        loadLatestData();

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
    }

    private void moveMCalendarToToday() {
        mCalendar = Calendar.getInstance(TimeZone.getTimeZone(CN_TIMEZONE));
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
                loadPassedData();
            }

            @Override
            public void onScrolledDown() {
                super.onScrolledDown();
            }

            @Override
            public void onScrolledUp() {
                super.onScrolledUp();
            }
        });
    }

    private void loadSplashView() {

//        zhihuApi.getSplashImg(480, 728)
//                .compose(RxUtil.<SplashResponse>fromIOtoMainThread())
//                .subscribe(imgResponse -> {
//                    Picasso.with(SplashActivity.this)
//                            .load(imgResponse.getImg())
//                            .transform(new BlurTransformation(SplashActivity.this))
//                            .placeholder(R.mipmap.ic_launcher)
//                            .into(splashView);
//                });

    }

    @Override
    public int getLayout() {
        return R.layout.base_drawer_layout;
    }

    private void loadLatestData() {
        stories.clear();
        zhihuApi.getLatestStories()
                .compose(RxUtil.fromIOtoMainThread())
                .doOnNext(latestStories -> latestDate = latestStories.getDate())
                .flatMap(latestStories -> Observable.from(latestStories.getStories()))
                .subscribe(new Subscriber<Story>() {
                    @Override
                    public void onCompleted() {
                        moveMCalendarToToday();
                        contentAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
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

        calendarToStringObservable(mCalendar)
                .doOnNext(mCalendarString -> {
                    if (mCalendarString.equals(latestDate))
                        try {
                            throw new Exception(DUPLICATE_DATE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                })
                .flatMap(s -> zhihuApi.getPastStories(s))
                .compose(RxUtil.fromIOtoMainThread())
                .flatMap(latestStories -> Observable.from(latestStories.getStories()))
                .subscribe(new Subscriber<Story>() {
                    @Override
                    public void onCompleted() {
                        mCalendar.add(Calendar.DATE, -1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage().equals(DUPLICATE_DATE)) {
                            mCalendar.add(Calendar.DATE, -1);
                            loadPassedData();
                        }
                    }

                    @Override
                    public void onNext(Story story) {
                        stories.add(story);
                        contentAdapter.notifyItemInserted(stories.size() - 1);
                    }
                });
    }

    private String calendarToString(Calendar mCalendar, SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(mCalendar.getTime());
    }

    private Observable<String> calendarToStringObservable(Calendar mCalendar) {
        return Observable.defer(() -> Observable.just(calendarToString(mCalendar, sdf)));
    }
}
