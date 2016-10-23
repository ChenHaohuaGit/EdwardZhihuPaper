package com.example.edwardlucci.edwardzhihupaper;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.edwardlucci.edwardzhihupaper.util.RxBus;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by edward on 2016/10/19.
 */

public class InitService extends IntentService {

    public InitService() {
        super("InitService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        initComponent();
    }

    private void initComponent() {
        if (BuildConfig.DEBUG) {

            //stetho
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build());
        }

        //logger setup
        Logger.init("Zhihu");

        RxBus.getInstance().post("init done");
    }

    public static void start(Context context){
        Intent intent = new Intent(context, InitService.class);
        context.startService(intent);
    }
}
