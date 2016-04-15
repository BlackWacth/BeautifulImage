package com.hua.beautifulimage.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hua.beautifulimage.R;
import com.hua.beautifulimage.ui.adapter.PagerAdapter;
import com.hua.beautifulimage.ui.fragment.ImageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private List<PagerAdapter.FragmentModel> mModels;

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;

    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        setSupportActionBar(mToolbar);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPagerAdapter.update(mViewPager.getCurrentItem(), mSwipeRefreshLayout);
            }
        });

        if (mModels == null) {
            mModels = new ArrayList<>();
            mModels.add(new PagerAdapter.FragmentModel("性感美女", new ImageFragment(mSwipeRefreshLayout, 1)));
            mModels.add(new PagerAdapter.FragmentModel("韩日美女", new ImageFragment(mSwipeRefreshLayout, 2)));
            mModels.add(new PagerAdapter.FragmentModel("丝袜美腿", new ImageFragment(mSwipeRefreshLayout, 3)));
            mModels.add(new PagerAdapter.FragmentModel("美女照片", new ImageFragment(mSwipeRefreshLayout, 4)));
            mModels.add(new PagerAdapter.FragmentModel("美女写真", new ImageFragment(mSwipeRefreshLayout, 5)));
            mModels.add(new PagerAdapter.FragmentModel("清纯美女", new ImageFragment(mSwipeRefreshLayout, 6)));
            mModels.add(new PagerAdapter.FragmentModel("性感车模", new ImageFragment(mSwipeRefreshLayout, 7)));
        }

        mPagerAdapter = new PagerAdapter(getFragmentManager(), mModels);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

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
}
