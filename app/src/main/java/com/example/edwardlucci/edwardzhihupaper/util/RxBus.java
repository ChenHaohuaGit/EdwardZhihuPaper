package com.example.edwardlucci.edwardzhihupaper.util;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

public class RxBus {
    public static Subscription rxSubscription;

    private static volatile RxBus instance;
    private final SerializedSubject<Object, Object> subject;

    private RxBus() {
        subject = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public void post(Object object) {
        subject.onNext(object);
    }

    public <T> Observable<T> toObservable(final Class<T> type) {
        return subject.ofType(type);
    }

    public boolean hasObservers() {
        return subject.hasObservers();
    }

    /**
     * un-subscribe to avoid leak memory
     *
     * @param subscription Subscription returns from Observable.subscribe(Subscriber) to allow un-subscribing
     * @see #subscribe(Class, Subscriber)
     */
    public void unSubscribe(Subscription subscription) {
        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
