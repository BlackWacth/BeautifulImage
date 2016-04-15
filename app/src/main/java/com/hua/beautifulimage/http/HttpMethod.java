package com.hua.beautifulimage.http;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.hua.beautifulimage.entity.Gallery;
import com.hua.beautifulimage.entity.Pictures;
import com.hua.beautifulimage.http.api.GalleryService;
import com.hua.beautifulimage.http.api.PicturesService;
import com.hua.beautifulimage.http.subscriber.LoaderSubscriber;
import com.hua.beautifulimage.http.subscriber.SubscriberOnNextListener;
import com.hua.beautifulimage.utils.Constants;

import java.util.concurrent.TimeUnit;

import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 网络请求管理类
 * Created by ZHONG WEI  HUA on 2016/4/1.
 */
public class HttpMethod {

    public static final String BASE_URL = "http://www.tngou.net/tnfs/api/";
    public static final int DEFAULT_CONNECT_TIME_OUT = 10;
    public static final int DEFAULT_READ_TIME_OUT = 30;

    private final GalleryService mGalleryService;
    private final PicturesService mPicturesService;

    private static class HttpMethodHolder {
        public static final HttpMethod HTTP_METHOD = new HttpMethod();
    }

    public static HttpMethod create(){
        return HttpMethodHolder.HTTP_METHOD;
    }

    private HttpMethod() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mGalleryService = retrofit.create(GalleryService.class);
        mPicturesService = retrofit.create(PicturesService.class);
    }

    public void queryGallery(Context context, SubscriberOnNextListener listener, SwipeRefreshLayout swipe, int id, int page, int rows) {
        mGalleryService.query(id, page, rows)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoaderSubscriber<Gallery>(context, listener, swipe));
    }

    public void queryGallery(Context context, SubscriberOnNextListener listener, SwipeRefreshLayout swipe, int id, int page) {
        queryGallery(context, listener, swipe, id, page, Constants.DEFAULT_PAGE_ROWS);
    }

    public void show(Subscriber<Pictures> subscriber, int id) {
        mPicturesService.show(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
