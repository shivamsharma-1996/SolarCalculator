package com.shivam.solarcalculator.di.module;

import android.app.Application;
import android.content.Context;

import com.shivam.solarcalculator.data.AppDataManager;
import com.shivam.solarcalculator.data.DataManager;
import com.shivam.solarcalculator.data.db.AppDbHelper;
import com.shivam.solarcalculator.data.db.DbHelper;
import com.shivam.solarcalculator.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper providePreferencesHelper(AppDbHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }
}
