package com.example.edwardlucci.edwardzhihupaper.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.adapter.CommentAdapter;
import com.example.edwardlucci.edwardzhihupaper.base.BaseActivity;
import com.example.edwardlucci.edwardzhihupaper.bean.Comment;
import com.example.edwardlucci.edwardzhihupaper.bean.CommentResponse;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuApi;
import com.example.edwardlucci.edwardzhihupaper.network.ZhihuService;
import com.example.edwardlucci.edwardzhihupaper.util.DensityUtil;
import com.example.edwardlucci.edwardzhihupaper.util.ItemOffsetDecoration;
import com.example.edwardlucci.edwardzhihupaper.util.RxUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by edwardlucci on 16/4/24.
 */
public class CommentActivity extends BaseActivity implements CommentContract.View{

    ZhihuApi zhihuApi;
    int story_id;

    ArrayList<Comment> comments = new ArrayList<>();

    CommentAdapter commentAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        story_id = getIntent().getIntExtra("story_id",0);

        if (story_id == 0){
            finish();
        }

        zhihuApi = ZhihuService.getInstance();

        int paddingDimen = (int) getResources().getDimension(R.dimen.fab_margin);
        recyclerView.setPadding(paddingDimen,paddingDimen,paddingDimen,paddingDimen);
        recyclerView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(DensityUtil.dpToPx(10)));
        setContentView(recyclerView);
        commentAdapter = new CommentAdapter(CommentActivity.this,comments);
        recyclerView.setAdapter(commentAdapter);

        loadComments();

    }

    @Override
    public int getLayout() {
        return R.layout.recyclerview_layout;
    }

    private void loadComments() {
        Observable<CommentResponse> longCommentResponseObservable
                = zhihuApi.getLongComment(story_id);

        Observable<CommentResponse> shortCommentResponseObservable
                = zhihuApi.getShortComment(story_id);

        Observable.merge(shortCommentResponseObservable,longCommentResponseObservable)
                .compose(RxUtil.fromIOtoMainThread())
                .compose(bindToLifecycle())
                .flatMap(commentResponse -> {
                    List<Comment> comments = commentResponse.getComments();
                    Collections.sort(comments,new Comment.LikesCompare());
                    return Observable.from(comments);
                })
                .subscribe(new Subscriber<Comment>() {
                    @Override
                    public void onCompleted() {
                        commentAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Comment comment) {
                        System.out.println(comment.getLikes());
                        comments.add(comment);
                    }
                });
    }

    @Override
    public void showData() {

    }

    @Override
    public void commentDialog() {

    }

    @Override
    public void setPresenter(CommentContract.Presenter basePresenter) {

    }
}
