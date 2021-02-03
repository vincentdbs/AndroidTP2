package com.labs.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FlickApp extends AppCompatActivity {
    private Button btnGetImage;

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

        btnGetImage = findViewById(R.id.btn_getImage);
        btnGetImage.setOnClickListener(new GetImageOnClickListener());
    }

    public class GetImageOnClickListener implements View.OnClickListener {
        private String url;

        public GetImageOnClickListener() {

        }

        @Override public void onClick(View v) {
            new AsyncFlickrJSONData().execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
        }

    }
}