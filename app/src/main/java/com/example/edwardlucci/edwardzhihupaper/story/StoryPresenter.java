package com.example.edwardlucci.edwardzhihupaper.story;

import android.view.View;

import com.example.edwardlucci.edwardzhihupaper.bean.StoryDetail;

/**
 * Created by edward on 16/8/1.
 */
public class StoryPresenter implements StoryContract.Presenter {

    int storyId;
    StoryDetail storyDetail;

    StoryContract.View mView;

    public StoryPresenter(int storyId, StoryDetail storyDetail, StoryContract.View mView) {
        this.storyId = storyId;
        this.storyDetail = storyDetail;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void loadWebContent() {

    }

    @Override
    public void start() {

    }
}
