package com.hua.beautifulimage.http.subscriber;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.hua.beautifulimage.utils.L;

import rx.Subscriber;

/**
 * Created by ZHONG WEI  HUA on 2016/4/1.
 */
public class LoaderSubscriber<T> extends Subscriber<T>{

    private Context mContext;
    private SubscriberOnNextListener<T> mSubscriberOnNextListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public LoaderSubscriber(Context mContext, SubscriberOnNextListener<T> mSubscriberOnNextListener, SwipeRefreshLayout mSwipeRefreshLayout) {
        this.mContext = mContext;
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    @Override
    public void onStart() {
        loader(true);
    }

    @Override
    public void onCompleted() {
        showToast("加载完成");
        loader(false);
    }

    @Override
    public void onError(Throwable e) {
        L.e(e.getMessage());
        showToast("网络异常");
        loader(false);
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.onNext(t);
    }

    private void loader(boolean flag) {
        mSwipeRefreshLayout.setRefreshing(flag);
    }

    public void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }
}
