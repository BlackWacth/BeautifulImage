package com.hua.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Fragment基类
 * 懒加载
 */
public abstract class BaseFragment extends Fragment{

    private View containView;

    protected boolean isVisible;
    protected boolean isPrepared;
    protected boolean isHasLoaded;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(containView == null) {
            containView = inflater.inflate(getLayoutId(), container, false);
            isPrepared = true;
            init(containView);
            lazyLoad();
        }
        ViewGroup parent = (ViewGroup) containView.getParent();
        if(parent != null) {
            parent.removeView(parent);
        }
        return containView;
    }

    /**
     * 当前Fragment是否可见，这个方法执行在Fragment生命周期之前；
     * 且该方法只有在ViewPager中使用的时候才执行，否则不执行。
     * @param isVisibleToUser
     */
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

    /**
     * 界面可见时执行。
     */
    private void onVisible() {
        lazyLoad();
    }

    /**
     * 界面不可见执行
     */
    private void onInvisible() {

    }

    /**
     * 懒加载
     * 视图未加载好，视图不可见，视图曾加载过，都不能再继续执行后续操作。比如像网络。
     */
    protected void lazyLoad(){
        if(!isPrepared || !isVisible || isHasLoaded) {
            return ;
        }
        load();
    }

    private void init(View containView) {
        initView(containView);
        initData();
    }

    public void showToast(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 返回布局文件ID
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图组件
     * @param view 显示视图
     */
    protected abstract void initView(View view);

    /**
     * 初始化所需数据
     */
    protected abstract void initData();

    /**
     * 加载任务
     */
    protected abstract void load();

    /**
     * 下拉刷新
     * @param swipe 下拉控件
     */
    public abstract void update(SwipeRefreshLayout swipe);

}
