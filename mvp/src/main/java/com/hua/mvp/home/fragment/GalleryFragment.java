package com.hua.mvp.home.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hua.mvp.R;
import com.hua.mvp.base.BaseFragment;
import com.hua.mvp.base.presenter.FragmentPresenterImpl;

/**
 * 图片列表Fragment
 */
public class GalleryFragment extends FragmentPresenterImpl {

    private static final String ARG_IMAGE_TYPE = "ARG_IMAGE_TYPE";

    private int imageType;


    public GalleryFragment() {
    }

    public static GalleryFragment newInstance(int imageType) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_TYPE, imageType);
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
}
