package com.hua.beautifulimage.ui.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hua.beautifulimage.R;
import com.hua.beautifulimage.entity.Gallery;
import com.hua.beautifulimage.http.HttpMethod;
import com.hua.beautifulimage.utils.L;

import rx.Subscriber;

/**
 * 性感美女
 */
public class SexyFragment extends BaseFragment {

    private HttpMethod mHttpMethod;

    @Override
    protected void init() {
        mHttpMethod = HttpMethod.create();
        mHttpMethod.queryGallery(new Subscriber<Gallery>() {
            @Override
            public void onCompleted() {
                showToast("加载完成");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Gallery gallery) {
                L.i(gallery.toString());
            }
        }, 1, 1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sexy;
    }
}
