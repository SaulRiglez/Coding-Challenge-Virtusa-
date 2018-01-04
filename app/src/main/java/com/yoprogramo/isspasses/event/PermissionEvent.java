package com.yoprogramo.isspasses.event;

/**
 * Created by User on 1/4/2018.
 */

public class PermissionEvent extends BaseEvent {

    private boolean granted;

    public PermissionEvent(boolean granted) {
        this.granted = granted;
    }

    public boolean isGranted() {
        return granted;
    }
}
