package com.example.edwardlucci.edwardzhihupaper.util;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by edwardlucci on 16/5/16.
 */
public class RxUtil {
    public static <T> Observable.Transformer<T, T> fromIOtoMainThread() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

}
