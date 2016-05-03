package com.hua.mvp.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.hua.mvp.base.presenter.ActivityPresenterImpl;
import com.hua.mvp.global.C;
import com.hua.mvp.global.MApplication;
import com.hua.mvp.home.entity.Pictures;
import com.hua.mvp.home.view.PictureView;
import com.hua.mvp.http.subscriber.listener.OnNextListener;
import com.hua.mvp.utils.L;

import java.util.ArrayList;
import java.util.List;

public class PictureActivity extends ActivityPresenterImpl<PictureView> {

    private List<Pictures.Picture> mList;
    private int pictureId;

    @Override
    public void created(Bundle saveInstance) {
        super.created(saveInstance);
        Intent intent = getIntent();
        pictureId = intent.getIntExtra(C.EXTRA_PICTURE_ID, 0);

        mView.initToolbar(this);
        if(mList == null) {
            mList = new ArrayList<>();
        }
        mView.initRecyclerView(this, mList);
        loadPicture();
    }

    public void loadPicture() {
        MApplication.mHttpManager.loadPicture(pictureId, mView.getmSwipeRefreshLayout(), new OnNextListener<Pictures>() {
            @Override
            public void onNext(Pictures pictures) {
                mView.setToolbarTitle(pictures.getTitle());
                mList.clear();
                mList.addAll(pictures.getList());
                mView.getmAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if(mView.isShowPicture()) {
                mView.closePicture();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
