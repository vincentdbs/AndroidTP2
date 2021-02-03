package com.labs.tp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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
    private EditText tbLogin, tbPassword;
    private TextView tvResult;
    private final String LOG_AUTHENTICATE = "AUTHENTICATE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        btnAuthenticate = findViewById(R.id.btn_authenticate);
        tbLogin = findViewById(R.id.tb_login);
        tbPassword = findViewById(R.id.tb_password);
        tvResult = findViewById(R.id.tv_result);

        btnAuthenticate.setOnClickListener(v -> {
            new Thread(() -> {
                //Do the request in a thread because otherwise, the call block the main thread
                //Pass in parameter the content of the editText
                httpRequest(tbLogin.getText().toString(), tbPassword.getText().toString());
            }).start();
        });

    }

    private void httpRequest(String login, String password){
        URL url = null;
        try {
            //Replace http by https
            url = new URL("https://httpbin.org/basic-auth/bob/sympa");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //Add the login and password to the request
            String basicAuth = "Basic " + Base64.encodeToString((login + ":" + password).getBytes(), Base64.NO_WRAP);
            urlConnection.setRequestProperty ("Authorization", basicAuth);

            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);
                Log.i(LOG_AUTHENTICATE, s);

                //Get the authenticated value
                JSONObject result = new JSONObject(s);
                String authenticated = result.getString("authenticated");

                //Add runOnUiThread so that the change is executed in the main thread
                runOnUiThread(
                    new OneShotTask(authenticated)
                );

            } catch (JSONException e) {
                e.printStackTrace();
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

    //Inner class to get result
    class OneShotTask implements Runnable {
        private String str;

        OneShotTask(String s) { str = s; }

        @Override
        public void run() {
            tvResult.setText(str);
        }
    }

}