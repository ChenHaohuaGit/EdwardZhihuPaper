package com.example.edwardlucci.edwardzhihupaper.comment;

import com.example.edwardlucci.edwardzhihupaper.base.BasePresenter;
import com.example.edwardlucci.edwardzhihupaper.base.BaseView;
import com.example.edwardlucci.edwardzhihupaper.bean.Comment;

import java.util.ArrayList;

/**
 * Created by edward on 16/6/16.
 */
public class CommentContract {
    interface Presenter extends BasePresenter{
        void loadData();
    }

    interface View extends BaseView<Presenter>{
        void showData(ArrayList<Comment> comments);
    }
}
