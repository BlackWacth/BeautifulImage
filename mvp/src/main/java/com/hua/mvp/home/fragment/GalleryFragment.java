package com.hua.mvp.home.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.hua.mvp.base.presenter.FragmentPresenterImpl;
import com.hua.mvp.global.MApplication;
import com.hua.mvp.home.entity.Gallery;
import com.hua.mvp.home.view.GalleryView;
import com.hua.mvp.http.subscriber.listener.OnNextListener;
import com.hua.mvp.utils.L;
import com.hua.mvp.widget.MSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 图片列表Fragment
 */
public class GalleryFragment extends FragmentPresenterImpl<GalleryView> {

    private static final String ARG_IMAGE_TYPE = "ARG_IMAGE_TYPE";
    private static final String ARG_SWIPE_REFRESH_LAYOUT = "ARG_SWIPE_REFRESH_LAYOUT";

    private static MSwipeRefreshLayout mSwipeRefreshLayout;
    private int imageType;
    private int page;

    private List<Gallery.Tngou> mList;

    public GalleryFragment() {

    }

    public static GalleryFragment newInstance(MSwipeRefreshLayout swipeRefreshLayout, int imageType) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_TYPE, imageType);
        mSwipeRefreshLayout = swipeRefreshLayout;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageType = getArguments().getInt(ARG_IMAGE_TYPE);
        }
    }

    @Override
    protected void init() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mView.setData(getContext(), mList);
    }

    @Override
    protected void load() {
        if(mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
        loadOne(mSwipeRefreshLayout);
    }

    private void loadOne(SwipeRefreshLayout swipe) {
        page = 1;
        MApplication.mHttpManager.loaderGallery(imageType, page, swipe, new OnNextListener<Gallery>() {
            @Override
            public void onNext(Gallery gallery) {
                isHasLoaded = true;
                mList.clear();
                mList.addAll(gallery.getTngou());
                mView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    public void loadMore() {
        page ++;
        MApplication.mHttpManager.loaderGallery(imageType, page, new Subscriber<Gallery>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                L.i(e.getMessage());
            }

            @Override
            public void onNext(Gallery gallery) {
                int preCount = mView.getAdapter().getItemCount();
                mList.addAll(gallery.getTngou());
                int laterCount = mList.size();
                mView.getAdapter().notifyItemRangeChanged(preCount + 1, laterCount);
            }
        });
    }

    @Override
    public void update(SwipeRefreshLayout swipe) {
       loadOne(swipe);
    }
}
