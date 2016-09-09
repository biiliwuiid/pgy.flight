package com.moji.daypack.ui.base;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/8/16
 * Time: 11:49 PM
 * Desc: BaseView
 */
public interface BaseView<P extends BasePresenter> {

    void setPresenter(P presenter);
}
