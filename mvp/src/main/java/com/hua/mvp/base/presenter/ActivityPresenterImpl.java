package com.hua.mvp.base.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hua.mvp.base.presenter.i.IPresenter;
import com.hua.mvp.base.view.i.IView;
import com.hua.mvp.utils.GenericHelper;

/**
 * Activity作为Presenter的基类
 */
public abstract class ActivityPresenterImpl<T extends IView> extends AppCompatActivity implements IPresenter<T> {

    protected T mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(savedInstanceState);
        try {
            mView = getViewClass().newInstance();
            mView.bindPresenter(this);
            setContentView(mView.create(getLayoutInflater(), null));
            mView.bindEvent();
            created(savedInstanceState);
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
