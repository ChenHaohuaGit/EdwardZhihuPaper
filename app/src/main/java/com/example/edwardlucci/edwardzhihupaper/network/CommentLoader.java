package com.example.edwardlucci.edwardzhihupaper.network;

import android.content.Context;

import com.example.edwardlucci.edwardzhihupaper.base.AsyncLoader;
import com.example.edwardlucci.edwardzhihupaper.bean.CommentResponse;
import com.example.edwardlucci.edwardzhihupaper.util.RxUtil;

import java.util.Iterator;

import rx.Observable;

/**
 * Created by edward on 16/6/15.
 */
public class CommentLoader extends AsyncLoader<Iterable<CommentResponse>> {

    int id;

    public CommentLoader(Context context, int id) {
        super(context);
        this.id = id;
    }

    @Override
    public Iterable<CommentResponse> loadInBackground() {
        Observable<CommentResponse> commentsObservable =
                Observable.merge(
                        ZhihuService.getInstance().getLongComment(id),
                        ZhihuService.getInstance().getShortComment(id));

        return commentsObservable
                .compose(RxUtil.fromIOtoImmediateThread())
                .toBlocking()
                .toIterable();
    }
}
