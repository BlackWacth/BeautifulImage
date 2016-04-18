package com.hua.beautifulimage.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hua.beautifulimage.R;
import com.hua.beautifulimage.application.BApplication;
import com.hua.beautifulimage.entity.Pictures;
import com.hua.beautifulimage.ui.adapter.OnItemClickListener;
import com.hua.beautifulimage.ui.adapter.ShowPictureAdapter;
import com.hua.beautifulimage.ui.adapter.ShowPicturePagerAdapter;
import com.hua.beautifulimage.ui.view.HackyViewPager;
import com.hua.beautifulimage.utils.Constants;
import com.hua.beautifulimage.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

public class ShowImageActivity extends AppCompatActivity {

    @Bind(R.id.show_tool_bar)
    Toolbar mToolbar;

    @Bind(R.id.show_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.show_pager_layout)
    FrameLayout expandLayout;

    @Bind(R.id.show_hacky_view_pager)
    HackyViewPager hackyViewPager;

    @Bind(R.id.show_app_bar_layout)
    AppBarLayout appBarLayout;

    private List<Pictures.Picture> mList;
    private ShowPictureAdapter mAdapter;

    private Animator mAnimator;
    private int animTime = 300;

    private boolean isShowPager = false;

    private ImageView thumbPicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Toolbar上添加箭头
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if(mList == null) {
            mList = new ArrayList<>();
        }
        mAdapter = new ShowPictureAdapter(this, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                L.i("position = " + position);
                showPictureInViewPager((ImageView) view, mList, position);
            }
        });

        Intent intent = getIntent();
        int id = intent.getIntExtra(Constants.EXTRA_SHOW_PICTURE_ID, 1);
        getPictures(id);
    }


    private void showPictureInViewPager(ImageView thumbImage, List<Pictures.Picture> list, int position){
        if(mAnimator != null) {
            mAnimator.cancel();
        }

        thumbPicture = thumbImage;

        ShowPicturePagerAdapter pagerAdapter = new ShowPicturePagerAdapter(list);
        hackyViewPager.setAdapter(pagerAdapter);
        hackyViewPager.setCurrentItem(position);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        Point globalOffset = new Point();

        thumbImage.getGlobalVisibleRect(startBounds);
        expandLayout.getGlobalVisibleRect(finalBounds, globalOffset);

        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float scale;
        if(((float)finalBounds.width() / finalBounds.height()) > ((float)startBounds.width() / startBounds.height())) {
            scale = (float) startBounds.height() / finalBounds.height();
            float startWidth = finalBounds.width() * scale;
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        }else {
            scale = (float) startBounds.width() / finalBounds.width();
            float startHeight = finalBounds.height() * scale;
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        expandLayout.setPivotX(0);
        expandLayout.setPivotY(0);

        thumbImage.setAlpha(0f);
        expandLayout.setVisibility(View.VISIBLE);

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(expandLayout, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandLayout, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandLayout, View.SCALE_X, scale, 1f))
                .with(ObjectAnimator.ofFloat(expandLayout, View.SCALE_Y, scale, 1f));
        set.setDuration(animTime);
        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isShowPager = true;
                hideViews();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mAnimator = null;
            }
        });
        set.start();
        mAnimator = set;
    }

    private void closePicturePager() {
        thumbPicture.setAlpha(1f);
        expandLayout.animate().alpha(0f).setInterpolator(new DecelerateInterpolator(2)).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                expandLayout.setVisibility(View.GONE);
                expandLayout.setAlpha(1f);
            }
        });
        isShowPager = false;
        showViews();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isShowPager) {
                closePicturePager();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void getPictures(int id) {
        BApplication.mHttpMethod.show(new Subscriber<Pictures>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Pictures pictures) {
                mToolbar.setTitle(pictures.getTitle());
                mList.clear();
                mList.addAll(pictures.getList());
                mAdapter.notifyDataSetChanged();
            }
        }, id);
    }

    private void hideViews() {
        appBarLayout.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showViews() {
        appBarLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }
}
