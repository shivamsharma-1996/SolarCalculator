package com.shivam.solarcalculator.di.component;

import android.app.Application;
import android.content.Context;

import com.shivam.solarcalculator.application.AppClass;
import com.shivam.solarcalculator.data.DataManager;
import com.shivam.solarcalculator.di.ApplicationContext;
import com.shivam.solarcalculator.di.module.ApplicationModule;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(AppClass app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();

}