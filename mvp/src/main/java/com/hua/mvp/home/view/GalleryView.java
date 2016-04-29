package com.hua.mvp.home.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.hua.mvp.R;
import com.hua.mvp.base.view.ViewImpl;
import com.hua.mvp.global.C;
import com.hua.mvp.home.activity.PictureActivity;
import com.hua.mvp.home.adapter.GalleryRecyclerAdapter;
import com.hua.mvp.home.adapter.listener.OnItemClickListener;
import com.hua.mvp.home.entity.Gallery;
import com.hua.mvp.home.fragment.GalleryFragment;
import com.hua.mvp.utils.L;
import com.hua.mvp.widget.listener.RecyclerOnScrollListener;

import java.util.List;

/**
 * 图片列表，瀑布流
 */
public class GalleryView extends ViewImpl{

    private RecyclerView mRecyclerView;
    private GalleryRecyclerAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gallery;
    }

    @Override
    public void created() {
        super.created();
        mRecyclerView = findViewById(R.id.gallery_recycler_view);
    }

    public void setData(Context context, List<Gallery.Tngou> list){
        mAdapter = new GalleryRecyclerAdapter(context, list);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        mAdapter.setOnItemClickListener(new OnItemClickListener<Gallery.Tngou>() {
            @Override
            public void onItemClick(View view, int position, Gallery.Tngou tngou) {
                L.i("positon = " + position);
                Intent intent = new Intent(view.getContext(), PictureActivity.class);
                intent.putExtra(C.EXTRA_PICTURE_ID, tngou.getId());
                view.getContext().startActivity(intent);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
            @Override
            protected void canRefresh(boolean flag) {

            }

            @Override
            protected void onScrollBottom() {
                ((GalleryFragment)mPresenter).loadMore();
            }
        });
    }

    public GalleryRecyclerAdapter getAdapter() {
        return mAdapter;
    }
}
