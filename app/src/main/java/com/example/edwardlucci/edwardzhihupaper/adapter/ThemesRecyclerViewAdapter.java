package com.example.edwardlucci.edwardzhihupaper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edwardlucci.edwardzhihupaper.R;
import com.example.edwardlucci.edwardzhihupaper.bean.ChangeContentEvent;
import com.example.edwardlucci.edwardzhihupaper.bean.Other;
import com.example.edwardlucci.edwardzhihupaper.util.RxBus;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by edwardlucci on 16/6/9.
 */
public class ThemesRecyclerViewAdapter extends RecyclerView.Adapter<ThemesRecyclerViewAdapter.ThemeVH> {

    private Context mContext;
    private ArrayList<Other> others;

    public ThemesRecyclerViewAdapter(Context mContext, ArrayList<Other> others) {
        this.mContext = mContext;
        this.others = others;
    }

    @Override
    public ThemeVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ThemeVH(LayoutInflater.from(mContext).inflate(R.layout.themes_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ThemeVH holder, int position) {
        holder.themeTitleTextview.setText(getItem(position).getName());

        Picasso.with(mContext)
                .load(getItem(position).getThumbnail())
                .into(holder.themeImageview);

//        holder.themeImageview.setOnClickListener(v ->
//                RxBus.getInstance().post(new ChangeContentEvent(getItem(position))));
    }

    private Other getItem(int pos) {
        return others.get(pos);
    }

    @Override
    public int getItemCount() {
        if (others != null) {
            return others.size();
        } else {
            return 0;
        }
    }

    class ThemeVH extends RecyclerView.ViewHolder {

        @Bind(R.id.themes_imageview)
        ImageView themeImageview;

        @Bind(R.id.theme_title_textview)
        TextView themeTitleTextview;

        public ThemeVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
