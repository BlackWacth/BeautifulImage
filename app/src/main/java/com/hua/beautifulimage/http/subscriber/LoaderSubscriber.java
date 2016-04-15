package com.hua.beautifulimage.http.subscriber;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.hua.beautifulimage.utils.L;

import in.srain.cube.views.ptr.PtrFrameLayout;
import rx.Subscriber;

public class LoaderSubscriber<T> extends Subscriber<T>{

    private Context mContext;
    private SubscriberOnNextListener<T> mSubscriberOnNextListener;
    private SwipeRefreshLayout swipe;

    public LoaderSubscriber(Context mContext, SubscriberOnNextListener<T> mSubscriberOnNextListener, SwipeRefreshLayout swipe) {
        this.mContext = mContext;
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.swipe = swipe;
    }

    @Override
    public void onCompleted() {
        showToast("加载完成");
        swipe.setRefreshing(false);
    }

    @Override
    public void onError(Throwable e) {
        L.e(e.getMessage());
        showToast("网络异常");
        swipe.setRefreshing(false);
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.onNext(t);
    }

    public void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }
}
