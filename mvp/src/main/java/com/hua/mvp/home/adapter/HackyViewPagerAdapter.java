package com.hua.mvp.home.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hua.mvp.R;
import com.hua.mvp.global.C;
import com.hua.mvp.home.entity.Pictures;
import com.hua.mvp.utils.ImageLoaderUtils;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class HackyViewPagerAdapter extends PagerAdapter {

    private List<Pictures.Picture> mList;

    public HackyViewPagerAdapter(List<Pictures.Picture> mList) {
        this.mList = mList;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_hacky_view_pager, container,false);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.item_hacky_progress_bar);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.item_hacky_photoview);
        String url = C.ImageUrl.BASE_IMAGE_URL + mList.get(position).getSrc();
        ImageLoaderUtils.imageLoader(url, photoView, progressBar);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
