package com.labs.tp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            new Thread(() -> {
                //Do the request in a thread because otherwise, the call block the main thread
                httpRequest();
            }).start();
        });

    }

    private void httpRequest(){
        URL url = null;
        try {
            //Replace http by https
            url = new URL("https://httpbin.org/basic-auth/bob/sympa");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String basicAuth = "Basic " + Base64.encodeToString("bob:sympa".getBytes(), Base64.NO_WRAP);
            urlConnection.setRequestProperty ("Authorization", basicAuth);
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
    }

    //ReadStream is a function that the developer need to implement
    private String readStream(InputStream is) {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder();
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }
            return total.toString();
        } catch (IOException e) {
            Log.i(LOG_AUTHENTICATE, e.toString());
            return "";
        }
    }
}