package com.example.edwardlucci.edwardzhihupaper.comment;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import com.example.edwardlucci.edwardzhihupaper.bean.Comment;
import com.example.edwardlucci.edwardzhihupaper.bean.CommentResponse;
import com.example.edwardlucci.edwardzhihupaper.network.CommentLoader;

import java.util.ArrayList;


/**
 * Created by edward on 16/6/18.
 */
public class CommentPresenter implements CommentContract.Presenter, LoaderManager.LoaderCallbacks<Iterable<CommentResponse>> {

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
    public void loadData() {
        mLoaderManager.initLoader(0, null, this);
    }

    @Override
    public Loader<Iterable<CommentResponse>> onCreateLoader(int id, Bundle args) {
        return mCommentLoader;
    }

    @Override
    public void onLoadFinished(Loader<Iterable<CommentResponse>> loader, Iterable<CommentResponse> data) {
        ArrayList<Comment> comments = new ArrayList<>();
        for (CommentResponse commentResponse : data) {
            comments.addAll(commentResponse.getComments());
        }
        mView.showData(comments);
    }

    @Override
    public void onLoaderReset(Loader<Iterable<CommentResponse>> loader) {

    }
}
