package com.shivam.solarcalculator.ui.splash;

import com.shivam.solarcalculator.di.PerActivity;
import com.shivam.solarcalculator.ui.base.MvpPresenter;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

@PerActivity
public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    boolean checkPlayServiceAvailability();

}
