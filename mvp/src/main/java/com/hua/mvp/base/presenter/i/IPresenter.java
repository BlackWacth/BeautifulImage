package com.hua.mvp.base.presenter.i;

import android.os.Bundle;

/**
 * Presenter层接口
 */
public interface IPresenter<T> {

    /**
     * 获取当前Presenter类型
     * @return
     */
    Class<T> getViewClass();

    /**
     * View初始化之前，在此可执行一些操作
     * @param saveInstance
     */
    void create(Bundle saveInstance);

    /**
     * View初始化完毕后调用
     * @param saveInstance
     */
    void created(Bundle saveInstance);
}
