package com.hua.beautifulimage.ui.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.hua.beautifulimage.ui.fragment.BaseFragment;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 主页适配器
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<FragmentModel> mList;

    public PagerAdapter(FragmentManager fm, List<FragmentModel> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getTitle();
    }

    public boolean checkCanDoRefresh(int position, PtrFrameLayout frame, View content, View header) {
        return getCurrentFragment(position).checkCanDoRefresh(frame, content, header);
    }

    private BaseFragment getCurrentFragment(int position) {
        return (BaseFragment) getItem(position);
    }

    public void update(int position, SwipeRefreshLayout swipe){
        getCurrentFragment(position).update(swipe);
    }

    public static class FragmentModel {
        private String title;
        private BaseFragment fragment;

        public FragmentModel(String title, BaseFragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public BaseFragment getFragment() {
            return fragment;
        }

        public void setFragment(BaseFragment fragment) {
            this.fragment = fragment;
        }
    }
}
