package com.example.airwatcher.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.airwatcher.R;
import com.example.airwatcher.model.AirNow;
import com.example.airwatcher.repository.AirNowApiInterface;
import com.example.airwatcher.ui.map.MapFragment;
import com.example.airwatcher.ui.settings.SettingsFragment;
import com.example.airwatcher.utils.AirNowUtils;
import com.google.android.material.navigation.NavigationView;

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_home) {
            fragment = new MapFragment();
        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();
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
