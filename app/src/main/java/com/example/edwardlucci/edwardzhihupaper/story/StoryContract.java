package com.example.edwardlucci.edwardzhihupaper.story;

import com.example.core.base.BasePresenter;
import com.example.core.base.BaseView;

/**
 * Created by edward on 16/8/1.
 */
public interface StoryContract{

    interface Presenter extends BasePresenter {
        void loadWebContent();

        void share();
    }

    interface View extends BaseView<Presenter> {
        void showWebView(String content);

        void loadBgImage(String imageUrl);

        void setTitle(String title);

        void share(String shareUrl,String title);
    }
}
