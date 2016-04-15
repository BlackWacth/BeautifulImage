package com.hua.beautifulimage.http.api;

import com.hua.beautifulimage.entity.Gallery;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ZHONG WEI  HUA on 2016/4/1.
 */
public interface GalleryService {

    @GET("list")
    Observable<Gallery> query(@Query("id")int id, @Query("page")int page, @Query("rows")int rows);

}
