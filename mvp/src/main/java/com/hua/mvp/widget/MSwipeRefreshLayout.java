package com.hua.mvp.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 解决ViewPager与SwipeRefreshLayout滑动冲突
 */
public class MSwipeRefreshLayout extends SwipeRefreshLayout {

    /**是否存在左右滑动的事件 */
    private boolean mDragger;
    /**手指按下时的位置 */
    private float mStartY, mStartX;
    /**发出事件的最短距离 */
    private int mTouchSlop;

    public MSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartX = ev.getX();
                mStartY = ev.getY();
                mDragger = false;
                break;

            case MotionEvent.ACTION_MOVE:
                //如果是左右滑动事件，直接返回false，不拦截
                if(mDragger) {
                    return false;
                }

                //手指结束位置
                float endX = ev.getX();
                float endY = ev.getY();

                //X和Y轴方向上滑动的距离
                float disX = Math.abs(endX - mStartX);
                float disY = Math.abs(endY - mStartY);

                if(disX > mTouchSlop && disX > disY) {
                    mDragger = true;
                    return false;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mDragger = false; //初始化左右滑动事件为false
                break;

        }
        return super.onInterceptTouchEvent(ev);
    }
}
