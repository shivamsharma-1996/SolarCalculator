package com.shivam.solarcalculator.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

public final class AppConstants {


    public static final double INDIAN_LOCAL_OFFSET = 5.500;
    public static final int BOTTOM_CARD_COLLAPSE_DELAY = 2300; // in milliseconds
    public static final int ERROR_DIALOG_REQUEST = 9001;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
    public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9003;
    public static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    public static final float DEFAULT_ZOOM = 16f;

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}

