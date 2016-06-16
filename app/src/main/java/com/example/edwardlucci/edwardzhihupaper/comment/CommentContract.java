package com.example.edwardlucci.edwardzhihupaper.comment;

import com.example.edwardlucci.edwardzhihupaper.base.BasePresenter;
import com.example.edwardlucci.edwardzhihupaper.base.BaseView;
import com.example.edwardlucci.edwardzhihupaper.bean.Comment;
import com.example.edwardlucci.edwardzhihupaper.bean.CommentResponse;

import java.util.ArrayList;

/**
 * Created by edward on 16/6/16.
 */
public class CommentContract {
    interface Presenter extends BasePresenter{
        void loadData(ArrayList<Comment> comments);
    }

    interface View extends BaseView<Presenter>{
        void showData();

        void commentDialog();
    }
}
