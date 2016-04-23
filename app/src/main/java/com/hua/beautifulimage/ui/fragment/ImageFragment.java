package com.hua.beautifulimage.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.hua.beautifulimage.R;
import com.hua.beautifulimage.application.BApplication;
import com.hua.beautifulimage.entity.Gallery;
import com.hua.beautifulimage.http.subscriber.SubscriberOnNextListener;
import com.hua.beautifulimage.ui.activity.ShowImageActivity;
import com.hua.beautifulimage.ui.adapter.ImageRecyclerAdapter;
import com.hua.beautifulimage.ui.adapter.OnItemClickListener;
import com.hua.beautifulimage.ui.view.SpacesItemDecoration;
import com.hua.beautifulimage.utils.Constants;
import com.hua.beautifulimage.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@SuppressLint("ValidFragment")
public class ImageFragment extends BaseFragment {

    @Bind(R.id.sexy_recycler_view)
    RecyclerView mRecyclerView;

    private ImageRecyclerAdapter mAdapter;
    private List<Gallery.Tngou> mList;

    private SwipeRefreshLayout swipe;

    private int page, maxPage;
    private int imageType;

    public ImageFragment(SwipeRefreshLayout swipe, int imageType) {
        this.swipe = swipe;
        this.imageType = imageType;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected void init() {
        page = 1;
        if(mList == null) {
            mList = new ArrayList<>();
        }
        mAdapter = new ImageRecyclerAdapter(getActivity(), mList);
        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
            @Override
            protected void canRefresh(boolean flag) {
                swipe.setEnabled(flag);
            }

            @Override
            protected void onScrollBottom() {
                if(++page > maxPage) {
                    return;
                }
                getImages(swipe, imageType, page, true);
            }
        });


        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), ShowImageActivity.class);
                intent.putExtra(Constants.EXTRA_SHOW_PICTURE_ID, mList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void load() {
        page = 1;
        getImages(swipe, imageType, page, false);
    }

    @Override
    public void update(SwipeRefreshLayout swipe) {
        page = 1;
        getImages(swipe, imageType, page, false);
    }

    private void getImages(SwipeRefreshLayout swipe, int typeId, int page, final boolean isMore) {
        BApplication.mHttpMethod.queryGallery(getActivity(), new SubscriberOnNextListener<Gallery>() {
            @Override
            public void onNext(Gallery gallery) {
                isHasLoadedOnece = true;
                maxPage = gallery.getTotal() / Constants.DEFAULT_PAGE_ROWS;
                List<Gallery.Tngou> list = gallery.getTngou();
                if(!isMore) {
                    mList.clear();
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }else {
                    int preCount = mAdapter.getItemCount();
                    mList.addAll(list);
                    int laterCount = mList.size();
                    mAdapter.notifyItemRangeChanged(preCount + 1, laterCount);
                }
            }
        }, swipe, typeId, page);
    }
}
