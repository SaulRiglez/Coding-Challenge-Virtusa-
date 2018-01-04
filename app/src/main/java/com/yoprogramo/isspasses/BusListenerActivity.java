package com.yoprogramo.isspasses;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.yoprogramo.isspasses.util.PermissionUtil;
import com.yoprogramo.isspasses.controller.Bus;

/**
 * Created by User on 1/4/2018.
 */

public abstract class BusListenerActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        Bus.register(this);
    }

    @Override
    protected void onStop() {
        Bus.unregister(this);
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionUtil.handlePermissionsResult(requestCode, permissions, grantResults, this);
    }
}
