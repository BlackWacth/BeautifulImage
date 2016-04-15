package com.hua.beautifulimage.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hua.beautifulimage.R;
import com.hua.beautifulimage.application.BApplication;
import com.hua.beautifulimage.entity.Pictures;
import com.hua.beautifulimage.ui.adapter.ShowPictureAdapter;
import com.hua.beautifulimage.utils.Constants;

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

    private List<Pictures.Picture> mList;
    private ShowPictureAdapter mAdapter;

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

        Intent intent = getIntent();
        int id = intent.getIntExtra(Constants.EXTRA_SHOW_PICTURE_ID, 1);
        getPictures(id);
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
}
