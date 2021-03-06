package com.hua.beautifulimage.http.api;


import com.hua.beautifulimage.entity.Pictures;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 具体图片列表
 */
public interface PicturesService {

    @GET("show")
    Observable<Pictures> show(@Query("id") int id);

}
