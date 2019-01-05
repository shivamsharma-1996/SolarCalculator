package com.shivam.solarcalculator.ui.dashboard;

import com.google.android.gms.maps.model.LatLng;
import com.shivam.solarcalculator.data.models.Address;
import com.shivam.solarcalculator.di.PerActivity;
import com.shivam.solarcalculator.ui.base.MvpPresenter;

import java.util.List;


/**
 * Created by Shivam Sharma on 3-1-2019.
 */

@PerActivity
public interface MapsMvpPresenter<V extends MapsMvpView> extends MvpPresenter<V> {

    void pinCurrentLocationToDB(String address, LatLng latLng);

    List<Address> getAllPinnedLocations();

}
