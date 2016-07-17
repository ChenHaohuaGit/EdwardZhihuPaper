package com.example.edwardlucci.edwardzhihupaper.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.example.edwardlucci.edwardzhihupaper.ui.StoryActivity;
import com.example.edwardlucci.edwardzhihupaper.view.FadedBorderImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by edwardlucci on 16/4/23.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.NewsViewHolder> {

    private Context mContext;
    private ArrayList<Story> stories;
    private OnitemClickListener onitemClickListener;

    public ContentAdapter(@NonNull Context mContext, @NonNull ArrayList<Story> stories) {
        this.mContext = mContext;
        this.stories = stories;
    }

    class ContentType {
        public static final int NORMAL = 0;
        public static final int XIACHE = 1;
        public static final int SHENYE = 2;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new NewsViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.news_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        final Story story = stories.get(position);

        boolean hasImage = false;
        loadImageFromNetwork(story, holder);

        holder.news_title_textview.setText(story.getTitle());

        holder.container.setOnClickListener(v ->
                StoryActivity.startActivity(mContext, stories.get(position).getId(), Pair.create(holder.news_imageview, "image"), Pair.create(holder.news_title_textview, "title")));
    }

    private void loadImageFromDrawable(NewsViewHolder holder) {
        Picasso.with(mContext)
                .load(R.drawable.unknown)
                .placeholder(R.drawable.transparent)
                .into(holder.news_imageview, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) holder.news_imageview.getDrawable()).getBitmap();

                        Palette.from(bitmap)
                                .generate(palette -> {
                                    if (null != palette.getVibrantSwatch()) {
                                        holder.news_title_textview.setBackgroundColor(palette.getVibrantSwatch().getRgb());
                                        holder.news_title_textview.setTextColor(palette.getVibrantSwatch().getTitleTextColor());
                                    } else {
                                        holder.news_title_textview.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                                        holder.news_title_textview.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                                    }
                                });

                    }

                    @Override
                    public void onError() {

                    }
                });

    }

    private void loadImageFromNetwork(Story story, NewsViewHolder holder) {
        Picasso.with(mContext)
                .load(story.getImage())
                .placeholder(R.drawable.transparent)
                .error(R.drawable.unknown)
                .into(holder.news_imageview, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) holder.news_imageview.getDrawable()).getBitmap();

                        Palette.from(bitmap)
                                .generate(palette -> {
                                    if (null != palette.getVibrantSwatch()) {
                                        holder.news_title_textview.setBackgroundColor(palette.getVibrantSwatch().getRgb());
                                        holder.news_title_textview.setTextColor(palette.getVibrantSwatch().getTitleTextColor());
                                    } else {
                                        holder.news_title_textview.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                                        holder.news_title_textview.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                                    }
                                });

                    }

                    @Override
                    public void onError() {

                    }
                });

    }

//    @Override
//    public int getItemViewType(int position) {
//        if (stories.get(position).getTitle().contains("深夜惊奇")) {
//            return ContentType.SHENYE;
//        } else if (stories.get(position).getTitle().contains("瞎扯")) {
//            return ContentType.XIACHE;
//        } else {
//            return ContentType.NORMAL;
//        }
//    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.container)
        CardView container;

        @Bind(R.id.news_image)
        FadedBorderImageView news_imageview;

        @Bind(R.id.news_title)
        TextView news_title_textview;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            //set listener in bind process now
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onitemClickListener) {
                onitemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public void setData(@Nullable ArrayList<Story> stories) {
        if (this.stories == null) {
            this.stories = stories;
        } else {
            this.stories.clear();
            if (null != stories) {
                this.stories.addAll(stories);
            }
        }

    }

    public void setOnitemClickListener(OnitemClickListener onitemClickListener) {
        this.onitemClickListener = onitemClickListener;
    }
}
