package com.example.edwardlucci.edwardzhihupaper.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.edwardlucci.edwardzhihupaper.InitService;
import com.example.edwardlucci.edwardzhihupaper.util.RxBus;
import com.trello.rxlifecycle.components.RxActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by edward on 2016/10/11.
 */

public class SplashActivity extends RxActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RxBus.getInstance().toObservable(String.class)
                .filter(s -> s.equals("init done"))
                .subscribe(s -> {
                    redirect();
                });

        InitService.start(this);

    }

    public void redirect(){
        MainActivity.start(this);
        finish();
    }
}
