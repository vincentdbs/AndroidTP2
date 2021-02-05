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
        /*
            -- Documentation
            https://www.flickr.com/services/api/misc.urls.html

            -- Url type
            https://live.staticflickr.com/{server-id}/{id}_{secret}_{size-suffix}.jpg
        */

        try {

            JSONArray arrayOfImage = result.getJSONObject("photos").getJSONArray("photo");

            for (int i = 0; i < arrayOfImage.length() ; i++) {
                String id = arrayOfImage.getJSONObject(i).getString("id");
                String server = arrayOfImage.getJSONObject(i).getString("server");
                String secret = arrayOfImage.getJSONObject(i).getString("secret");

                String url = "https://live.staticflickr.com/" + server + "/" + id + "_" + secret + "_m.jpg";
                adapter.add(url);
                Log.i(LOG_ASYNC, "Adding to adapter url : " + url);
            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.i(utils.LOG_TAG, result.toString());
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

                Log.i(utils.LOG_TAG, "ResultJSON before \n" + s);

                //Remove the beginning of the file
                s = s.replace("jsonFlickrApi(", "");
                //Remove the last character
                s = s.substring(0, s.length() - 1);

                Log.i(utils.LOG_TAG, "ResultJSON after \n" + s);

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
