package com.example.edwardlucci.edwardzhihupaper.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.example.edwardlucci.edwardzhihupaper.AppModule;
import com.example.edwardlucci.edwardzhihupaper.BuildConfig;
import com.example.edwardlucci.edwardzhihupaper.InitService;
import com.example.edwardlucci.edwardzhihupaper.data.network.DaggerDataComponent;
import com.example.edwardlucci.edwardzhihupaper.data.network.DataComponent;
import com.example.edwardlucci.edwardzhihupaper.data.network.DataModule;
import com.example.edwardlucci.edwardzhihupaper.data.network.OkClient;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

/**
 * Created by edwardlucci on 16/5/20.
 */
public class MyApp extends Application {

    static private int SDK_VERSION;

    private DataComponent dataComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        dataComponent = DaggerDataComponent.builder()
                .appModule(new AppModule(this))
                .build();

        SDK_VERSION = Build.VERSION.SDK_INT;

        LeakCanary.install(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        InitService.start(this);
    }

    public static int getSdkVersion() {
        return SDK_VERSION;
    }

    public Context getContext() {
        return getApplicationContext();
    }

    public DataComponent getDataComponent() {
        return dataComponent;
    }
}
