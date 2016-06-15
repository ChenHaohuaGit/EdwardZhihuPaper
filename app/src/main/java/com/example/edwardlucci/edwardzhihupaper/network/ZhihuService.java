package com.example.edwardlucci.edwardzhihupaper.network;

import com.example.edwardlucci.edwardzhihupaper.base.MyApp;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by edwardlucci on 16/4/23.
 */
public class ZhihuService {

    private static final String base_url= "http://news-at.zhihu.com/api/4/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(base_url)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
//            .client(MyApp.okHttpClient)
            .build();

    private ZhihuService() {}

    public static ZhihuApi getInstance(){
        return SingletonHolder.INSTANCE;
    }

    @Deprecated
    public static ZhihuApi createZhihuService() {
        return retrofit.create(ZhihuApi.class);
    }

    private static class SingletonHolder{
        static final  ZhihuApi INSTANCE = retrofit.create(ZhihuApi.class);}
}
