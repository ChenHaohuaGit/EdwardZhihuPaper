package com.example.edwardlucci.edwardzhihupaper.story;

import dagger.Module;

/**
 * Created by edward on 16/8/9.
 */
@Module
public class StoryPresenterModule {

    private final StoryContract.View mView;

    public StoryPresenterModule(StoryContract.View mView) {
        this.mView = mView;
    }

    public StoryContract.View provideStoryContractView() {
        return mView;
    }
}
