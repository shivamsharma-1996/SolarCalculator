package com.shivam.solarcalculator.ui.dashboard;

import com.google.android.gms.maps.model.LatLng;
import com.shivam.solarcalculator.data.DataManager;
import com.shivam.solarcalculator.data.models.Address;
import com.shivam.solarcalculator.ui.base.BasePresenter;
import java.util.List;
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

    @Override
    public void pinCurrentLocationToDB(String address, LatLng latLng) {
        Address addressObj = new Address();
        addressObj.setAddress(address);
        addressObj.setLatlong(String.valueOf(latLng));
        //addressObj.setLongitude(longitude);
        getDataManager().pinCurrentLocation(addressObj);
    }

    @Override
    public List<Address> getAllPinnedLocations() {
       return getDataManager().getAllPinnedLocation();

    }
}
