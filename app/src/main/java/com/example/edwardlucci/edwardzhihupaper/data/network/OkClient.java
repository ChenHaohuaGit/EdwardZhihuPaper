package com.example.edwardlucci.edwardzhihupaper.data.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by edward on 16/6/16.
 */
public class OkClient {

    private OkClient() {
    }

    public static OkHttpClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static final OkHttpClient INSTANCE = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

}
