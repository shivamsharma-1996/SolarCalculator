package com.shivam.solarcalculator.ui.splash;

import android.app.Dialog;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.shivam.solarcalculator.R;
import com.shivam.solarcalculator.data.DataManager;
import com.shivam.solarcalculator.ui.base.BasePresenter;
import javax.inject.Inject;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    private static final String TAG = "SplashPresenter";
    private static final int ERROR_DIALOG_REQUEST = 101;

    @Inject
    public SplashPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public boolean checkPlayServiceAvailability() {
        //checking google services version & availability
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable((SplashActivity) getMvpView());

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            getMvpView().openMapsActivity();
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog((SplashActivity) getMvpView(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            getMvpView().showMessage(((SplashActivity) getMvpView()).getString(R.string.map_request_failure));
        }
        return false;
    }

}
