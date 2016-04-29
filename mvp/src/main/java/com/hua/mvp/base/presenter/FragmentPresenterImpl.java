package com.hua.mvp.base.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hua.mvp.base.BaseFragment;
import com.hua.mvp.base.presenter.i.IPresenter;
import com.hua.mvp.base.view.i.IView;
import com.hua.mvp.utils.GenericHelper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Fragment作为Presenter的基类
 */
public abstract class FragmentPresenterImpl<T extends IView> extends BaseFragment implements IPresenter<T> {

    protected T mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        create(savedInstanceState);
        if(containView == null) {
            try {
                mView = getViewClass().newInstance();
                containView = mView.create(inflater, container);
                isPrepared = true;
                mView.bindPresenter(this);
                created(savedInstanceState);
                mView.bindEvent();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

        }
        ViewGroup parent = (ViewGroup) containView.getParent();
        if(parent != null) {
            parent.removeView(parent);
        }
        return containView;
    }

    @Override
    public Class<T> getViewClass() {
        return GenericHelper.getViewClass(getClass());
    }

    @Override
    public void create(Bundle saveInstance) {

    }

    @Override
    public void created(Bundle saveInstance) {
        init();
        lazyLoad();
    }
}
