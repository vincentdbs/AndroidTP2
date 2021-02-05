package com.labs.flickrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageRequest;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ListView myList;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        myList = (ListView) findViewById(R.id.list);
        adapter = new MyAdapter(this);
        myList.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getLocation();
    }


    private void getLocation() {
        // Fournisseurs de service
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Récupération de la localisation
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            enableLocationSettings();
            Log.i(utils.LOG_TAG, "GPS Disabled");
        }else{
            Log.i(utils.LOG_TAG, "GPS Enable");
            Location localisation = manager.getLastKnownLocation("network");
            if(localisation != null){
                double lon = localisation.getLongitude();
                double lat = localisation.getLatitude();


                Log.i(utils.LOG_TAG, "Lattitude " + lon);
                Log.i(utils.LOG_TAG, "Longitute " + lat);

                String url = "https://api.flickr.com/services/rest/?" +
                        "method=flickr.photos.search" +
                        "&license=4" +
                        "&api_key=553e52034e811e083aeb700ab9d86f99" +
                        "&has_geo=1&lat=" + lat +
                        "&lon=" + lon + "&per_page=1&format=json";

                new AsyncFlickrJSONDataForList(adapter).execute(url);

            }

        }
    }

    private void enableLocationSettings() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }

}