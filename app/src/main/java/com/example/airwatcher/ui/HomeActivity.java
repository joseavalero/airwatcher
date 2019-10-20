package com.example.airwatcher.ui;

import android.os.Bundle;

import com.example.airwatcher.R;
import com.example.airwatcher.model.AirNow;
import com.example.airwatcher.repository.AirNowApiClient;
import com.example.airwatcher.repository.AirNowApiInterface;
import com.example.airwatcher.ui.map.MapFragment;
import com.example.airwatcher.utils.AirNowUtils;
import com.example.airwatcher.utils.ZipCodesUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";
    private AirNowApiInterface airNowApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));


     /*   LatLng northWestBound = new LatLng(35.620100, -90.499517);
        LatLng southEastBound = new LatLng(35.351164, -90.171542 );
        try {
            ZipCodesUtils.getZipCodesByBounds(northWestBound, southEastBound, 4, 10, this);
        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }*/
        //AirNowApi call
        //airNowApiInterface = AirNowApiClient.getClient().create(AirNowApiInterface.class);
        //getAirNowByZipCode("20002","2019-10-16");
    }

    /*AirNow Api information by zip code*/
    public void getAirNowByZipCode(String zipCode, String date) {
        String format = AirNowUtils.getInstance().getFormat();
        String distance = AirNowUtils.getInstance().getDistance();
        String api_key = AirNowUtils.getInstance().getApi_key();

        Call<List<AirNow>> call = airNowApiInterface.getAirNowByZipCode(format,zipCode,date,distance,api_key);
        call.enqueue(new Callback<List<AirNow>>() {
            @Override
            public void onResponse(Call<List<AirNow>> call, Response<List<AirNow>> response) {
                List<AirNow> airNows = response.body();
                if (airNows != null) {
                    Log.d(TAG, airNows.toString());
                    Log.d(TAG, "Date: " + airNows.get(0).getDateIssue());
                    Log.d(TAG, "AQI: " + airNows.get(0).getAQI().toString());
                }
            }

            @Override
            public void onFailure(Call<List<AirNow>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_home) {
            fragment = new MapFragment();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        if (fragment != null) {
            addFragment(fragment, false, fragment.getClass().getSimpleName());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.content_frame, fragment, tag);
        ft.commitAllowingStateLoss();
    }
}
