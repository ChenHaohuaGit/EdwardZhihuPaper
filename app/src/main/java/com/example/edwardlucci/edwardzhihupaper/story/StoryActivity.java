package com.example.edwardlucci.edwardzhihupaper.story;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.base.BaseActivity;
import com.example.edwardlucci.edwardzhihupaper.comment.CommentActivity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.OnClick;

public class StoryActivity extends BaseActivity implements StoryContract.View {

    public static final String STORY_ID = "story_id";

    int storyId;

    StoryPresenter storyPresenter;

    @Bind(R.id.web_container)
    FrameLayout webContainer;

    @Bind(R.id.collapsingBg)
    ImageView collapsingBgImageView;

    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbar;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    BottomSheetBehavior mBottomSheetBehavior;
    @Bind(R.id.bottom_sheet)
    View bottomSheetView;

    WebView storyWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        storyId = getIntent().getIntExtra(STORY_ID, 0);

        if (storyId == 0)
            finish();

        storyWebView = new WebView(getApplicationContext());
        webContainer.addView(storyWebView);

        WebSettings webSettings = storyWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        initBottomSheet();

        storyPresenter = new StoryPresenter(storyId, this);
        storyPresenter.start();
    }

    private void initBottomSheet() {
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
    }

    @Override
    public int getLayout() {
        return R.layout.news_activity_layout;
    }

    @Override
    public void showWebView(String content) {
        storyWebView.loadDataWithBaseURL("\"file:///android_asset/\"", content, "text/html", "UTF-8", null);
    }

    @Override
    public void loadBgImage(String imageUrl) {
        Picasso.with(getActivity())
                .load(imageUrl).fit().centerCrop()
                .into(collapsingBgImageView);
    }

    @Override
    public void setTitle(String title) {
        collapsingToolbar.setTitle(title);
    }

    @Override
    public void share(String shareUrl,String shareTitle) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, shareTitle));
    }


    @OnClick(R.id.fab)
    public void openBottomSheet() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @OnClick(R.id.share_fab)
    public void shareStory() {
        storyPresenter.share();
    }

    @OnClick(R.id.comment_fab)
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

    @Override
    public void setPresenter(StoryContract.Presenter basePresenter) {
    }
}