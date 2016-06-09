package com.example.edwardlucci.edwardzhihupaper.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.bean.Comment;
import com.example.edwardlucci.edwardzhihupaper.transformation.CircleTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by edwardlucci on 16/4/25.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private ArrayList<Comment> comments;
    Transformation circle_transformation = new CircleTransform();

    public CommentAdapter(Context mContext, ArrayList<Comment> comments) {
        this.mContext = mContext;
        this.comments = comments;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CommentViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.comment_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, final int position) {
        holder.commentTextview.setText(comments.get(position).getContent());

        holder.container.setOnClickListener(v -> showFullComment(position));

        if (comments.get(position).getAvatar()!=null || !comments.get(position).getAvatar().isEmpty()){
            Picasso.with(mContext)
                    .load(comments.get(position).getAvatar())
                    .transform(circle_transformation)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.avatarPicImageview);
        }


    }

    private void showFullComment(int position) {
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView commentTextview = new TextView(mContext);
        commentTextview.setVerticalScrollBarEnabled(true);
        commentTextview.setMovementMethod(new ScrollingMovementMethod());
        commentTextview.setText(comments.get(position).getContent());
        commentTextview.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        commentTextview.setPadding(20,20,20,20);
        commentTextview.setTextSize(20);
        dialog.setContentView(commentTextview);

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.avatar_pic_imageview)
        ImageView avatarPicImageview;

        @Bind(R.id.comment_textview)
        TextView commentTextview;

        @Bind(R.id.container)
        LinearLayout container;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
