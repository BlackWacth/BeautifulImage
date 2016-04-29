package com.hua.mvp.home.view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hua.mvp.R;
import com.hua.mvp.base.view.ViewImpl;
import com.hua.mvp.home.adapter.MainViewPagerAdapter;
import com.hua.mvp.home.fragment.GalleryFragment;
import com.hua.mvp.widget.MSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 */
public class MainView extends ViewImpl{

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private MSwipeRefreshLayout mSwipeRefreshLayout;
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

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.update(mViewPager.getCurrentItem(), mSwipeRefreshLayout);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
                }
            }
        });
    }

    public void init(AppCompatActivity activity) {
        activity.setSupportActionBar(mToolbar);
        if(models == null) {
            models = new ArrayList<>();
            models.add(new MainViewPagerAdapter.FragmentModel("性感美女", GalleryFragment.newInstance(mSwipeRefreshLayout, 1)));
            models.add(new MainViewPagerAdapter.FragmentModel("韩日美女", GalleryFragment.newInstance(mSwipeRefreshLayout, 2)));
            models.add(new MainViewPagerAdapter.FragmentModel("丝袜美腿", GalleryFragment.newInstance(mSwipeRefreshLayout, 3)));
            models.add(new MainViewPagerAdapter.FragmentModel("美女照片", GalleryFragment.newInstance(mSwipeRefreshLayout, 4)));
            models.add(new MainViewPagerAdapter.FragmentModel("美女写真", GalleryFragment.newInstance(mSwipeRefreshLayout, 5)));
            models.add(new MainViewPagerAdapter.FragmentModel("清纯美女", GalleryFragment.newInstance(mSwipeRefreshLayout, 6)));
            models.add(new MainViewPagerAdapter.FragmentModel("性感车模", GalleryFragment.newInstance(mSwipeRefreshLayout, 7)));
        }
        mAdapter = new MainViewPagerAdapter(activity.getSupportFragmentManager(), models);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
