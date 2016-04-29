package com.hua.mvp.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.hua.mvp.base.presenter.FragmentPresenterImpl;

import java.util.List;

/**
 * MainView中的ViewPager适配器
 */
public class MainViewPagerAdapter extends FragmentStatePagerAdapter{

    private List<FragmentModel> mList;

    public MainViewPagerAdapter(FragmentManager fm, List<FragmentModel> list) {
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

    private FragmentPresenterImpl getCurrentFragment(int position) {
        return (FragmentPresenterImpl) getItem(position);
    }

    public void update(int position, SwipeRefreshLayout swipe){
        getCurrentFragment(position).update(swipe);
    }

    public static class FragmentModel {
        private String title;
        private FragmentPresenterImpl fragment;

        public FragmentModel(String title, FragmentPresenterImpl fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public FragmentPresenterImpl getFragment() {
            return fragment;
        }

        public void setFragment(FragmentPresenterImpl fragment) {
            this.fragment = fragment;
        }
    }
}
