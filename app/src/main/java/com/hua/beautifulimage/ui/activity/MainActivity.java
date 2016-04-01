package com.hua.beautifulimage.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hua.beautifulimage.R;
import com.hua.beautifulimage.ui.adapter.PagerAdapter;
import com.hua.beautifulimage.ui.fragment.CarModelFragment;
import com.hua.beautifulimage.ui.fragment.GrilDescribeFragment;
import com.hua.beautifulimage.ui.fragment.GrilPhotoFragment;
import com.hua.beautifulimage.ui.fragment.KoreaJapanFragment;
import com.hua.beautifulimage.ui.fragment.PureFragment;
import com.hua.beautifulimage.ui.fragment.SexyFragment;
import com.hua.beautifulimage.ui.fragment.StockingsFragment;

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

        if(mModels == null) {
            mModels = new ArrayList<>();
            mModels.add(new PagerAdapter.FragmentModel("性感美女", new SexyFragment()));
            mModels.add(new PagerAdapter.FragmentModel("韩日美女", new KoreaJapanFragment()));
            mModels.add(new PagerAdapter.FragmentModel("丝袜美腿", new StockingsFragment()));
            mModels.add(new PagerAdapter.FragmentModel("美女照片", new GrilPhotoFragment()));
            mModels.add(new PagerAdapter.FragmentModel("美女写真", new GrilDescribeFragment()));
            mModels.add(new PagerAdapter.FragmentModel("清纯美女", new PureFragment()));
            mModels.add(new PagerAdapter.FragmentModel("性感车模", new CarModelFragment()));
        }

        mPagerAdapter = new PagerAdapter(getFragmentManager(), mModels);
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }
}
