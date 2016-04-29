package com.hua.mvp.http.subscriber;

import android.support.v4.widget.SwipeRefreshLayout;

import com.hua.mvp.http.subscriber.listener.OnNextListener;
import com.hua.mvp.utils.L;

import rx.Subscriber;

/**
 * 带SwipeRefreshLayout的Subscriber, 统一处理onCompleted, onError
 */
public class LoaderSubscriber<T> extends Subscriber<T>{

    private OnNextListener<T> mOnNextListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public LoaderSubscriber(OnNextListener<T> mOnNextListener, SwipeRefreshLayout mSwipeRefreshLayout) {
        this.mOnNextListener = mOnNextListener;
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    @Override
    public void onCompleted() {
        refreshComplete();
    }

    @Override
    public void onError(Throwable e) {
        refreshComplete();
        L.i(e.toString());
    }

    @Override
    public void onNext(T t) {
        if(mOnNextListener != null) {
            mOnNextListener.onNext(t);
        }
    }

    private void refreshComplete(){
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                unsubscribe();
            }
        });
    }

}
