package com.example.edwardlucci.edwardzhihupaper;

import android.app.LoaderManager;
import android.os.Bundle;

import com.example.edwardlucci.edwardzhihupaper.bean.Comment;
import com.example.edwardlucci.edwardzhihupaper.bean.CommentResponse;
import com.example.edwardlucci.edwardzhihupaper.comment.CommentContract;
import com.example.edwardlucci.edwardzhihupaper.comment.CommentPresenter;
import com.example.edwardlucci.edwardzhihupaper.network.CommentLoader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Iterator;

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

    @Mock
    LoaderManager.LoaderCallbacks mockCallback;

    CommentPresenter commentPresenter;

    Comment comment = new Comment();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        commentPresenter = new CommentPresenter(Mockito.anyInt(),mockView,mockLoaderManager,mockLoader);
    }

    @Test
    public void testLoadData(){

        CommentResponse commentResponse = new CommentResponse();
        ArrayList<Comment> comments = new ArrayList<>();
        comment.setId(45);
        comments.add(comment);

        ArrayList<Comment> comments2 = new ArrayList<>();
        comment.setId(45);
        comments2.add(comment);
        comments2.add(comment);
        comments2.add(comment);
        commentResponse.setComments(comments);

        Mockito.doAnswer(invocation -> {
            Object[] arguments = invocation.getArguments();

            //callback是第三个参数
            LoaderManager.LoaderCallbacks callback = (LoaderManager.LoaderCallbacks) arguments[2];

            callback.onLoadFinished(mockLoader,commentResponse);

            return null;
        }).when(mockLoaderManager).initLoader(Mockito.anyInt(),Mockito.any(Bundle.class),Mockito.any(LoaderManager.LoaderCallbacks.class));

        commentPresenter.loadData();

        Mockito.verify(mockLoaderManager).initLoader(Mockito.anyInt(),null,mockCallback);

        Mockito.verify(mockView).showData(comments);
//
//        Mockito.verify()
    }
}
