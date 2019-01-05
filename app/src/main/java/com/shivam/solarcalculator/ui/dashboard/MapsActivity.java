package com.shivam.solarcalculator.ui.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.shivam.solarcalculator.R;
import com.shivam.solarcalculator.data.models.Date;
import com.shivam.solarcalculator.data.models.PlaceInfo;
import com.shivam.solarcalculator.utils.PhaseTimeCalculator;
import com.shivam.solarcalculator.ui.base.BaseActivity;
import com.shivam.solarcalculator.ui.dashboard.bookmarks.BookmarksDialog;
import com.shivam.solarcalculator.utils.KeyboardUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;


import static com.shivam.solarcalculator.utils.AppConstants.BOTTOM_CARD_COLLAPSE_DELAY;
import static com.shivam.solarcalculator.utils.AppConstants.DEFAULT_ZOOM;
import static com.shivam.solarcalculator.utils.AppConstants.INDIAN_LOCAL_OFFSET;
import static com.shivam.solarcalculator.utils.AppConstants.LAT_LNG_BOUNDS;
import static com.shivam.solarcalculator.utils.CommonUtils.dpToPx;
import static com.shivam.solarcalculator.utils.DateUtils.getCalenderDate;
import static com.shivam.solarcalculator.utils.DateUtils.getCustomDateObject;
import static com.shivam.solarcalculator.utils.DateUtils.getNextDate;
import static com.shivam.solarcalculator.utils.DateUtils.getPreviousDate;

public class MapsActivity extends BaseActivity implements MapsMvpView,
        OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraIdleListener,
        View.OnClickListener {

    private static final String TAG = "MapsActivity";

    @Inject
    MapsMvpPresenter<MapsMvpView> mPresenter;

    private PhaseTimeCalculator phaseTimeCalculator;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    //place autocomplete
    private AutoCompleteTextView mSearchView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private PlaceInfo mPlace;
    private FloatingActionButton mFabCurrentLocation;
    private TextView mAddressBar;

    private String currentLocationAddress = "";

    //BottomSheet widgets
    private FloatingActionButton mFabPlay, mFabPreviousDate, mFabNextDate;
    private TextView mCalculatorDate, mSunriseTime, mSunsetime, mMoonriseTime, mMoonSetTime;
    private BottomSheetBehavior mBottomSheetBehaviour;
    private View bottomSheet;


    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 3000 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 30000000;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MapsActivity.class);
        return intent;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getActivityComponent().inject(this);

        mPresenter.onAttach(MapsActivity.this); //i.e interface extends an interface

        mAddressBar = findViewById(R.id.tv_address_bar);
        mCalculatorDate = findViewById(R.id.tv_date);
        mFabPlay = findViewById(R.id.fab_play);
        mFabPreviousDate = findViewById(R.id.fab_previous_date);
        mFabNextDate = findViewById(R.id.fab_next_date);
        mSunriseTime = findViewById(R.id.tv_sunrise);
        mSunsetime = findViewById(R.id.tv_sunset);
        mMoonriseTime = findViewById(R.id.tv_moonrise);
        mMoonSetTime = findViewById(R.id.tv_moonset);
        mSearchView = findViewById(R.id.act_input_search);
        mFabCurrentLocation = findViewById(R.id.fab_current_location);
        bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehaviour = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehaviour.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        initMap();
        init();
    }

    @Override
    protected void setUpView() {

    }

    public void setPhaseTime(Date date,double latitude, double longiude) {
        mSunriseTime.setText(phaseTimeCalculator.calcSunrise(date, INDIAN_LOCAL_OFFSET,latitude, longiude));
        mSunsetime.setText(phaseTimeCalculator.calcSunset(date, INDIAN_LOCAL_OFFSET,latitude, longiude));
    }

    @Override
    protected void onStart() {
        super.onStart();
        phaseTimeCalculator = new PhaseTimeCalculator();
        mCalculatorDate.setText(getCalenderDate());

        if (mBottomSheetBehaviour != null) {
            mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }, BOTTOM_CARD_COLLAPSE_DELAY);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void init() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);
        mSearchView.setAdapter(mPlaceAutocompleteAdapter);
        mSearchView.setOnItemClickListener(mAutocompleteClickListener);

        mFabPlay.setOnClickListener(MapsActivity.this);
        mFabNextDate.setOnClickListener(MapsActivity.this);
        mFabPreviousDate.setOnClickListener(MapsActivity.this);
        mFabCurrentLocation.setOnClickListener(MapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        MapsActivity.this.initCameraListeners();
        MapsActivity.this.getDeviceLocation();
    }

    private void initCameraListeners() {
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
    }

    private void getDeviceLocation() {
        try {
            final Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();
                        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                    } else {
                        Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mMap.clear();

        setPhaseTime(getCustomDateObject(mCalculatorDate.getText().toString()),latLng.latitude, latLng.longitude);   // setting phase time

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        animateCameraToMarker(latLng);
        KeyboardUtils.hideSoftInput(MapsActivity.this);

    }

    private void animateCameraToMarker(LatLng latLng) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(latLng);
        LatLngBounds bounds = builder.build();
        try {
            int padding = (int) dpToPx(30, this); // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            // Map not loaded yet
            // begin new code:
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            mMap.animateCamera(cu);
        }
    }

    private String getGeoLocationAddress(LatLng latLng) {
            List<Address> addresses = new ArrayList<>();
            try {
                Geocoder geo = new Geocoder(MapsActivity.this);
                addresses = geo.getFromLocation(latLng.latitude, latLng.longitude, 1);
            } catch (IOException e) {
                Log.e(TAG, "geoLocate: IOException: " + e.getMessage());

            }
            if (addresses.size() > 0) {
                //setTitle(addresses.get(0).getAddressLine(0));
            }
        return addresses.get(0).getAddressLine(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_pin_location) {
            MapsActivity.this.pinCurrentLocaion();
        } else if (id == R.id.menu_pinned_locations) {
            MapsActivity.this.showPinnedLocations();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        LatLng midLatlng = mMap.getCameraPosition().target;

        switch (v.getId()) {
            case R.id.fab_current_location:
                getDeviceLocation();
                break;
            case R.id.fab_play:
                mCalculatorDate.setText(getCalenderDate());
                setPhaseTime(getCustomDateObject(mCalculatorDate.getText().toString()), midLatlng.latitude, midLatlng.longitude);
                break;

            case R.id.fab_previous_date:
                mCalculatorDate.setText(getPreviousDate(mCalculatorDate.getText().toString()));
                setPhaseTime(getCustomDateObject(mCalculatorDate.getText().toString()), midLatlng.latitude, midLatlng.longitude);
                break;

            case R.id.fab_next_date:
                mCalculatorDate.setText(getNextDate((mCalculatorDate.getText().toString())));
                setPhaseTime(getCustomDateObject(mCalculatorDate.getText().toString()), midLatlng.latitude, midLatlng.longitude);
                break;
        }
    }

    @Override
    public void pinCurrentLocaion() {
        LatLng currentLatlong = mMap.getCameraPosition().target;
        mPresenter.pinCurrentLocationToDB(getGeoLocationAddress(currentLatlong), currentLatlong);
        showMessage(getString(R.string.save_pin_success));
    }

    @Override
    public void showPinnedLocations() {
        BookmarksDialog bookmarksDialog = new BookmarksDialog();
        bookmarksDialog.setOnItemClickListener(new BookmarksDialog.OnItemSelectListener() {
            @Override
            public void onItemSelected(com.shivam.solarcalculator.data.models.Address selectedAddress) {

                selectedAddress.getLatlong();
                moveCamera(selectedAddress.getRealLatlong(), DEFAULT_ZOOM);
                Toast.makeText(MapsActivity.this, "show", Toast.LENGTH_SHORT).show();
            }
        });
        bookmarksDialog.show(this.getSupportFragmentManager(), bookmarksDialog.getClass().getSimpleName());
    }

    /*
        --------------------------- google places API autocomplete suggestions starts -----------------
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            KeyboardUtils.hideSoftInput(MapsActivity.this);

            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(i);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };


    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                places.release();
                return;
            }
            final Place place = places.get(0);

            try {
                mPlace = new PlaceInfo();
                mPlace.setAddress(place.getAddress().toString());
                mPlace.setLatitude(place.getLatLng().latitude);
                mPlace.setLatitude(place.getLatLng().longitude);
            } catch (NullPointerException e) {
                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage());
            }
            moveCamera(new LatLng(place.getViewport().getCenter().latitude, place.getViewport().getCenter().longitude), DEFAULT_ZOOM);
            places.release();  //for memory leak
        }
    };
/*
        --------------------------- google places API autocomplete suggestions ends -----------------
     */

    @Override
    public void onCameraIdle() {
        LatLng midLatLng = mMap.getCameraPosition().target;
        new getLocationAddress(midLatLng).execute();
    }

    @Override
    public void onCameraMoveStarted(int i) {
        if (i == REASON_GESTURE)
            if (mBottomSheetBehaviour != null)
                mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }



    //To make the marker-location realtime
    protected void startLocationUpdates() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
        new getLocationAddress(new LatLng(location.getLatitude(), location.getLongitude())).execute();
    }

    //remove updates
    @Override
    protected void onStop() {
        super.onStop();
        mFusedLocationProviderClient.removeLocationUpdates(new LocationCallback());
    }


    //asynctask to get market location address in seperate thread
    class getLocationAddress extends AsyncTask<String, Void, String>
    {
        double latitude;
        double longitude;

        public getLocationAddress(LatLng currentLocation) {
            latitude = currentLocation.latitude;
            longitude = currentLocation.longitude;
        }

        @Override
        protected String doInBackground(String... strings) {
            Geocoder geocoder;
            List<Address> addresses = new ArrayList<Address>();
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses.size() > 0) {
                if (addresses.get(0).getAddressLine(0) != null)
                    currentLocationAddress = addresses.get(0).getAddressLine(0); // If any additional tvAddress line present than only, check with max available tvAddress lines by getMaxAddressLineIndex()
            }
            return currentLocationAddress;
            }

        @Override
        protected void onPostExecute(String s) {
                mAddressBar.setText(currentLocationAddress);
                setPhaseTime(getCustomDateObject(mCalculatorDate.getText().toString()),latitude, longitude);
        }
    }

}

