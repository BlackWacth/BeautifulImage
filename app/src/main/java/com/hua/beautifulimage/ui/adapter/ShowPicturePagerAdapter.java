package com.hua.beautifulimage.ui.adapter;

import android.support.v4.view.*;
import android.view.View;
import android.view.ViewGroup;

import com.hua.beautifulimage.entity.Pictures;
import com.hua.beautifulimage.utils.Constants;
import com.hua.beautifulimage.utils.ImageLoaderUtils;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by ZHONG WEI  HUA on 2016/4/18.
 */
public class ShowPicturePagerAdapter extends android.support.v4.view.PagerAdapter{

    private List<Pictures.Picture> mList;

    public ShowPicturePagerAdapter(List<Pictures.Picture> mList) {
        this.mList = mList;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        String uri = Constants.BASE_IMAGE_URL + mList.get(position).getSrc();
        ImageLoaderUtils.imageLoader(uri, photoView);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
