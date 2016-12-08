package com.shira.pickupapp3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    private String ganName = null;
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
        ganName = getIntent().getStringExtra("ganName");

        System.out.println("MapsActivity.onCreate() got from intent lat/long/ganName: " + lookupLatitude + ", " + lookupLongitude + ", " + ganName);

//        //set listener for directions 'button'
//        TextView getDirectTV = (TextView)findViewById(R.id.getDirectionsBtn);
//        getDirectTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("MapsActivity.onCreate(). you clicked 'get directions'");
//                startIntentToOpenMapsApp();
//            }
//        });
    }

    /**
     * This function starts an intent that opens google maps app and displays directions/navigation from current position to destination
     */
//    private void startIntentToOpenMapsApp() {
//        Intent googleMapIntent = new Intent();
//       //get current location:
//        Location currentLocation = mMap.getMyLocation();
//        double latitudeCurr = currentLocation.getLatitude();
//        double longitudeCurr = currentLocation.getLongitude();
//        String uri = "+http://maps.google.com/maps?saddr=" + latitudeCurr + "," + longitudeCurr + "&daddr="
//                + lookupLatitude + "," + lookupLongitude;
//        googleMapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//        googleMapIntent.setAction(Intent.ACTION_VIEW);
//        googleMapIntent.setData(Uri.parse(uri));
//        startActivity(googleMapIntent);
//    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("MapsActivity.onMapReady() -----------");
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("MapsActivity.onMapReady(). No permission granted......");
            onBackPressed();
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Add a marker at the pickupPoint and move the camera
        LatLng pickupPoint = new LatLng(lookupLatitude, lookupLongitude);


        MarkerOptions faceMarker = new MarkerOptions();
        faceMarker.position(pickupPoint)
                .title(ganName)
                .snippet("For directions, click the face and then the bottom-right arrow");
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.yayaMarker)); // this is cool, but image needs to be resized to tiny- like 30px
        if(ganName.equalsIgnoreCase("yehuda halevi school")){
            faceMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.yaya_marker));
        }
        else if (ganName.equalsIgnoreCase("gan zvia")){
            faceMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.twins_marker));
        } else if(ganName.equalsIgnoreCase("gan shir hadash")){
            faceMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.lavi_marker));
        }

        mMap.addMarker(faceMarker).showInfoWindow(); //shows marker title immediately
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pickupPoint)); //places 'pickupPoint' in centre of screen
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f)); //should set initial zoom level
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

}
