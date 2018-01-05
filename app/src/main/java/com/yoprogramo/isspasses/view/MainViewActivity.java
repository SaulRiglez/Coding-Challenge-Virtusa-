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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yoprogramo.isspasses.R;
import com.yoprogramo.isspasses.entities.Response;
import com.yoprogramo.isspasses.presenter.IPresenter;
import com.yoprogramo.isspasses.presenter.MainViewActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainViewActivity extends AppCompatActivity implements LocationListener, IView.ILocationActivityView {

    IPresenter.ILocationActiviy IPresenterLocationActivityInterface;


    private RecyclerView recyclerView;
    private TextView latitud;
    private TextView longitud;
    private TextView locationHeader;
    private LinearLayout failure;
    private LinearLayout success;

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
        longitud = ((TextView) findViewById(R.id.tv_longitud));
        latitud = ((TextView) findViewById(R.id.tv_latitud));
        recyclerView = ((RecyclerView) findViewById(R.id.recyclerview));
        locationHeader = ((TextView) findViewById(R.id.location_header));
        failure = ((LinearLayout) findViewById(R.id.failure));
        success = ((LinearLayout) findViewById(R.id.failure));

        initLocationManager();

        initAdapter();

    }

    private void initAdapter() {
        responsesAdapter = new ResponsesAdapter(issResponsesList, this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(responsesAdapter);

        IPresenterLocationActivityInterface = new MainViewActivityPresenter(this);
        IPresenterLocationActivityInterface.getIssPasses(issResponsesList, latitud.getText().toString(), longitud.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            Toast.makeText(this, "Location updated", Toast.LENGTH_SHORT).show();
            initializeLocationFields();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initLocationManager() {
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        // Initialize the location fields
        initializeLocationFields();
    }

    private void initializeLocationFields() {
        @SuppressLint("MissingPermission")
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
        } else {
            failure.setVisibility(View.VISIBLE);
            success.setVisibility(View.GONE);
            latitud.setText("Location not available");
            longitud.setText("Location not available");
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());

        String header = "Your are here: " + "( Lat: " + String.format("%6f", lat) + " , " + "Long: " + String.format("%6f", lng) + " )";
        locationHeader.setText(header);
        latitud.setText(String.format("%6f", lat));
        longitud.setText(String.format("%6f", lng));
        latitud.setVisibility(View.GONE);
        longitud.setVisibility(View.GONE);
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
