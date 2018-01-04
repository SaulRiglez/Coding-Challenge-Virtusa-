package com.yoprogramo.isspasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.yoprogramo.isspasses.model.ISSPass;
import com.yoprogramo.isspasses.util.RetrofitAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LocationActivity extends AppCompatActivity implements LocationListener {

//    @BindView(R.id.tv_latitud)
//    protected TextView latitud;
//    @BindView(R.id.tv_longitud)
//    protected TextView longitud;

    private TextView latitud;
    private TextView longitud;

    List<Integer> issPassesList = new ArrayList<Integer>();

    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        //ButterKnife.bind(LocationActivity.this);
        latitud = findViewById(R.id.tv_latitud);
        longitud = findViewById(R.id.tv_longitud);
        initLocationManager();


        if (latitud == null) {
            Toast.makeText(this, "Es null", Toast.LENGTH_SHORT).show();
        }


        latitud = ((TextView) findViewById(R.id.tv_longitud));
        longitud = ((TextView) findViewById(R.id.tv_latitud));


    }


    private void initLocationManager() {
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        @SuppressLint("MissingPermission")
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            latitud.setText("Location not available");
            longitud.setText("Location not available");
        }


    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        latitud.setText(String.valueOf(lat));
        longitud.setText(String.valueOf(lng));

        Observable<ISSPass> resultObservable = RetrofitAdapter.Factory.createObservable(latitud.getText().toString(),longitud.getText().toString());
        Observer issObserver = new Observer<ISSPass>() {
                @Override
            public void onCompleted() {
                    Log.d("TAG", "COmpleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG", "onError: " + e.getMessage());


            }

            @Override
            public void onNext(ISSPass issPass) {
                //iDetailFollowerView.setItems(repoUser);

                Log.d("TAG", "onNext: " + issPass.getResponse().iterator().next().getDuration());
            }

        };

        resultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(issObserver);


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
