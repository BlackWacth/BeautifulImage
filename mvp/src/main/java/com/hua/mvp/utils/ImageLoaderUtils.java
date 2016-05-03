package com.hua.mvp.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hua.mvp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * 图片加载二次封装工具
 */
public class ImageLoaderUtils {

    public static DisplayImageOptions initDisplayImageOptions() {
        DisplayImageOptions.Builder options = new DisplayImageOptions.Builder();
//        options.showImageOnLoading(R.mipmap.ic_launcher);
        options.showImageOnFail(R.mipmap.ic_launcher);
        options.showImageForEmptyUri(R.mipmap.ic_launcher);
        options.cacheInMemory(true);
        options.considerExifParams(true);
        options.cacheOnDisk(true);
        options.bitmapConfig(Bitmap.Config.RGB_565);

        return options.build();
    }

    public static void imageLoader(String uri, ImageView imageView, DisplayImageOptions options) {
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    public static void imageLoader(String uri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(uri, imageView, initDisplayImageOptions());
    }

    public static void imageLoader(String uri, ImageView imageView, final ProgressBar progressBar) {

        ImageLoader.getInstance().displayImage(uri, imageView, initDisplayImageOptions(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                progressBar.setVisibility(View.GONE);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String s, View view, int current, int total) {
                progressBar.setProgress(Math.round(100.0f * current / total));
            }
        });
    }
}
