package com.example.core.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by anzhuo002 on 2016/7/6.
 */

public abstract class BaseCommonPresenter<T extends BaseView>{
    /**
     * 使用CompositeSubscription来持有所有的Subscriptions
     */
    private CompositeSubscription mCompositeSubscription;

    public T mView;

    protected abstract void start();

    public BaseCommonPresenter(T view) {
        //创建 CompositeSubscription 对象 使用CompositeSubscription来持有所有的Subscriptions，然后在onDestroy()或者onDestroyView()里取消所有的订阅。
        mCompositeSubscription = new CompositeSubscription();
        this.mView = view;
        if (this instanceof BasePresenter){
            mView.setPresenter(this);
        }
    }

    /**
     * 解绑 CompositeSubscription
     */
    public void unsubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void destroy(){
        unsubscribe();
    }

    protected void addToCompositeSubscription(Subscription subscription){
        mCompositeSubscription.add(subscription);
    }
}
