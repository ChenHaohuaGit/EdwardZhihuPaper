package com.example.edwardlucci.edwardzhihupaper.story;

import com.example.core.base.BaseCommonPresenter;
import com.example.core.bean.StoryDetail;
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
public class StoryPresenter extends BaseCommonPresenter<StoryContract.View> implements StoryContract.Presenter {

    int storyId;
    StoryDetail storyDetail;

    ZhihuApi zhihuApi;

    @Inject
    StoryPresenter(int storyId, StoryContract.View mView) {
        super(mView);
        this.storyId = storyId;
        this.mView = mView;
        zhihuApi = ZhihuService.getInstance();
    }

    @Override
    public void loadWebContent() {
        addToCompositeSubscription(
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
                        }));
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
        super.destroy();
    }
}
