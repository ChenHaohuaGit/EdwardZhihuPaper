package com.example.edwardlucci.edwardzhihupaper.data.network;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.edwardlucci.edwardzhihupaper.base.DailyStoriesDeserializer;
import com.example.edwardlucci.edwardzhihupaper.base.StoryDeserializer;
import com.example.edwardlucci.edwardzhihupaper.bean.DailyStories;
import com.example.edwardlucci.edwardzhihupaper.bean.Story;
import com.example.edwardlucci.edwardzhihupaper.data.DataManager;
import com.example.edwardlucci.edwardzhihupaper.data.MemoryCache;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by edward on 16/8/1.
 */
@Module
public class DataModule {

    String mBaseUrl;

    // Constructor needs one parameter to instantiate.
    public DataModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    MemoryCache provideMemoryCache() {
        return new MemoryCache();
    }

    @Provides
    @Singleton
        // Application reference must come from AppModule.class
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Story.class, new StoryDeserializer())
                .registerTypeAdapter(DailyStories.class, new DailyStoriesDeserializer())
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpclient(){
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    @Provides
    @Singleton
    String provideBaseUrl(){
        return mBaseUrl;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(String baseUrl, Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    ZhihuApi providerZhihuApi(Retrofit retrofit){
        return retrofit.create(ZhihuApi.class);
    }

    @Provides
    @Singleton
    DataManager provideDataManager(ZhihuApi zhihuApi, MemoryCache memoryCache){
        return new DataManager(zhihuApi,memoryCache);
    }
}
