package com.example.sjayanna.mymap;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private final LatLng LOC_SEA = new LatLng(47.6097, -122.3331);
    private final LatLng LOC_SNV =  new LatLng(37.417162,-122.02512);

    private EditText etCityQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mMap.addMarker(new MarkerOptions().position(LOC_SNV).title("find me here!"));

        etCityQuery = (EditText) findViewById(R.id.etQuery);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();



    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    public void onClickSunnyvale(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraUpdate updateFactory = CameraUpdateFactory.newLatLngZoom(LOC_SNV, 17);
        mMap.animateCamera(updateFactory);
    }


    public void onClickSeattle(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        CameraUpdate updateFactory = CameraUpdateFactory.newLatLngZoom(LOC_SEA, 12);
        mMap.animateCamera(updateFactory);
    }


    public void onClickCity(View view) {

        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = null;

        String strAddress = etCityQuery.getText().toString();

        try {
            addressList = geocoder.getFromLocationName(strAddress, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addressList != null) {
            Address location = addressList.get(0);

            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            CameraUpdate updateFactory = CameraUpdateFactory.newLatLngZoom(loc, 14);
            mMap.animateCamera(updateFactory);
        }
    }
}
