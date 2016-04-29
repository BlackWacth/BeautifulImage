package com.hua.mvp.http.api;

import com.hua.mvp.home.entity.Gallery;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 列表
 */
public interface GalleryService {

    @GET("list")
    Observable<Gallery> query(@Query("id")int id, @Query("page")int page, @Query("rows")int rows);

}
