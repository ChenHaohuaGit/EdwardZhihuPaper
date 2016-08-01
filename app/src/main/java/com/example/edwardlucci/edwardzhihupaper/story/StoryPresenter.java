package com.example.edwardlucci.edwardzhihupaper.story;

import android.content.Intent;
import android.view.View;

import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.example.edwardlucci.edwardzhihupaper.bean.StoryDetail;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuService;
import com.example.edwardlucci.edwardzhihupaper.util.HtmlUtil;
import com.example.edwardlucci.edwardzhihupaper.util.Preconditions;
import com.example.edwardlucci.edwardzhihupaper.util.RxUtil;
import com.trello.rxlifecycle.RxLifecycle;

import rx.Subscriber;

/**
 * Created by edward on 16/8/1.
 */
public class StoryPresenter implements StoryContract.Presenter {

    int storyId;
    StoryDetail storyDetail;

    StoryContract.View mView;

    public StoryPresenter(int storyId, StoryContract.View mView) {
        this.storyId = storyId;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void loadWebContent() {
        ZhihuService.getInstance()
                .getStoryDetail(storyId)
                .doOnNext(sDetail -> storyDetail = sDetail)
                .compose(RxUtil.fromIOtoMainThread())
                .subscribe(new Subscriber<StoryDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(StoryDetail storyDetail) {
                        String webContent = HtmlUtil.structHtml(storyDetail.getBody(), "content_css.css");
                        mView.showWebView(webContent);
                        mView.loadBgImage(storyDetail.getImage());
                        mView.setTitle(storyDetail.getTitle());
                    }
                });

    }

    @Override
    public void share() {
        Preconditions.checkNotNull(storyDetail);
        mView.share(storyDetail.getShareUrl(), storyDetail.getTitle());
    }

    @Override
    public void start() {
        loadWebContent();
    }
}
