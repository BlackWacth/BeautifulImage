package com.hua.mvp.home.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
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
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hua.mvp.R;
import com.hua.mvp.base.view.ViewImpl;
import com.hua.mvp.home.activity.PictureActivity;
import com.hua.mvp.home.adapter.HackyViewPagerAdapter;
import com.hua.mvp.home.adapter.PictureAdapter;
import com.hua.mvp.home.adapter.listener.OnItemClickListener;
import com.hua.mvp.home.entity.Pictures;
import com.hua.mvp.utils.L;
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
    private FrameLayout mFrameLayout;

    private PictureAdapter mAdapter;

    private LinearLayoutManager mLinearLayoutManager;
    private AnimatorSet mAnimatorSet;
    private int animTime = 300;

    private boolean isShowPicture;
    private boolean move = false;
    private int curreenIndex = 0;

    private float finalScale;
    private int left, top, finalLeft, finalTop;


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
        mFrameLayout = findViewById(R.id.picture_framelayout);
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

    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }

    public void initRecyclerView(Context context, List<Pictures.Picture> list) {
        mAdapter = new PictureAdapter(context, list);
        mLinearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
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
        mRecyclerView.addOnScrollListener(new RecyclerViewListener());
    }

    public void showPicture(final ImageView thumbImage, List<Pictures.Picture> list, int position) {

        if(mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }

        HackyViewPagerAdapter adapter = new HackyViewPagerAdapter(list);
        mHackyViewPager.setAdapter(adapter);
        mHackyViewPager.setCurrentItem(position);

        Rect startBounds = new Rect();
        Rect endBounds = new Rect();
        Point globalOffset = new Point();

        thumbImage.getGlobalVisibleRect(startBounds);
        mFrameLayout.getGlobalVisibleRect(endBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        endBounds.offset(-globalOffset.x, -globalOffset.y);

        left = startBounds.left;
        top = startBounds.top;
        finalLeft = endBounds.left;
        finalTop = endBounds.top;

        L.i("show ---> startBound = " + startBounds.toString());
        L.i("show ---> endBounds = " + endBounds.toString());
        L.i("offset = " + globalOffset.toString());
        L.i("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        final float scale;
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
        finalScale = scale;

        L.i("scale = " + scale);

        mFrameLayout.setPivotX(0);
        mFrameLayout.setPivotY(0);

        thumbImage.setAlpha(0f);
        mFrameLayout.setVisibility(View.VISIBLE);

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(mFrameLayout, View.X, startBounds.left, endBounds.left))
           .with(ObjectAnimator.ofFloat(mFrameLayout, View.Y, startBounds.top, endBounds.top))
           .with(ObjectAnimator.ofFloat(mFrameLayout, View.SCALE_X, scale, 1f))
           .with(ObjectAnimator.ofFloat(mFrameLayout, View.SCALE_Y, scale, 1f));
        set.setDuration(animTime);
        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isShowPicture = true;
                isHideAppBar(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimatorSet = null;
                thumbImage.setAlpha(1f);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mAnimatorSet = null;
                thumbImage.setAlpha(1f);
            }
        });
        set.start();
        mAnimatorSet = set;
    }

    public void closePicture() {
        if(mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }
        if (!isShowPicture) {
            return;
        }

        WindowManager windowManager = ((AppCompatActivity)mPresenter).getWindowManager();
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        mFrameLayout.setPivotX(width/2);
        mFrameLayout.setPivotY(height/2);

//        mFrameLayout.setPivotX(0);
//        mFrameLayout.setPivotY(0);

        finalScale = 0f;
        float finalAlpha = 0.2f;
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(mFrameLayout, View.SCALE_X, finalScale))
           .with(ObjectAnimator.ofFloat(mFrameLayout, View.SCALE_Y, finalScale))
           .with(ObjectAnimator.ofFloat(mFrameLayout, View.ALPHA, finalAlpha));

//        set.play(ObjectAnimator.ofFloat(mFrameLayout, View.X, left))
//           .with(ObjectAnimator.ofFloat(mFrameLayout, View.Y, top))
//           .with(ObjectAnimator.ofFloat(mFrameLayout, View.SCALE_X, finalScale))
//           .with(ObjectAnimator.ofFloat(mFrameLayout, View.SCALE_Y, finalScale));

        set.setDuration(animTime);
        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isShowPicture = false;
                isHideAppBar(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimatorSet = null;
                mFrameLayout.setVisibility(View.GONE);
                mFrameLayout.setAlpha(1f);
                mFrameLayout.setScaleX(1f);
                mFrameLayout.setScaleY(1f);
                mFrameLayout.setX(finalLeft);
                mFrameLayout.setY(finalTop);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mAnimatorSet = null;
                mFrameLayout.setVisibility(View.GONE);
                mFrameLayout.setAlpha(1f);
                mFrameLayout.setScaleX(1f);
                mFrameLayout.setScaleY(1f);
                mFrameLayout.setX(finalLeft);
                mFrameLayout.setY(finalTop);
            }
        });
        set.start();
        mAnimatorSet = set;
    }

    public void isHideAppBar(boolean isHide) {
        if(!isHide) {
            mAppBarLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        }else {
            mAppBarLayout.animate().translationY(-mAppBarLayout.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        }
    }

    public boolean isShowPicture() {
        return isShowPicture;
    }

    private void moveToPosition(int n) {
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        if(n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        }else if(n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        }else {
            mRecyclerView.scrollToPosition(n);
            move = true;
        }
    }

    class RecyclerViewListener extends RecyclerView.OnScrollListener{
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(move){
                move = false;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if(layoutManager instanceof LinearLayoutManager) {
                    int n = curreenIndex - ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    if(n >= 0 && n < recyclerView.getChildCount()) {
                        int top = recyclerView.getChildAt(n).getTop();
                        recyclerView.scrollBy(0, top);
                    }
                }
            }

        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    }
}
