package com.example.edwardlucci.edwardzhihupaper;

import com.example.core.bean.CommentResponse;
import com.example.edwardlucci.edwardzhihupaper.data.network.ZhihuService;
import com.example.edwardlucci.edwardzhihupaper.util.RxUtil;
import com.orhanobut.logger.Logger;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by edward on 16/6/15.
 */
public class LoaderTest {

    @BeforeClass
    static public void setup() {
        Logger.init();
    }

    @Test
    public void deserilizerTest(){
        ZhihuService.getInstance().getLatestStories().toBlocking().single();
    }

    @Test
    @Ignore
    public void checkLongCommentLoader() {
        Assert.assertEquals(ZhihuService.getInstance()
                .getLongComment(4232852)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .toBlocking().single().getComments().size(), 14);
    }

    @Test
    @Ignore
    public void checkShortCommentLoader() {

        Assert.assertEquals(ZhihuService.getInstance()
                .getShortComment(4232852)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .toBlocking().single().getComments().size(), 20);
    }

    @Test
    public void checkMergedComment() {
        Observable<CommentResponse> commentsObservable =
                Observable.merge(
                        ZhihuService.getInstance().getLongComment(4232852),
                        ZhihuService.getInstance().getShortComment(4232852));

        commentsObservable
                .compose(RxUtil.fromIOtoImmediateThread())
                .toBlocking()
                .forEach(commentResponse ->
                        Assert.assertNotNull(commentResponse.getComments())
        );
    }

}
