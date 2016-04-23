package com.hua.mvp.base.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hua.mvp.base.presenter.i.IPresenter;
import com.hua.mvp.base.view.i.IView;
import com.hua.mvp.utils.GenericHelper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Fragment作为Presenter的基类
 */
public abstract class FragmentPresenterImpl<T extends IView> extends Fragment implements IPresenter<T> {

    protected T mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        create(savedInstanceState);
        try {
            mView = getViewClass().newInstance();
            View view = mView.create(inflater, container);
            mView.bindPresenter(this);
            mView.bindEvent();
            created(savedInstanceState);
            return view;
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
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

    }
}
