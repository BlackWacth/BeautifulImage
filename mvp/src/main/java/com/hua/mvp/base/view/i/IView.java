package com.hua.mvp.base.view.i;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hua.mvp.base.presenter.i.IPresenter;

/**
 * View层接口
 */
public interface IView {

    /**
     * 根据生产相应的View
     * @param inflater
     * @param container
     * @return
     */
    View create(LayoutInflater inflater, ViewGroup container);

    /**
     * Activity的onCreate()完毕后调用
     */
    void created();

    /**
     * 获取布局ID
     * @return
     */
    int getLayoutId();

    /**
     * 获取控件
     * @param id
     * @param <V>
     * @return
     */
    <V extends View> V findViewById(int id);

    /**
     * 绑定Presenter
     * @param presenter
     */
    void bindPresenter(IPresenter presenter);

    /**
     * 控件事件绑定
     */
    void bindEvent();


}
