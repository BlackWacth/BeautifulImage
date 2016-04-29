package com.hua.mvp.home.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.hua.mvp.R;
import com.hua.mvp.base.view.ViewImpl;
import com.hua.mvp.home.activity.PictureActivity;
import com.hua.mvp.home.adapter.HackyViewPagerAdapter;
import com.hua.mvp.home.adapter.PictureAdapter;
import com.hua.mvp.home.adapter.listener.OnItemClickListener;
import com.hua.mvp.home.entity.Pictures;
import com.hua.mvp.widget.HackyViewPager;
import com.hua.mvp.widget.MSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片展示
 */
public class PictureView extends ViewImpl {

    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private MSwipeRefreshLayout mSwipeRefreshLayout;
    private HackyViewPager mHackyViewPager;

    private PictureAdapter mAdapter;

    private AnimatorSet mAnimatorSet;
    private int animTime = 300;

    @Override
    public int getLayoutId() {
        return R.layout.activity_picture;
    }

    @Override
    public void created() {
        super.created();
        mAppBarLayout = findViewById(R.id.picture_app_bar_layout);
        mToolbar = findViewById(R.id.picture_tool_bar);
        mRecyclerView = findViewById(R.id.picture_recycler_view);
        mHackyViewPager = findViewById(R.id.picture_hacky_view_pager);
        mSwipeRefreshLayout = findViewById(R.id.picture_siwpe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
    }

    public MSwipeRefreshLayout getmSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public PictureAdapter getmAdapter() {
        return mAdapter;
    }

    public void initToolbar(final AppCompatActivity activity){
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }

    public void initRecyclerView(Context context, List<Pictures.Picture> list) {
        mAdapter = new PictureAdapter(context, list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new OnItemClickListener<Pictures.Picture>() {
            @Override
            public void onItemClick(View view, int position, Pictures.Picture picture) {
                showPicture((ImageView) view, mAdapter.getList(), position);
            }
        });
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((PictureActivity)mPresenter).loadPicture();
            }
        });

    }

    public void showPicture(ImageView thumbImage, List<Pictures.Picture> list, int position) {

        if(mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }
        HackyViewPagerAdapter adapter = new HackyViewPagerAdapter(list);
        mHackyViewPager.setAdapter(adapter);
        mHackyViewPager.setCurrentItem(position);

        final Rect startBounds = new Rect();
        final Rect endBounds = new Rect();
        Point globalOffset = new Point();

        thumbImage.getGlobalVisibleRect(startBounds);
        mHackyViewPager.getGlobalVisibleRect(endBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        endBounds.offset(-globalOffset.x, -globalOffset.y);

        float scale;
        if(((float)endBounds.width() / endBounds.height()) > ((float) startBounds.width() / startBounds.height())) {
            scale = (float) startBounds.height() / endBounds.height();
            float startWidth = endBounds.width() * scale;
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        }else {
            scale = (float) startBounds.width() / endBounds.width();
            float startHeight = endBounds.height() * scale;
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        mHackyViewPager.setPivotX(0);
        mHackyViewPager.setPivotY(0);

        thumbImage.setAlpha(0f);
        mHackyViewPager.setVisibility(View.VISIBLE);

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(mHackyViewPager, View.X, startBounds.left, endBounds.left))
                .with(ObjectAnimator.ofFloat(mHackyViewPager, View.Y, startBounds.top, endBounds.top))
                .with(ObjectAnimator.ofFloat(mHackyViewPager, View.SCALE_X, scale, 1f))
                .with(ObjectAnimator.ofFloat(mHackyViewPager, View.SCALE_Y, scale, 1f));
        set.setDuration(animTime);
        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
//                isShowPager = true;
//                hideViews();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                mAnimatorSet = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
//                mAnimatorSet = null;
            }
        });
        set.start();
        mAnimatorSet = set;
    }
}
