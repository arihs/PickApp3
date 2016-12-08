package com.shira.pickupapp3;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/*this class is unecessary*/

public class LocationManager extends AppCompatActivity implements LocationListener {
    private static final String TAG = LocationManager.class.getName();
    private android.location.LocationManager lManager;
    private String locationProvider;
    private double myLatitude;
    private double myLongitude;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("myLatitude", myLatitude);
        outState.putDouble("myLongitude", myLongitude);
    }

//once location ascertained, need to do something with that information

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_manager);
        lManager = (android.location.LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        locationProvider = lManager.getBestProvider(criteria, true);
        Log.d(TAG, "Best provider is: " + locationProvider);
    }

    private void showLocation(Location location){
        if(location!=null){
            myLatitude = location.getLatitude();
            myLongitude = location.getLongitude();

            System.out.println("latitude = " + location.getLatitude());
            System.out.println("longitude = " + location.getLongitude());
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
