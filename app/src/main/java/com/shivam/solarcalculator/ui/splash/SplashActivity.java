package com.shivam.solarcalculator.ui.splash;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.shivam.solarcalculator.R;
import com.shivam.solarcalculator.ui.base.BaseActivity;
import com.shivam.solarcalculator.ui.dashboard.MapsActivity;
import javax.inject.Inject;
import static com.shivam.solarcalculator.utils.AppConstants.ERROR_DIALOG_REQUEST;
import static com.shivam.solarcalculator.utils.AppConstants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.shivam.solarcalculator.utils.AppConstants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    private static final String TAG = "SplashActivity";

    @Inject
    SplashMvpPresenter<SplashMvpView> mPresenter;
    private AlertDialog noGpsDialog;
    private boolean mLocationPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getActivityComponent().inject(this);
        mPresenter.onAttach(SplashActivity.this); //i.e interface extends an interface

        initNoGpsAlertDialog();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(checkMapServices()){
            if(mLocationPermissionGranted){
                openMapsActivity();
            }
            else{
                requestLocationPermission();
            }
        }
    }

    @Override
    protected void setUpView() {
    }

    private boolean checkMapServices() {
        if (checkPlayServiceAvailability()) {
            if (isMapsEnabled()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPlayServiceAvailability() {
        //checking google services version & availability
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            showMessage(this.getString(R.string.map_request_failure));
        }
        return false;
    }

    private void initNoGpsAlertDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        noGpsDialog = builder.create();
    }
    private void showNoGpsDialog() {

        noGpsDialog.show();
    }

    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showNoGpsDialog();
            return false;
        }
        return true;
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;

            openMapsActivity();                               //All fine, so change the activity

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    noGpsDialog.dismiss();
                } else {
                    mLocationPermissionGranted = false;
                    requestLocationPermission();
                }
            }
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(isMapsEnabled())
                {
                    noGpsDialog.dismiss();
                    requestLocationPermission();
                }

            }
        }

    }

    @Override
    public void openMapsActivity() {
        startActivity(MapsActivity.getStartIntent(SplashActivity.this));
    }

}
