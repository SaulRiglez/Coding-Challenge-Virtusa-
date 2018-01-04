package com.yoprogramo.isspasses.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.yoprogramo.isspasses.R;
import com.yoprogramo.isspasses.util.PermissionUtil;
import com.yoprogramo.isspasses.util.Prefs;
import com.yoprogramo.isspasses.constants.Commons;
import com.yoprogramo.isspasses.event.PermissionEvent;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindString;
import butterknife.ButterKnife;

public class MainActivity extends BusListenerActivity implements PermissionUtil.PermissionListener {

    @BindString(R.string.location_permission_rationale)
    protected String mLocationPermissionString;
    @BindString(R.string.allow_camera_access)
    protected String mAllowLocationAccess;
    @BindString(R.string.unable_to_use_gps)
    protected String mUnableToUseGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Prefs.init(MainActivity.this);
        ButterKnife.bind(MainActivity.this);
        startLocationPermissions();
    }


    protected void startLocationPermissions() {
        if (Prefs.getInstance().getShowLocationSettings()) {
            PermissionUtil.openSettingsIfDeniedPermission(this,
                    Commons.LOCATION_REQUEST_PERMISSIONS, mAllowLocationAccess, mUnableToUseGPS);
            Prefs.getInstance().setShowLocationSettings(false);
        } else if (PermissionUtil.appHasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION,
                Commons.LOCATION_REQUEST_PERMISSIONS,
                mLocationPermissionString,
                this)) {
            getLocation();

        }
    }


    private void getLocation() {
        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
        finish();
    }

    @Subscribe
    public void onEvent(PermissionEvent event) {
        if (event.isGranted()) {
            getLocation();
        } else {
            Prefs.getInstance().setShowLocationSettings(true);
            startLocationPermissions();

        }
    }

    @Override
    public void cancelFromRationale() {

    }
}
