package com.shivam.solarcalculator.data.db;

import android.content.Context;
import com.shivam.solarcalculator.data.models.Address;
import com.shivam.solarcalculator.di.ApplicationContext;
import java.util.List;
import javax.inject.Inject;


/**
 * Created by Shivam Sharma on 03-01-2019.
 */
public class AppDbHelper implements DbHelper {

//    @Inject
//    List<PlaceInfo> placeInfoRealmList;

    private DatabaseHelper databaseHelper;

    @Inject
    public AppDbHelper(@ApplicationContext Context context) {

        databaseHelper =  DatabaseHelper.getInstance(context);
    }

    @Override
    public void pinCurrentLocation(Address address) {
        databaseHelper.insertAddress(address);
    }

    @Override
    public List<Address> getAllPinnedLocation() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
