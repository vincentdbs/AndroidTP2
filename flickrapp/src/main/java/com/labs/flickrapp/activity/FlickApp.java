package com.labs.flickrapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.labs.flickrapp.R;
import com.labs.flickrapp.async.AsyncFlickrJSONData;

public class FlickApp extends AppCompatActivity {
    private Button btnGetImage, btnListActivity;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flick_app);
        /*
            - Exercise 12
            The JSON file start with jsonFlickrFeed(...)

            - Exercise 13
            String : type of the parameter send at the execution
            Void : Progress of the execution
            JSONObject : Result of the execution
         */

        image = findViewById(R.id.image);
        btnGetImage = findViewById(R.id.btn_getImage);
        btnGetImage.setOnClickListener(new GetImageOnClickListener());

        btnListActivity = findViewById(R.id.btn_toListActivity);
        btnListActivity.setOnClickListener(v -> {
            Intent listActivity = new Intent(this, ListActivity.class);
            startActivity(listActivity);
        });

    }

    public class GetImageOnClickListener implements View.OnClickListener {
        public GetImageOnClickListener() {
        }

        @Override public void onClick(View v) {
             new AsyncFlickrJSONData(image).execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
        }

    }
}