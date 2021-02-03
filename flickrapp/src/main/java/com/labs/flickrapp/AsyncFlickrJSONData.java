package com.labs.flickrapp;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {
    private static String LOG_ASYNC = "ASYNC";

    protected void onProgressUpdate() {

    }

    @Override
    protected void onPostExecute(JSONObject result) {
        Log.i(LOG_ASYNC, result.toString());
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        URL url = null;
        try {
            //Replace http by https
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = utils.readStream(in);
                s = s.replace("jsonFlickrFeed", "");
                s = s.replace("(", "");
                s = s.replace(")", "");

                Log.i(LOG_ASYNC, s);

                //Get the authenticated value
                JSONObject result = new JSONObject(s);
                return result;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            Log.i(LOG_ASYNC, e.toString());
        } catch (IOException e) {
            Log.i(LOG_ASYNC, e.toString());
        }
        return null;
    }
}
