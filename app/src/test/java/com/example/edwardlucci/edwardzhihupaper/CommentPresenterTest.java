package com.example.edwardlucci.edwardzhihupaper;

import android.app.LoaderManager;
import android.os.Bundle;

import com.example.edwardlucci.edwardzhihupaper.bean.Comment;
import com.example.edwardlucci.edwardzhihupaper.comment.CommentContract;
import com.example.edwardlucci.edwardzhihupaper.comment.CommentPresenter;
import com.example.edwardlucci.edwardzhihupaper.data.network.CommentLoader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

/**
 * Created by edward on 16/6/19.
 */
public class CommentPresenterTest {

    @Mock
    CommentContract.View mockView;

    @Mock
    LoaderManager mockLoaderManager;

    @Mock
    CommentLoader mockLoader;

    CommentPresenter commentPresenter;

    Comment comment = new Comment();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        commentPresenter = new CommentPresenter(Mockito.anyInt(),mockView,mockLoaderManager,mockLoader);
    }

    @Test
    public void testLoadData(){
        ArrayList<Comment> comments = new ArrayList<>();
        comment.setId(45);
        comments.add(comment);

        ArrayList<Comment> comments2 = new ArrayList<>();
        comment.setId(45);
        comments2.add(comment);
        comments2.add(comment);
        comments2.add(comment);


        Mockito.doAnswer(invocation -> {
            Object[] arguments = invocation.getArguments();

            //callback是第三个参数
            LoaderManager.LoaderCallbacks callback = (LoaderManager.LoaderCallbacks) arguments[2];

            callback.onLoadFinished(mockLoader,comments2);

            return null;
        }).when(mockLoaderManager).initLoader(Mockito.anyInt(),Mockito.any(Bundle.class),Mockito.any(LoaderManager.LoaderCallbacks.class));

        commentPresenter.loadData();

        Mockito.verify(mockLoaderManager).initLoader(Mockito.anyInt(),Mockito.any(Bundle.class),Mockito.any(LoaderManager.LoaderCallbacks.class));
        Mockito.verify(mockView).showData(comments2);

    }
}
