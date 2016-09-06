package com.example.edwardlucci.edwardzhihupaper.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.adapter.CommentAdapter;
import com.example.edwardlucci.edwardzhihupaper.base.BaseActivity;
import com.example.edwardlucci.edwardzhihupaper.bean.Comment;
import com.example.edwardlucci.edwardzhihupaper.data.network.CommentLoader;
import com.example.edwardlucci.edwardzhihupaper.util.DensityUtil;
import com.example.edwardlucci.edwardzhihupaper.util.ItemOffsetDecoration;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by edwardlucci on 16/4/24.
 */
public class CommentActivity extends BaseActivity implements CommentContract.View {

    int story_id;

    public static final String EXTRA_STORY_ID  ="story_id";

    ArrayList<Comment> comments = new ArrayList<>();

    CommentAdapter commentAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    CommentContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        story_id = getIntent().getIntExtra("story_id", 0);

        if (story_id == 0) {
            Toast.makeText(getActivity(),"story id = null",Toast.LENGTH_SHORT).show();
            finish();
        }

        initRecyclerView();
        new CommentPresenter(story_id, this, getLoaderManager(), new CommentLoader(getActivity(), story_id));
    }

    private void initRecyclerView() {
        int paddingDimen = (int) getResources().getDimension(R.dimen.fab_margin);
        recyclerView.setPadding(paddingDimen, paddingDimen, paddingDimen, paddingDimen);
        recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(DensityUtil.dpToPx(10)));
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
        setContentView(recyclerView);
        commentAdapter = new CommentAdapter(CommentActivity.this, comments);
        recyclerView.setAdapter(commentAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public int getLayout() {
        return R.layout.recyclerview_layout;
    }

    @Override
    public void showData(ArrayList<Comment> comments) {
        this.comments.clear();
        this.comments.addAll(comments);
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(CommentContract.Presenter basePresenter) {
        presenter = basePresenter;
    }

    public void start(Context context,int storyId){
        Intent intent = new Intent();
        intent.setClass(context,CommentActivity.class);
        intent.putExtra(EXTRA_STORY_ID,storyId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }
}
