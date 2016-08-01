package com.example.edwardlucci.edwardzhihupaper.story;

import com.example.edwardlucci.edwardzhihupaper.base.BasePresenter;
import com.example.edwardlucci.edwardzhihupaper.base.BaseView;

/**
 * Created by edward on 16/8/1.
 */
public interface StoryContract{

    interface Presenter extends BasePresenter{
        void loadWebContent();
    }

    interface View extends BaseView<Presenter>{
        void showWebView(String content);

        void showBottomSheet();

        void loadBgImage();
    }
}
