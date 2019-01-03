package com.shivam.solarcalculator.ui.base;

import com.shivam.solarcalculator.data.DataManager;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    DataManager mDataManager;
    private V mMvpView;

    public BasePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        if(mMvpView!=null)
        mMvpView = null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

}
