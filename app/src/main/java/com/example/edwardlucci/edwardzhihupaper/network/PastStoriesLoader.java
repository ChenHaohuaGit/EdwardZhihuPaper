package com.example.edwardlucci.edwardzhihupaper.network;

import android.content.Context;

import com.example.edwardlucci.edwardzhihupaper.base.AsyncLoader;
import com.example.edwardlucci.edwardzhihupaper.bean.LatestStories;

/**
 * Created by edward on 16/6/14.
 */
public class PastStoriesLoader extends AsyncLoader<LatestStories>{

    String date;

    public PastStoriesLoader(Context context,String date) {
        super(context);
        this.date = date;
    }

    @Override
    public LatestStories loadInBackground() {
        //database
        //network

        return ZhihuService.getInstance()
                .getPastStories(date)
                .toBlocking()
                .first();
    }
}
