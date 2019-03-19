package com.alazz.imovie.home.base;


import androidx.annotation.StringRes;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void makeToast(@StringRes int stringId);

    void makeToast(String message);

}
