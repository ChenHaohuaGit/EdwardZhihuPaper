package com.example.edwardlucci.edwardzhihupaper.network;

import android.content.Context;

import com.example.edwardlucci.edwardzhihupaper.base.AsyncLoader;
import com.example.edwardlucci.edwardzhihupaper.bean.CommentResponse;

/**
 * Created by edward on 16/6/15.
 */
public class ShortCommentLoader extends AsyncLoader<CommentResponse> {

    int id;

    public ShortCommentLoader(Context context, int id) {
        super(context);
        this.id = id;
    }

    @Override
    public CommentResponse loadInBackground() {
        return ZhihuService.getInstance()
                .getShortComment(id).toBlocking().single();
    }
}
