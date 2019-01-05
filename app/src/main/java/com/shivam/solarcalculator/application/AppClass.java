package com.shivam.solarcalculator.application;

import android.app.Application;
import android.content.Context;
import com.shivam.solarcalculator.di.component.ApplicationComponent;
import com.shivam.solarcalculator.di.component.DaggerApplicationComponent;
import com.shivam.solarcalculator.di.module.ApplicationModule;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

public class AppClass extends Application {

    private static final String TAG = "AppClass";

    private ApplicationComponent mApplicationComponent;

    private static Context mContext = null;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

    }

    public static Context getAppContext(){
        return mContext;
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}