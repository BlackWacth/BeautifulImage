package com.hua.mvp.global;

import android.app.Application;
import android.content.Context;

import com.hua.mvp.http.HttpManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MApplication extends Application {

    public static HttpManager mHttpManager;

    @Override
    public void onCreate() {
//        if(Constants.Config.DEVELOPER_MODE && Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
//
//        }
        super.onCreate();
        mHttpManager = HttpManager.newInstance();

        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.writeDebugLogs();

        ImageLoader.getInstance().init(config.build());
    }
}
