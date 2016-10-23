package com.example.edwardlucci.edwardzhihupaper.comment;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import com.example.edwardlucci.edwardzhihupaper.bean.Comment;
import com.example.edwardlucci.edwardzhihupaper.data.network.CommentLoader;

import java.util.ArrayList;


/**
 * Created by edward on 16/6/18.
 */
public class CommentPresenter implements CommentContract.Presenter, LoaderManager.LoaderCallbacks<ArrayList<Comment>> {

    public static final int COMMENT_LOADER_ID = 0;

    int id;

    CommentContract.View mView;

    private LoaderManager mLoaderManager;

    private CommentLoader mCommentLoader;

    public CommentPresenter(int id, CommentContract.View mView, LoaderManager mLoaderManager, CommentLoader commentLoader) {
        this.id = id;
        this.mView = mView;
        this.mLoaderManager = mLoaderManager;
        this.mCommentLoader = commentLoader;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadData();
    }

    @Override
    public void destroy() {
        mLoaderManager.destroyLoader(COMMENT_LOADER_ID);
    }

    @Override
    public void loadData() {
        mLoaderManager.initLoader(COMMENT_LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<Comment>> onCreateLoader(int id, Bundle args) {
        return mCommentLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Comment>> loader, ArrayList<Comment> data) {
        mView.showData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Comment>> loader) {

    }

}
