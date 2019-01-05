package com.shivam.solarcalculator.data.db;

import com.shivam.solarcalculator.data.models.Address;
import java.util.List;


/**
 * Created by Shivam Sharma on 03-01-2019.
 */
public interface DbHelper {

    void pinCurrentLocation(Address address);

    List<Address> getAllPinnedLocation();

    void destroy();
}
