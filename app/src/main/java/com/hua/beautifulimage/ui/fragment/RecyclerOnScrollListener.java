package com.hua.beautifulimage.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * RecyclerView处理滑动事件
 */
public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener{

    private int mLastVisbleItem;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            int[] firstVisibleItem = null;
            int[] firstCompletelyVisibleItem = null;
            int[] lastVisibleItem = null;
            firstVisibleItem = manager.findFirstVisibleItemPositions(firstVisibleItem);
            firstCompletelyVisibleItem = manager.findFirstCompletelyVisibleItemPositions(firstCompletelyVisibleItem);
            lastVisibleItem = manager.findLastVisibleItemPositions(lastVisibleItem);
            mLastVisbleItem = Math.max(lastVisibleItem[0], lastVisibleItem[1]);

            if(firstCompletelyVisibleItem[0] == firstVisibleItem[0] && firstCompletelyVisibleItem[1] == firstVisibleItem[1]) {
                canRefresh(true);
            }else {
                canRefresh(false);
            }
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if(newState == recyclerView.SCROLL_STATE_IDLE && mLastVisbleItem + 1 == recyclerView.getAdapter().getItemCount()) {
            onScrollBottom();
        }
    }

    protected abstract void canRefresh(boolean flag);

    protected abstract void onScrollBottom();
}
