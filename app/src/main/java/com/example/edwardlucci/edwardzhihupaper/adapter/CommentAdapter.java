package com.example.edwardlucci.edwardzhihupaper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
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

        new MaterialDialog.Builder(mContext)
                .title(comments.get(position).getAuthor())
                .content(comments.get(position).getContent())
                .positiveText(R.string.md_back_label)
                .show();
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
