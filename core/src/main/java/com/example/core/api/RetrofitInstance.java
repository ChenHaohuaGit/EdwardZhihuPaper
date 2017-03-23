package com.example.core.api;


import com.example.core.bean.DailyStories;
import com.example.core.bean.Story;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by edwardlucci on 16/4/23.
 */
public class RetrofitInstance {

    private static final String base_url = "http://news-at.zhihu.com/api/4/";

    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Story.class, new StoryDeserializer())
            .registerTypeAdapter(DailyStories.class, new DailyStoriesDeserializer())
            .create();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(base_url)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OkClient.getInstance())
            .build();

    private RetrofitInstance() {
    }

    public static Retrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static final Retrofit INSTANCE = new Retrofit.Builder()
                .baseUrl(base_url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(OkClient.getInstance())
                .build();
    }
}