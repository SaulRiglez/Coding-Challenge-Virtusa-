package com.yoprogramo.isspasses.event;

/**
 * Created by User on 1/5/2018.
 */

public class onLocationChangedEvent extends BaseEvent {

    double lat;
    double lng;

    public onLocationChangedEvent(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return "onLocationChangedEvent{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
