package com.example.edwardlucci.edwardzhihupaper.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.base.BaseActivity;
import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.example.edwardlucci.edwardzhihupaper.bean.StoryDetail;
import com.example.edwardlucci.edwardzhihupaper.comment.CommentActivity;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuService;
import com.example.edwardlucci.edwardzhihupaper.util.HtmlUtil;
import com.example.edwardlucci.edwardzhihupaper.util.RxUtil;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.OnClick;

public class StoryActivity extends BaseActivity {

    public static final String STORY_ID = "story_id";

    int storyId;

    StoryDetail storyDetail;

    @Bind(R.id.web_container)
    FrameLayout webContainer;

    @Bind(R.id.collapsingBg)
    ImageView collapsingBgImageView;

    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbar;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    WebView storyWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        storyId = getIntent().getIntExtra(STORY_ID,0);

        if (storyId==0)
            finish();

        storyWebView = new WebView(getApplicationContext());
        webContainer.addView(storyWebView);

        WebSettings webSettings = storyWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        getStoryUrlAndLoadWebview();

    }

    @Override
    public int getLayout() {
        return R.layout.news_activity_layout;
    }

    private void getStoryUrlAndLoadWebview() {
        ZhihuService.getInstance()
                .getStoryDetail(storyId)
                .doOnNext(sDetail -> storyDetail = sDetail)
                .doOnNext(storyDetail -> collapsingToolbar.setTitle(storyDetail.getTitle()))
                .map(storyDetail1 -> HtmlUtil.structHtml(storyDetail1.getBody(), "content_css.css"))
                .compose(RxUtil.fromIOtoMainThread())
                .subscribe(s -> {
                    storyWebView.loadDataWithBaseURL("\"file:///android_asset/\"", s, "text/html", "UTF-8", null);
                    loadBgImage();
                });
    }

    private void loadBgImage() {

        Picasso.with(getActivity())
                .load(storyDetail.getImage())
                .into(collapsingBgImageView);
    }

    @OnClick(R.id.fab)
    public void toCommentActivity() {
        Intent intent = new Intent();
        intent.setClass(StoryActivity.this, CommentActivity.class);
        intent.putExtra(STORY_ID, storyId);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        webContainer.removeView(storyWebView);
        storyWebView = null;
        super.onDestroy();
    }

    public static void startActivity(@NonNull Context context, @NonNull int storyId, @Nullable Pair... sharedView) {
        Intent intent = new Intent(context, StoryActivity.class);
        intent.putExtra(STORY_ID, storyId);
        if (Build.VERSION.SDK_INT > 20) {
            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context, sharedView).toBundle());
        } else {
            context.startActivity(intent);
        }
    }
}
