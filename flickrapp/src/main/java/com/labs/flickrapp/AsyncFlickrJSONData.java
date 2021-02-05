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
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {
    private ImageView imageView;

    public AsyncFlickrJSONData(ImageView img) {
        imageView = img;
    }

    protected void onProgressUpdate(ImageView imageView) {

    }

    @Override
    protected void onPostExecute(JSONObject result) {
        try {
            JSONArray arrayOfImage = result.getJSONArray("items");
            JSONObject firstImage = arrayOfImage.getJSONObject(0);
            String url = firstImage.getJSONObject("media").getString("m");
            Log.i(utils.LOG_TAG, url);

            new AsyncBitmapDownloader(imageView).execute(url);


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

                Log.i(utils.LOG_TAG, s);

                //Get the authenticated value
                JSONObject result = new JSONObject(s);
                return result;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            Log.i(utils.LOG_TAG, e.toString());
        } catch (IOException e) {
            Log.i(utils.LOG_TAG, e.toString());
        }
        return null;
    }
}
