package com.shivam.solarcalculator.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.shivam.solarcalculator.data.AppDataManager;
import com.shivam.solarcalculator.data.DataManager;
import com.shivam.solarcalculator.di.ActivityContext;
import com.shivam.solarcalculator.di.PerActivity;
import com.shivam.solarcalculator.ui.splash.SplashMvpPresenter;
import com.shivam.solarcalculator.ui.splash.SplashMvpView;
import com.shivam.solarcalculator.ui.splash.SplashPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

}
