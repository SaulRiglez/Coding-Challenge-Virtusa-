package com.yoprogramo.isspasses.event;

/**
 * Created by User on 1/5/2018.
 */

public class locationStatusEvent extends BaseEvent {

    boolean status;

    public locationStatusEvent(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
}
