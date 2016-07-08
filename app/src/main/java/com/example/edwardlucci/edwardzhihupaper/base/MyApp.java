package com.example.edwardlucci.edwardzhihupaper.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.example.edwardlucci.edwardzhihupaper.network.OkClient;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.Logger;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
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
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        okHttpClient = OkClient.getInstance();

        SDK_VERSION = Build.VERSION.SDK_INT;

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    public static int getSdkVersion() {
        return SDK_VERSION;
    }

    public Context getContext(){
        return getApplicationContext();
    }
}
