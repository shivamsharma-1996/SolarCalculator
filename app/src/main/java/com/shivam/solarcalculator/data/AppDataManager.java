package com.shivam.solarcalculator.data;

import android.content.Context;
import com.shivam.solarcalculator.data.db.DbHelper;
import com.shivam.solarcalculator.data.models.Address;
import com.shivam.solarcalculator.data.models.PlaceInfo;
import com.shivam.solarcalculator.di.ApplicationContext;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Created by Shivam Sharma on 3-1-2019.
 */
@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final DbHelper mDbHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper) {
        this.mContext = context;
        this.mDbHelper = dbHelper;
    }

    @Override
    public void pinCurrentLocation(Address address) {
        mDbHelper.pinCurrentLocation(address);
    }

    @Override
    public List<Address> getAllPinnedLocation() {
        return mDbHelper.getAllPinnedLocation();
    }

    @Override
    public void destroy() {
        mDbHelper.destroy();
    }

}