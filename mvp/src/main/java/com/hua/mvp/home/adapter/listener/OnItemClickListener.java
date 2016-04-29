package com.hua.mvp.home.adapter.listener;

import android.view.View;

/**
 * Created by ZHONG WEI  HUA on 2016/4/18.
 */
public interface OnItemClickListener<T> {

    public void onItemClick(View view, int position, T t);

}
