package com.example.edwardlucci.edwardzhihupaper.network;

import android.content.Context;
import android.content.Loader;

import com.example.edwardlucci.edwardzhihupaper.base.AsyncLoader;
import com.example.edwardlucci.edwardzhihupaper.bean.CommentResponse;

/**
 * Created by edward on 16/6/15.
 */
public class LongCommentLoader extends AsyncLoader<CommentResponse> {

    int id;

    public LongCommentLoader(Context context,int id) {
        super(context);
        this.id = id;
    }

    @Override
    public CommentResponse loadInBackground() {
        return ZhihuService.getInstance()
                .getLongComment(id).toBlocking().single();
    }
}
