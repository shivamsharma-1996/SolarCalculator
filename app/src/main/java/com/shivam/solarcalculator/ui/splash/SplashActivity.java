package com.shivam.solarcalculator.ui.splash;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.shivam.solarcalculator.R;
import com.shivam.solarcalculator.ui.base.BaseActivity;
import com.shivam.solarcalculator.ui.map.MapsActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    private static final String TAG = "SplashActivity";

    @Inject
    SplashMvpPresenter<SplashMvpView> mPresenter;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getActivityComponent().inject(this);

        mPresenter.onAttach(SplashActivity.this); //i.e interface extends an interface

        if(mPresenter.checkPlayServiceAvailability()){
            openMapsActivity();
        }
    }

    @Override
    protected void setUpView() {

    }

    @Override
    public void openMapsActivity() {
        startActivity(MapsActivity.getStartIntent(SplashActivity.this));
    }

}
