package com.example.airwatcher.ui.map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.airwatcher.R;
import com.example.airwatcher.model.AirNow;
import com.example.airwatcher.model.MarkerModel;
import com.example.airwatcher.repository.ApiClient;
import com.example.airwatcher.repository.ApiInterface;
import com.example.airwatcher.repository.OnResponseListener;
import com.example.airwatcher.repository.WAQIClient;
import com.example.airwatcher.utils.AQIUtils;
import com.example.airwatcher.utils.AirNowUtils;
import com.example.airwatcher.utils.DateUtils;
import com.example.airwatcher.utils.PrefsUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment{

    private final String TAG = MapFragment.class.getSimpleName();
    private GoogleMap map;
    private ApiInterface apiInterface;
    private SharedPreferences sharedPreferences;
    private double latitude;
    private double longitude;
    private String zipCode;
    private String distance;
    private String date;
    private int aqi;
    private ProgressBar progressBar;
    private int wAQIValueRecovered = 0;

    String codigoPostal = null;
    private double latitudeAnterior = 0;
    private double longitudAnterior = 0;
    private String privousZipcode = "";
    private HashMap<String, LatLng> zipcodeLatLngs = new HashMap<String, LatLng>();
    private ArrayList<MarkerModel> markers;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return this.getLayoutInflater().inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sharedPreferences = getActivity().getSharedPreferences(PrefsUtils.PREFS_KEY, Context.MODE_PRIVATE);

        markers = new ArrayList<MarkerModel>();

        latitude = Double.parseDouble(getActivity().getString(R.string.default_latitude));
        longitude = Double.parseDouble(getActivity().getString(R.string.default_longitude));
        zipCode = getActivity().getString(R.string.default_zip);
        distance = getActivity().getString(R.string.default_distance);
        date = DateUtils.getCurrentDate();
        aqi = Integer.parseInt(getActivity().getString(R.string.default_aqi));

        this.progressBar = view.findViewById(R.id.progressBar);

        setupMap();
    }

    private void setupMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                map = mMap;
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                map.clear(); //clear old markers

                if (sharedPreferences != null) {
                    if (sharedPreferences.contains(PrefsUtils.LAT_KEY)
                            && sharedPreferences.contains(PrefsUtils.LONG_KEY)
                            && sharedPreferences.contains(PrefsUtils.ZIP_KEY)
                            && sharedPreferences.contains(PrefsUtils.DATE_KEY)
                            && sharedPreferences.contains(PrefsUtils.AQI_KEY)) {
                        latitude = Double.parseDouble(sharedPreferences.getString(PrefsUtils.LAT_KEY, String.valueOf(latitude)));
                        longitude = Double.parseDouble(sharedPreferences.getString(PrefsUtils.LONG_KEY, String.valueOf(longitude)));
                        zipCode = sharedPreferences.getString(PrefsUtils.ZIP_KEY, zipCode);
                        distance = sharedPreferences.getString(PrefsUtils.DIST_KEY, distance);
                        date = sharedPreferences.getString(PrefsUtils.DATE_KEY, date);
                        aqi = sharedPreferences.getInt(PrefsUtils.AQI_KEY, aqi);
                    }
                }


                CameraPosition googlePlex = CameraPosition.builder()
                        //.target(new LatLng(37.9805272,-1.1621948))//Murcia
                        //.target(new LatLng(38.901846, -76.988518))//Washington D. C, zipcode 20002
                        //.target(new LatLng(39.772682, -86.164683))//Indianapolis, 46202
                        .target(new LatLng(latitude, longitude))//Philadelfia zipcode 19111
                        .zoom(10)
                        .bearing(0)
                        .tilt(45)
                        .build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1000, null);

                map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        final Double newLatitude = map.getCameraPosition().target.latitude;
                        final Double newLongitude = map.getCameraPosition().target.longitude;

                        if (latitudeAnterior == 0) {
                            latitudeAnterior = newLatitude;
                        }
                        if (longitudAnterior == 0) {
                            longitudAnterior = newLongitude;
                        }

                        Location previousLocation = new Location("previous");
                        previousLocation.setLatitude(latitudeAnterior);
                        previousLocation.setLongitude(longitudAnterior);

                        Location newLocation = new Location("new");
                        newLocation.setLatitude(newLatitude);
                        newLocation.setLongitude(newLongitude);

                        float distance = previousLocation.distanceTo(newLocation) / 1000;//Meter in Kilometer

                        if (distance < 10) return;

                        latitudeAnterior = newLatitude;
                        longitudAnterior = newLongitude;
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

                        List<Address> direccion = null;
                        try {
                            direccion = geocoder.getFromLocation(newLatitude, newLongitude, 1);
                            if (direccion.size() > 0 && direccion.get(0).getPostalCode() != null) {
                                Log.d("GECODER", "Codigo postal: " + direccion.get(0).getPostalCode());
                                privousZipcode = direccion.get(0).getPostalCode();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        if (!zipcodeLatLngs.containsKey(privousZipcode)) {

                            zipcodeLatLngs.put(privousZipcode, new LatLng(newLatitude, newLongitude));


                            //Call WAQI API
                            WAQIClient.getInstance().getAQIByLocation(newLatitude, newLongitude, new OnResponseListener() {
                                @Override
                                public void responseReceived() {
                                    //TODO
                                    Integer AQI = WAQIClient.getInstance().getAQI();
                                    LatLng latLng = new LatLng(newLatitude, newLongitude);
                                    Integer circleColor = AQIUtils.getInstance().getColor(AQI);

                                    markers.add(new MarkerModel(privousZipcode, newLatitude, newLongitude, AQI, circleColor));
                                    map.addMarker(new MarkerOptions()
                                            .position(latLng)
                                            .title("WAQI: " + AQI)
                                            .snippet("Zipcode: " + privousZipcode)).showInfoWindow();


                                    map.addCircle(new CircleOptions()
                                            .center(latLng)
                                            .radius(1000)
                                            .strokeWidth(1)
                                            .strokeColor(circleColor)
                                            .fillColor(circleColor)
                                    );
                                    wAQIValueRecovered = AQI;
                                }

                            });

                            //AirNowApi call
                            AirNowUtils.getInstance().getAirNowByZipCode(privousZipcode, date, new OnResponseListener() {
                                @Override
                                public void responseReceived() {

                                    //Map with zipcode -- AQI
                                    HashMap<String, Integer> zipCodesInfo = AirNowUtils.getInstance().getZipCodesInfo();

                                    Iterator<String> zipCodesIt = zipCodesInfo.keySet().iterator();

                                    while (zipCodesIt.hasNext()) {
                                        addAQI(zipCodesInfo, zipCodesIt);
                                    }


                                }
                            });
                        }

                    }
                });

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        double latitude = marker.getPosition().latitude;
                        double longitude = marker.getPosition().longitude;
                        // TODO: put arguments
                        /*Bundle b = new Bundle();
                        b.putDouble("MARKER_LAT", latitude);
                        b.putDouble("MARKER_LONG", longitude);
                        ((HomeActivity) getActivity()).showMarkerDetail(b);*/

                        Intent i = new Intent(getActivity(), MapDetailActivity.class);
                        i.putExtra("MARKER_LAT", latitude);
                        i.putExtra("MARKER_LONG", longitude);
                        startActivity(i);

                        return false;
                    }
                });

            }

            private void addAQI(HashMap<String, Integer> zipCodesInfo, Iterator<String> zipCodesIt) {
                String zipCode = zipCodesIt.next();

                Integer AQI = zipCodesInfo.get(zipCode).intValue();
                Integer circleColor = AQIUtils.getInstance().getColor(AQI);

                if (zipcodeLatLngs.get(zipCode) != null) {
                    markers.add(new MarkerModel(privousZipcode, zipcodeLatLngs.get(zipCode).latitude, zipcodeLatLngs.get(zipCode).longitude, AQI, circleColor));

                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(zipcodeLatLngs.get(zipCode).latitude, zipcodeLatLngs.get(zipCode).longitude))
                            .title("AQI: " + AQI)
                            .snippet("Zipcode: " + zipCode)).showInfoWindow();
                    //.snippet("AQI: "+ AirNowUtils.getInstance().getAQI()));

                    if (AQI > 0 && AQI > wAQIValueRecovered)  {
                        map.addCircle(new CircleOptions()
                                .center(new LatLng(zipcodeLatLngs.get(zipCode).latitude,zipcodeLatLngs.get(zipCode).longitude))
                                .radius(1000)
                                .strokeWidth(1)
                                .strokeColor(circleColor)
                                .fillColor(circleColor)
                        );
                    }
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_home);
    }

    private void visible(Boolean visible) {
        if (visible) {
            this.progressBar.setVisibility(View.VISIBLE);
        } else {
            this.progressBar.setVisibility(View.GONE);
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_map, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_warning:
                // TODO: Color circle
                // CLean markers
                if (markers.size() > 0) {
                    map.clear();
                    for(MarkerModel m : markers) {
                        if (m.getAQI() > Integer.parseInt(getString(R.string.default_aqi))) {
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(m.getLatitude(), m.getLongitude()))
                                    .title("AQI: " + m.getAQI())).showInfoWindow();
                            map.addCircle(new CircleOptions()
                                    .center(new LatLng(m.getLatitude(), m.getLongitude()))
                                    .radius(1000)
                                    .strokeWidth(10)
                                    .strokeColor(m.getColor())
                                    .fillColor(m.getColor())
                            );
                        }
                    }
                }

                return true;
            case R.id.action_clean:
                // TODO: Color circle
                // CLean markers
                if (markers.size() > 0) {
                    map.clear();
                    for(MarkerModel m : markers) {
                        if (m.getAQI() <= Integer.parseInt(getString(R.string.default_aqi))) {
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(m.getLatitude(), m.getLongitude()))
                                    .title("AQI: " + m.getAQI())).showInfoWindow();
                            map.addCircle(new CircleOptions()
                                    .center(new LatLng(m.getLatitude(), m.getLongitude()))
                                    .radius(1000)
                                    .strokeWidth(10)
                                    .strokeColor(m.getColor())
                                    .fillColor(m.getColor())
                            );
                        }
                    }
                }

                return true;
            default:
                break;
        }

        return false;
    }
}



