package com.example.airwatcher.ui.map;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.airwatcher.R;
import com.example.airwatcher.model.AirNow;
import com.example.airwatcher.model.AtributosWAQI;
import com.example.airwatcher.model.DataWAQI;
import com.example.airwatcher.repository.ApiClient;
import com.example.airwatcher.repository.ApiInterfaceWAQI;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapDetailActivity extends AppCompatActivity {

    private LineChart lineChart;
    private LineDataSet lineDataSet;
    private AirNow airnow = null;
    private double latitude, longitude;
    private ProgressBar progressBar;
//    private CoordinatorLayout cLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_detail);
        setTheme(R.style.AppTheme);

//        cLayout = findViewById(R.id.clMapDetail);
        lineChart = findViewById(R.id.lineChart);
        progressBar = findViewById(R.id.progressBar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detail");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("MARKER_LAT")) {
                latitude = bundle.getDouble("MARKER_LAT");
            }
            if (bundle.containsKey("MARKER_LONG")) {
                longitude = bundle.getDouble("MARKER_LONG");
            }
        }

        getData(String.valueOf(latitude), String.valueOf(longitude));
    }

    private void getData(String latitude, String longitude) {
        showProgress(true);
        ApiInterfaceWAQI interfaceWAQI = ApiClient.getClientWAQI().create(ApiInterfaceWAQI.class);

        interfaceWAQI.getPorCoordenadas(latitude, longitude, ApiClient.WAQIToken).enqueue(new Callback<DataWAQI>() {
            @Override
            public void onResponse(Call<DataWAQI> call, Response<DataWAQI> response) {
                DataWAQI data = response.body();
                if (data != null) {
                    printLineChart(data);
                    showProgress(false);
//                    cLayout.performClick();
                }
            }

            @Override
            public void onFailure(Call<DataWAQI> call, Throwable t) {
                showProgress(false);
                Toast.makeText(getApplicationContext(),"Error Loading Data from services", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void printLineChart(DataWAQI data){
        // Creamos set de datos
        ArrayList<Entry> lineEntries = null;

        for (AtributosWAQI item : data.getData().getAttributions()) {
            lineEntries = new ArrayList<Entry>();
            lineEntries.add(new Entry((float) 1, (float) 1));

            lineDataSet = new LineDataSet(lineEntries, item.getName());

            lineDataSet.setColor(ColorTemplate.PASTEL_COLORS[((int) Math.random() * 4)], 130);

            LineData lineData = new LineData();
            lineData.addDataSet(lineDataSet);

            lineChart.setData(lineData);

            lineEntries = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showProgress (Boolean visible){
        if ( visible ) {
            this.progressBar.setVisibility(View.VISIBLE);
        } else{
            this.progressBar.setVisibility(View.GONE);
        }
    }
}
