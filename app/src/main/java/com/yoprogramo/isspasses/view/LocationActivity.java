package com.yoprogramo.isspasses.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.yoprogramo.isspasses.R;
import com.yoprogramo.isspasses.model.Response;
import com.yoprogramo.isspasses.presenter.IPresenter;
import com.yoprogramo.isspasses.presenter.LocationActivityPresenter;
import com.yoprogramo.isspasses.util.ResponsesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class LocationActivity extends AppCompatActivity implements LocationListener, IView.ILocationActivityView {

    IPresenter.ILocationActiviy IPresenterLocationActivityInterface;


    private RecyclerView recyclerView;


    private TextView latitud;
    private TextView longitud;

    List<Response> issResponsesList = new ArrayList<>();

    private LocationManager locationManager;
    private String provider;

    ResponsesAdapter responsesAdapter;
    private RecyclerView.LayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        latitud = findViewById(R.id.tv_latitud);
        longitud = findViewById(R.id.tv_longitud);
        recyclerView = ((RecyclerView) findViewById(R.id.recyclerview));
        initLocationManager();


        if (latitud == null) {
            Toast.makeText(this, "Location is not available", Toast.LENGTH_SHORT).show();
        }

        latitud = ((TextView) findViewById(R.id.tv_longitud));
        longitud = ((TextView) findViewById(R.id.tv_latitud));

    }

    private void initAdapter() {
        responsesAdapter = new ResponsesAdapter(issResponsesList, this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(responsesAdapter);

        IPresenterLocationActivityInterface = new LocationActivityPresenter(this);
        IPresenterLocationActivityInterface.getIssPasses(issResponsesList, latitud.getText().toString(), longitud.getText().toString());
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

        latitud.setText(String.format("%6f", lat));
        longitud.setText(String.format("%6f", lng));
        initAdapter();
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


    @Override
    public void notifyAdapter() {
        responsesAdapter.notifyDataSetChanged();
    }
}
