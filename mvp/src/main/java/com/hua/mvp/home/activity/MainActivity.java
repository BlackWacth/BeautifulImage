package com.hua.mvp.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hua.mvp.R;
import com.hua.mvp.base.presenter.ActivityPresenterImpl;
import com.hua.mvp.home.view.MainView;

public class MainActivity extends ActivityPresenterImpl<MainView> {

    @Override
    public void created(Bundle saveInstance) {
        super.created(saveInstance);
        mView.init(this);
    }
}
