package com.example.edwardlucci.edwardzhihupaper.data.network;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.core.bean.DailyStories;
import com.example.core.bean.Story;
import com.example.edwardlucci.edwardzhihupaper.base.DailyStoriesDeserializer;
import com.example.edwardlucci.edwardzhihupaper.base.StoryDeserializer;
import com.example.edwardlucci.edwardzhihupaper.data.DataManager;
import com.example.edwardlucci.edwardzhihupaper.data.MemoryCache;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by edward on 16/8/1.
 */
@Module
public class DataModule {

    public static final String BASE_URL = "http://news-at.zhihu.com/api/4/";

    // Constructor needs one parameter to instantiate.
    public DataModule() {
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
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(Logger::json);
    }

    @Provides
    @Singleton
    okhttp3.Cache provideCache(Application application) {
        return new okhttp3.Cache(application.getCacheDir(), 1024 * 1024 * 5);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpclient(HttpLoggingInterceptor httpLoggingInterceptor, okhttp3.Cache cache) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    String provideBaseUrl() {
        return BASE_URL;
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
    ZhihuApi providerZhihuApi(Retrofit retrofit) {
        return retrofit.create(ZhihuApi.class);
    }

    @Provides
    @Singleton
    DataManager provideDataManager(ZhihuApi zhihuApi, MemoryCache memoryCache) {
        return new DataManager(zhihuApi, memoryCache);
    }
}
