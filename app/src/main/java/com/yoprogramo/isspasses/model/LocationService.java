package com.yoprogramo.isspasses.model;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.yoprogramo.isspasses.controller.Bus;

public class LocationService implements LocationListener{
    private LocationManager locationManager;
    private String provider;
    Activity activity;

    public LocationService(Activity activity) {
        this.activity = activity;
    }

    public void initService() {
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        initializeLocationFields();
    }

    void initializeLocationFields() {
        @SuppressLint("MissingPermission")
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
        } else {


            Bus.locationStatus(false);
        }
    }


    @Override
    public void onLocationChanged(Location location) {

        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        Bus.onLocationChagged(lat, lng);

        Log.d("Photo", "onLocationChanged: " + lat +"    " + lng);


        //Bus.updateLocation(true, lat, lng);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {


    }
}
