package com.example.edwardlucci.edwardzhihupaper.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.example.edwardlucci.edwardzhihupaper.network.OkClient;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.Logger;

import okhttp3.OkHttpClient;

/**
 * Created by edwardlucci on 16/5/20.
 */
public class MyApp extends Application{

    static public OkHttpClient okHttpClient;
    static private int SDK_VERSION;

    @Override
    public void onCreate() {
        super.onCreate();

        //logger setup
        Logger.init("Zhihu");

        //stetho
        Stetho.initializeWithDefaults(this);

        okHttpClient = OkClient.getInstance();

        SDK_VERSION = Build.VERSION.SDK_INT;
    }

    public static int getSdkVersion() {
        return SDK_VERSION;
    }

    public Context getContext(){
        return getApplicationContext();
    }
}
