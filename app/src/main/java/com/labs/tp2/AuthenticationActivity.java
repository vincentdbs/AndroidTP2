package com.labs.tp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AuthenticationActivity extends AppCompatActivity {
    private Button btnAuthenticate;
    private final String LOG_AUTHENTICATE = "AUTHENTICATE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        btnAuthenticate = findViewById(R.id.btn_authenticate);

        btnAuthenticate.setOnClickListener(v -> {
            URL url = null;
            try {
                url = new URL("http://www.android.com/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String s = readStream(in);
                    Log.i(LOG_AUTHENTICATE, s);
                } finally {
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                Log.i(LOG_AUTHENTICATE, e.toString());
            } catch (IOException e) {
                Log.i(LOG_AUTHENTICATE, e.toString());
            }
        });
    }

    //ReadStream is a function that the developer need to implement
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            Log.i(LOG_AUTHENTICATE, e.toString());
            return "";
        }
    }
}