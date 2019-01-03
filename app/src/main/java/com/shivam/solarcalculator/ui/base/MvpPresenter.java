package com.shivam.solarcalculator.ui.base;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);  // attach instance of  activity-specific mvpView in onCreate();

    void onDetach();           // detach the attached instance of that mvpView in onDestroy();

}
