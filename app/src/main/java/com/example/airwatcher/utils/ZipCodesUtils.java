package com.example.airwatcher.utils;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ZipCodesUtils {


    static public HashMap<String, LatLng> getZipCodesByBounds (LatLng northWestBound, LatLng southEastBound, int horizontalDivision, int verticalDivision, Context context) throws IOException {

        HashMap<String, LatLng> zipCodesList = new  HashMap<>();


        Geocoder geocoder = new Geocoder(context);
        double latitudeDivision = ((northWestBound.latitude - southEastBound.latitude ) / horizontalDivision);
        double longitudeDivision = ((northWestBound.longitude - southEastBound.longitude) / verticalDivision);

        List<Address> directionsList = new ArrayList<Address>();
        Set<String> zipCodes =  new HashSet<>();

        for ( int i = 0; i < horizontalDivision; ++i ) {
            for ( int j = 0; j < verticalDivision; ++j ) {
                double nextStepLatitude  = 0;
                double nextStepLongitude  = 0;
                if ( i > 0 ) nextStepLatitude = latitudeDivision * i;
                if ( j > 0 ) nextStepLongitude = longitudeDivision * j;
                 directionsList = geocoder.getFromLocation( northWestBound.latitude + nextStepLatitude, northWestBound.longitude + nextStepLongitude, 1 );

                if ( directionsList.size() > 0 && directionsList.get(0).getPostalCode() != null) {
                    zipCodes.add(directionsList.get(0).getPostalCode());
                    zipCodesList.put(directionsList.get(0).getPostalCode(), new LatLng(northWestBound.latitude + nextStepLatitude,northWestBound.longitude + nextStepLongitude ));
            }
            }
        };
        Log.d("GECODER", zipCodes.toString());


        return  zipCodesList;

    }


}
