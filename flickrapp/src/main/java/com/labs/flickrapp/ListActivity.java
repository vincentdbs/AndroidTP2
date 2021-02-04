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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        myList = (ListView) findViewById(R.id.list);
        MyAdapter adapter = new MyAdapter(this);
        myList.setAdapter(adapter);

//        new AsyncFlickrJSONDataForList(adapter).execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");

    }

    @Override
    protected void onStart() {
        super.onStart();
        getLocation();
    }


    private void getLocation() {
        // Fournisseurs de service
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> fournisseurs = manager.getAllProviders();
//        for (String f : fournisseurs)
//            Toast.makeText(getApplicationContext(), "" + f,
//                    Toast.LENGTH_SHORT).show();

        // Récupération de la localisation
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            enableLocationSettings();
            Log.i(utils.LOG_TAG, "GPS Disabled");
        }else{
            Log.i(utils.LOG_TAG, "GPS Enable");
            Location localisation = manager.getLastKnownLocation("network");
            if(localisation != null){
                Log.i(utils.LOG_TAG, "Lattitude " + localisation.getLatitude());
                Log.i(utils.LOG_TAG, "Longitute " + localisation.getLongitude());
            }

        }
    }

    private void enableLocationSettings() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }

}