package com.shivam.solarcalculator.di.component;


import com.shivam.solarcalculator.di.PerActivity;
import com.shivam.solarcalculator.di.module.ActivityModule;
import com.shivam.solarcalculator.ui.dashboard.MapsActivity;
import com.shivam.solarcalculator.ui.splash.SplashActivity;
import dagger.Component;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity activity);

    void inject(MapsActivity activity);

}
