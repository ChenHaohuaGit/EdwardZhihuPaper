package com.example.edwardlucci.edwardzhihupaper.story;

import com.example.edwardlucci.edwardzhihupaper.bean.StoryDetail;
import com.example.edwardlucci.edwardzhihupaper.data.network.ZhihuApi;
import com.example.edwardlucci.edwardzhihupaper.data.network.ZhihuService;
import com.example.edwardlucci.edwardzhihupaper.util.HtmlUtil;
import com.example.edwardlucci.edwardzhihupaper.util.Preconditions;
import com.example.edwardlucci.edwardzhihupaper.util.RxUtil;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by edward on 16/8/1.
 */
public class StoryPresenter implements StoryContract.Presenter {

    int storyId;
    StoryDetail storyDetail;

    StoryContract.View mView;

    ZhihuApi zhihuApi;

    @Inject
    public StoryPresenter(int storyId, StoryContract.View mView) {
        this.storyId = storyId;
        this.mView = mView;
        zhihuApi = ZhihuService.getInstance();
        mView.setPresenter(this);
    }

//    @Inject
//    void setupListeners() {
//        mView.setPresenter(this);
//    }

    @Override
    public void loadWebContent() {
        zhihuApi.getStoryDetail(storyId)
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

    @Override
    public void destroy() {

    }
}
