package com.yoprogramo.isspasses.view;

import android.os.Bundle;
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
import com.yoprogramo.isspasses.event.locationStatusEvent;
import com.yoprogramo.isspasses.event.onLocationChangedEvent;
import com.yoprogramo.isspasses.model.LocationService;
import com.yoprogramo.isspasses.presenter.IPresenter;
import com.yoprogramo.isspasses.presenter.MainViewActivityPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainViewActivity extends BusListenerActivity implements IView.ILocationActivityView {

    IPresenter.ILocationActiviy IPresenterLocationActivityInterface;

    LocationService locationService;


    private RecyclerView recyclerView;
    private TextView latitud;
    private TextView longitud;
    private TextView locationHeader;
    private LinearLayout failure;
    private LinearLayout success;

    List<Response> issResponsesList = new ArrayList<>();

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

       locationService = new LocationService(this);
        locationService.initService();
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
            locationService.initService();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void notifyAdapter() {
        responsesAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(onLocationChangedEvent event) {

        final double lat = event.getLat();
        final double lng = event.getLat();

        String header = "Your are here: " + "( Lat: " + String.format("%6f", lat) + " , " + "Long: " + String.format("%6f", lng) + " )";
        locationHeader.setText(header);
        latitud.setText(String.format("%6f", lat));
        longitud.setText(String.format("%6f", lng));
        latitud.setVisibility(View.GONE);
        longitud.setVisibility(View.GONE);
        issResponsesList.clear();
        initAdapter();

    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(locationStatusEvent event) {
        if(!event.isStatus()){
            failure.setVisibility(View.VISIBLE);
            success.setVisibility(View.GONE);
            latitud.setText("Location not available");
            longitud.setText("Location not available");
        }

    }

}
