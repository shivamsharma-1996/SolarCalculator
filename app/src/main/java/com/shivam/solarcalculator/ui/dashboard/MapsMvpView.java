package com.shivam.solarcalculator.ui.dashboard;


import com.shivam.solarcalculator.ui.base.MvpView;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

public interface MapsMvpView extends MvpView {

    void pinCurrentLocaion();

    void showPinnedLocations();
}
