package com.example.edwardlucci.edwardzhihupaper.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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

                });

        Observable.just(1)
                .delay(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(integer -> {
                    redirect();
                });

//        Observable.just(1).delay(1, TimeUnit.SECONDS).subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                MainActivity.start(SplashActivity.this);
//                finish();
//            }
//        });

}
}
