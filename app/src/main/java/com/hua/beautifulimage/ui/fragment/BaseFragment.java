package com.hua.beautifulimage.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 
 */
public abstract class BaseFragment extends Fragment{

    private View contentView;

    protected boolean isVisible;
    protected boolean isPrepared;
    protected boolean isHasLoadedOnece;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(contentView == null) {
            contentView = inflater.inflate(getLayoutId(), container, false);
            isPrepared = true;
            ButterKnife.bind(this, contentView);
            init();
            lazyLoad();
        }
        ViewGroup parent = (ViewGroup) contentView.getParent();
        if(parent != null) {
            parent.removeView(contentView);
        }
        return contentView;
    }

    protected abstract int getLayoutId();

    protected abstract void init();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad(){
        if(!isPrepared || !isVisible || isHasLoadedOnece) {
            return ;
        }
        load();
    }

    protected abstract void load();

    protected void onInvisible() {

    }

    /**
     * 是否需要下拉刷新
     * @return
     */
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//        if(!isHasLoadedOnece) {
//            frame.autoRefresh(true);
//        }
//        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        return false;
    }

    /**
     * 在支持下拉刷新的情况下，数据刷新
     * @param swipe
     */
    public abstract void update(SwipeRefreshLayout swipe);

    public void showToast(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
