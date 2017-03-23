package com.example.edwardlucci.edwardzhihupaper.data.network;

import android.content.Context;

import com.example.core.bean.Comment;
import com.example.core.bean.CommentResponse;
import com.example.edwardlucci.edwardzhihupaper.base.AsyncLoader;
import com.example.edwardlucci.edwardzhihupaper.util.RxUtil;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by edward on 16/6/15.
 */
public class CommentLoader extends AsyncLoader<ArrayList<Comment>> {

    int id;

    public CommentLoader(Context context, int id) {
        super(context);
        this.id = id;
    }

    @Override
    public ArrayList<Comment> loadInBackground() {
        Observable<CommentResponse> commentsObservable =
                Observable.merge(
                        ZhihuService.getInstance().getLongComment(id),
                        ZhihuService.getInstance().getShortComment(id));

        Iterable<CommentResponse> commentResponses;

        commentResponses = commentsObservable
                .compose(RxUtil.fromIOtoImmediateThread())
                .toBlocking()
                .toIterable();

        ArrayList<Comment> comments = new ArrayList<>();

        for (CommentResponse commentResponse : commentResponses) {
            comments.addAll(commentResponse.getComments());
        }
        return comments;
    }
}
