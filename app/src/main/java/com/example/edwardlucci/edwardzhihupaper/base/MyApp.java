package com.example.edwardlucci.edwardzhihupaper.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.example.edwardlucci.edwardzhihupaper.AppModule;
import com.example.edwardlucci.edwardzhihupaper.BuildConfig;
import com.example.edwardlucci.edwardzhihupaper.network.DaggerDataComponent;
import com.example.edwardlucci.edwardzhihupaper.network.DataComponent;
import com.example.edwardlucci.edwardzhihupaper.network.DataModule;
import com.example.edwardlucci.edwardzhihupaper.network.OkClient;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.Logger;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import dagger.internal.DaggerCollections;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

/**
 * Created by edwardlucci on 16/5/20.
 */
public class MyApp extends Application {

    static public OkHttpClient okHttpClient;
    static private int SDK_VERSION;

    private DataComponent dataComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
//            LeakCanary.install(this);

            //stetho
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build());
        }

        dataComponent = DaggerDataComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule("http://news-at.zhihu.com/api/4/"))
                .build();

        //logger setup
        Logger.init("Zhihu");

        okHttpClient = OkClient.getInstance();

        SDK_VERSION = Build.VERSION.SDK_INT;

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
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
