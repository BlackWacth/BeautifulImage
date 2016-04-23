package com.hua.mvp.base.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hua.mvp.base.presenter.i.IPresenter;
import com.hua.mvp.base.view.i.IView;

/**
 * View层基类
 */
public abstract class ViewImpl implements IView {

    /**根据布局ID生产的视图 */
    protected View mRootView;

    /**绑定的Presenter */
    protected IPresenter mPresenter;

    @Override
    public View create(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        created();
        return mRootView;
    }

    @Override
    public void created() {

    }

    @Override
    public <V extends View> V findViewById(int id) {
        return (V) mRootView.findViewById(id);
    }

    @Override
    public void bindPresenter(IPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void bindEvent() {

    }
}
