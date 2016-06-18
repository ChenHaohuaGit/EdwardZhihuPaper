package com.example.edwardlucci.edwardzhihupaper.network;

import android.content.Context;

import com.example.edwardlucci.edwardzhihupaper.base.AsyncLoader;
import com.example.edwardlucci.edwardzhihupaper.bean.DailyStories;

/**
 * Created by edward on 16/6/14.
 */
public class PastStoriesLoader extends AsyncLoader<DailyStories>{

    String date;

    public PastStoriesLoader(Context context,String date) {
        super(context);
        this.date = date;
    }

    @Override
    public DailyStories loadInBackground() {
        //database
        //network

        return ZhihuService.getInstance()
                .getPastStories(date)
                .toBlocking()
                .first();
    }
}
