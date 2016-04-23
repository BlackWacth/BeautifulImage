package com.hua.mvp.home.view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;

import com.hua.mvp.R;
import com.hua.mvp.base.view.ViewImpl;
import com.hua.mvp.home.MainViewPagerAdapter;
import com.hua.mvp.home.fragment.GalleryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 */
public class MainView extends ViewImpl{

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ViewPager mViewPager;

    private List<MainViewPagerAdapter.FragmentModel> models;

    private MainViewPagerAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void created() {
        super.created();
        mToolbar = findViewById(R.id.main_tool_bar);
        mTabLayout = findViewById(R.id.main_tab_layout);
        mSwipeRefreshLayout = findViewById(R.id.main_swipe_refresh_layout);
        mViewPager = findViewById(R.id.main_view_pager);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        initViewPager();
    }

    private void initViewPager() {
        if(models == null) {
            models = new ArrayList<>();
            models.add(new MainViewPagerAdapter.FragmentModel("性感美女", GalleryFragment.newInstance(1)));
            models.add(new MainViewPagerAdapter.FragmentModel("韩日美女", GalleryFragment.newInstance(2)));
            models.add(new MainViewPagerAdapter.FragmentModel("丝袜美腿", GalleryFragment.newInstance(3)));
            models.add(new MainViewPagerAdapter.FragmentModel("美女照片", GalleryFragment.newInstance(4)));
            models.add(new MainViewPagerAdapter.FragmentModel("美女写真", GalleryFragment.newInstance(5)));
            models.add(new MainViewPagerAdapter.FragmentModel("清纯美女", GalleryFragment.newInstance(6)));
            models.add(new MainViewPagerAdapter.FragmentModel("性感车模", GalleryFragment.newInstance(7)));
        }
//        mAdapter = new MainViewPagerAdapter()
    }
}
