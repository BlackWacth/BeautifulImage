package com.hua.mvp.http;

import android.support.v4.widget.SwipeRefreshLayout;

import com.hua.mvp.global.C;
import com.hua.mvp.home.entity.Gallery;
import com.hua.mvp.home.entity.Pictures;
import com.hua.mvp.http.api.GalleryService;
import com.hua.mvp.http.api.PicturesService;
import com.hua.mvp.http.subscriber.LoaderSubscriber;
import com.hua.mvp.http.subscriber.listener.OnNextListener;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpManager {

    private Retrofit mRetrofit;
    private GalleryService mGalleryService;
    private PicturesService mPicturesService;

    private static final class HttpManagerHelper{
        public static final HttpManager HTTP_MANAGER = new HttpManager();
    }

    public static HttpManager newInstance(){
        return HttpManagerHelper.HTTP_MANAGER;
    }

    public HttpManager() {
        mRetrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(C.ImageUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mGalleryService = mRetrofit.create(GalleryService.class);
        mPicturesService = mRetrofit.create(PicturesService.class);
    }

    /**
     * 加载图片列表
     * @param id
     * @param page
     * @param rows
     * @param swipeRefreshLayout
     * @param onNextListener
     */
    public void  loaderGallery(int id, int page, int rows, SwipeRefreshLayout swipeRefreshLayout, OnNextListener<Gallery> onNextListener) {
        mGalleryService.query(id, page, rows)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoaderSubscriber<Gallery>(onNextListener, swipeRefreshLayout));
    }

    /**
     * 加载图片列表，默认每页10
     * @param id
     * @param page
     * @param swipeRefreshLayout
     * @param onNextListener
     */
    public void  loaderGallery(int id, int page, SwipeRefreshLayout swipeRefreshLayout, OnNextListener<Gallery> onNextListener) {
        loaderGallery(id, page, C.ImageUrl.DEFAULT_ROWS, swipeRefreshLayout, onNextListener);
    }

    public void  loaderGallery(int id, int page, Subscriber<Gallery> subscriber) {
        mGalleryService.query(id, page, C.ImageUrl.DEFAULT_ROWS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void loadPicture(int id, SwipeRefreshLayout swipeRefreshLayout, OnNextListener<Pictures> onNextListener) {
        mPicturesService.show(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoaderSubscriber<Pictures>(onNextListener, swipeRefreshLayout));
    }

}
