package com.example.edwardlucci.edwardzhihupaper.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Pair;
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

    Story story;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        story = (Story) getIntent().getSerializableExtra("story");

        collapsingToolbar.setTitle(story.getTitle());

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
                .getStoryDetail(story.getId())
                .doOnNext(sDetail -> storyDetail = sDetail)
                .map(storyDetail1 -> HtmlUtil.structHtml(storyDetail1.getBody(), "content_css.css"))
                .compose(RxUtil.fromIOtoMainThread())
                .subscribe(s -> {
                    storyWebView.loadDataWithBaseURL("\"file:///android_asset/\"", s, "text/html", "UTF-8", null);
                    loadBgImage();
                });
    }

    private void loadBgImage() {

        Picasso.with(StoryActivity.this)
                .load(storyDetail.getImage())
                .into(collapsingBgImageView);
    }

    @OnClick(R.id.fab)
    public void toCommentActivity() {
        Intent intent = new Intent();
        intent.setClass(StoryActivity.this, CommentActivity.class);
        intent.putExtra("story_id", story.getId());
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        webContainer.removeView(storyWebView);
        storyWebView = null;
        super.onDestroy();
    }

    public static void toStoryActivity(@NonNull Context context, @NonNull Story story, @Nullable Pair... sharedView) {
        Intent intent = new Intent(context, StoryActivity.class);
        intent.putExtra("story", story);
        if (Build.VERSION.SDK_INT > 20) {
            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context,sharedView).toBundle());
        } else {
            context.startActivity(intent);
        }
    }

//    Intent sendIntent = new Intent();
//    sendIntent.setAction(Intent.ACTION_SEND);
//    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//    sendIntent.setType("text/plain");
//    startActivity(sendIntent);

}
