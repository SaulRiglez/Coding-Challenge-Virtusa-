package com.yoprogramo.isspasses.controller;


import com.yoprogramo.isspasses.event.BaseEvent;
import com.yoprogramo.isspasses.event.LocationPermissionEvent;

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

    public static void onLocationPermissionGranted(boolean granted) {
        post(new LocationPermissionEvent(granted));
    }

}
