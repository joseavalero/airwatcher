package com.example.airwatcher.ui.settings;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.airwatcher.R;
import com.example.airwatcher.utils.DateUtils;
import com.example.airwatcher.utils.PrefsUtils;
import com.example.airwatcher.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private EditText etLatitude;
    private EditText etLongitude;
    private EditText etZipCode;
    private EditText etDistance;
    private EditText etDesiredAQI;
    private TextView tvDate;
    private Button btnAcceptSettings;
    private ImageView ivSettingsSaved;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        etLatitude = v.findViewById(R.id.etLatitude);
        etLongitude = v.findViewById(R.id.etLongitude);
        etZipCode = v.findViewById(R.id.etZipCode);
        etDistance = v.findViewById(R.id.etDistance);
        etDesiredAQI = v.findViewById(R.id.etDesiredAQI);
        tvDate = v.findViewById(R.id.tvDateSelected);
        tvDate.setText(year + "-" + month + "-" + day);

        btnAcceptSettings = v.findViewById(R.id.btnAcceptSettings);
        ivSettingsSaved = v.findViewById(R.id.ivSettingsSaved);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_settings);
        sharedPreferences = getActivity().getSharedPreferences(PrefsUtils.PREFS_KEY, Context.MODE_PRIVATE);

        btnAcceptSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String latitude = etLatitude.getText().toString();
                String longitude = etLongitude.getText().toString();
                String zipCode = etZipCode.getText().toString();
                String distance = etDistance.getText().toString();
                String desiredAqi = etDesiredAQI.getText().toString();
                String date = tvDate.getText().toString();

                if (!latitude.isEmpty() && !longitude.isEmpty() && !zipCode.isEmpty() && !distance.isEmpty() && !desiredAqi.isEmpty() && !date.isEmpty()) {
                    Boolean isValid = true;
                    // Latitude
                    double lat = Double.parseDouble(latitude);
                    if (lat < -90 || lat > 90) {
                        etLatitude.setError(getString(R.string.latitude_error));
                        isValid = false;
                    }

                    // Longitude
                    double lon = Double.parseDouble(longitude);
                    if (lon < -180 || lon > 180) {
                        etLongitude.setError(getString(R.string.longitude_error));
                        isValid = false;
                    }

                    // Zip Code
                    if (!Utils.validateZipCode(zipCode)) {
                        etZipCode.setError(getString(R.string.zip_error));
                        isValid = false;
                    }

                    // Distance
                    int dist = Integer.parseInt(distance);
                    if (dist < 0) {
                        etDistance.setError(getString(R.string.distance_error));
                        isValid = false;
                    }

                    // Desired AQI
                    int aqi = Integer.parseInt(desiredAqi);
                    if (aqi < 0 || aqi > 500) {
                        etDesiredAQI.setError(getString(R.string.aqi_error));
                        isValid = false;
                    }

                    if (isValid) {
                        sharedPreferences.edit().putString(PrefsUtils.LAT_KEY, etLatitude.getText().toString());
                        sharedPreferences.edit().putString(PrefsUtils.LONG_KEY, etLongitude.getText().toString());
                        sharedPreferences.edit().putString(PrefsUtils.ZIP_KEY, etZipCode.getText().toString());
                        sharedPreferences.edit().putString(PrefsUtils.DIST_KEY, etDistance.getText().toString());
                        sharedPreferences.edit().putInt(PrefsUtils.AQI_KEY, aqi);
                        sharedPreferences.edit().putString(PrefsUtils.DATE_KEY, date);
                        ivSettingsSaved.setVisibility(View.VISIBLE);
                    } else {
                        ivSettingsSaved.setVisibility(View.GONE);
                    }
                } else {
                    Snackbar.make(getView(), getString(R.string.settings_error), Snackbar.LENGTH_SHORT).show();
                    ivSettingsSaved.setVisibility(View.GONE);
                }
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }

        });
    }

    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        tvDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, year, month, day);
        datePickerDialog.show();

    }
}
