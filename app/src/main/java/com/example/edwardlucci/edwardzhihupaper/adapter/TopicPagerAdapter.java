package com.example.edwardlucci.edwardzhihupaper.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.core.bean.Other;

import java.util.ArrayList;

/**
 * Created by edwardlucci on 16/5/6.
 */

public class TopicPagerAdapter extends PagerAdapter{

    Context mContext;
    ArrayList<Other> others;

    ArrayList<View> views;

    public TopicPagerAdapter(Context mContext, ArrayList<View> views) {
        this.mContext = mContext;
        this.views = views;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        System.out.println(position);
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
