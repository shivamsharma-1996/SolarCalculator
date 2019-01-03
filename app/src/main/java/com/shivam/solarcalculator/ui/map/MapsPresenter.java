package com.shivam.solarcalculator.ui.map;

import com.shivam.solarcalculator.data.DataManager;
import com.shivam.solarcalculator.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

public class MapsPresenter<V extends MapsMvpView> extends BasePresenter<V> implements MapsMvpPresenter<V> {

    private static final String TAG = "MapsPresenter";

    @Inject
    public MapsPresenter(DataManager dataManager) {
        super(dataManager);
    }

}
