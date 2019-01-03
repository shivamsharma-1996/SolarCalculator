package com.shivam.solarcalculator.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by Shivam Sharma on 29-12-2018.
 */

public interface MvpView {
    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

}
