package com.example.sss.littlenotes.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by edwardlucci on 16/5/20.
 */
public class MyApp extends Application {

    static private int SDK_VERSION;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static int getSdkVersion() {
        return SDK_VERSION;
    }

    public Context getContext() {
        return getApplicationContext();
    }

}
