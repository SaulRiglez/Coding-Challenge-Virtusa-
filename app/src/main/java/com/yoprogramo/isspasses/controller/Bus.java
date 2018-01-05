package com.yoprogramo.isspasses.controller;


import com.yoprogramo.isspasses.event.BaseEvent;
import com.yoprogramo.isspasses.event.LocationPermissionEvent;
import com.yoprogramo.isspasses.event.locationStatusEvent;
import com.yoprogramo.isspasses.event.onLocationChangedEvent;

import org.greenrobot.eventbus.EventBus;

public class Bus {

    private Bus() {
    }

    public static void register(Object object) {
        EventBus.getDefault().register(object);
    }

    public static void unregister(Object object) {
        EventBus.getDefault().unregister(object);
    }

    private static void post(BaseEvent event) {
        EventBus.getDefault().post(event);
    }

    private static void postSticky(BaseEvent event) {
        EventBus.getDefault().postSticky(event);
    }

    public static void onLocationPermissionGranted(boolean granted) {
        post(new LocationPermissionEvent(granted));
    }

    public static void onLocationChagged(double lat, double lng) {
        postSticky(new onLocationChangedEvent(lat, lng));
    }

    public static void locationStatus(boolean status) {

        postSticky(new locationStatusEvent(status));
    }
}
