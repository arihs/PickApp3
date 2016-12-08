package com.shira.pickupapp3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This class is never called as I decided to use google maps' app, rather than an in-app map.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Double lookupLatitude;
    private Double lookupLongitude;
    private final int MY_PERMISSIONS_REQUEST_CODE_LOCATION_ACCESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lookupLatitude = getIntent().getDoubleExtra("lat", 0);
        lookupLongitude = getIntent().getDoubleExtra("lng", 0);

        System.out.println("MapsActivity.onCreate() got from intent lat and long: " + lookupLatitude + ", " + lookupLongitude);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("MapsActivity.onMapReady() -----------");
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            onBackPressed();
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Add a marker at the pickupPoint and move the camera
        LatLng pickupPoint = new LatLng(lookupLatitude, lookupLongitude);
        mMap.addMarker(new MarkerOptions().position(pickupPoint).title("PickUp location")); //adds marker
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(), "You clicked the marker!",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pickupPoint)); //places 'pickupPoint' in centre of screen
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f)); //should set initial zoom level
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

}
