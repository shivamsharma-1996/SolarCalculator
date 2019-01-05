package com.shivam.solarcalculator.ui.splash;

import com.shivam.solarcalculator.data.DataManager;
import com.shivam.solarcalculator.ui.base.BasePresenter;
import javax.inject.Inject;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    private static final String TAG = "SplashPresenter";

    @Inject
    public SplashPresenter(DataManager dataManager) {
        super(dataManager);
    }

}
