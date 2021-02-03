package com.labs.flickrapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject> {
    private static String LOG_ASYNC = "AsyncFlickrJSONData";
    private MyAdapter adapter;

    public AsyncFlickrJSONDataForList(MyAdapter adapter) {
        this.adapter = adapter;
    }

    protected void onProgressUpdate() {

    }

    @Override
    protected void onPostExecute(JSONObject result) {
        try {
            JSONArray arrayOfImage = result.getJSONArray("items");

            for (int i = 0; i < arrayOfImage.length() ; i++) {
                JSONObject firstImage = arrayOfImage.getJSONObject(i);
                String url = firstImage.getJSONObject("media").getString("m");
                adapter.add(url);
                Log.i(LOG_ASYNC, "Adding to adapter url : " + url);
            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                //Remove the beginning of the file
                s = s.replace("jsonFlickrFeed(", "");
                //Remove the last character
                s = s.substring(0, s.length() - 1);

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
