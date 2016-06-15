package com.example.edwardlucci.edwardzhihupaper.presenter;

import android.support.v7.view.menu.BaseMenuPresenter;

import com.example.edwardlucci.edwardzhihupaper.base.BasePresenter;
import com.example.edwardlucci.edwardzhihupaper.bean.CommentResponse;

/**
 * Created by edward on 16/6/15.
 */
public interface CommentPresenter extends BasePresenter{

    CommentResponse loadLongComments();
    CommentResponse loadShortComments();
}
