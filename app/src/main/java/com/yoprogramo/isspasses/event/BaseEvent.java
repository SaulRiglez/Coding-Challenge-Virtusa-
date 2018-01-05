package com.yoprogramo.isspasses.event;

import android.os.Bundle;

/**
 * Created by User on 1/4/2018.
 */

public class BaseEvent {
    private Bundle animationBundle;

    public Bundle getAnimationBundle() {
        return animationBundle;
    }

    public void setAnimationBundle(Bundle animationBundle) {
        this.animationBundle = animationBundle;
    }
}
