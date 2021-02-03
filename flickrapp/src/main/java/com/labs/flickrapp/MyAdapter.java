package com.labs.flickrapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.Vector;

public class MyAdapter extends BaseAdapter {
    private Vector<String> urls;
    private Context context;

    public MyAdapter(Context context) {
        urls = new Vector<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int i) {
        return urls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        /*
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.textviewlayout, viewGroup, false);
        }

        // get the TextView for item name and item description
        TextView textViewItemName = (TextView) convertView.findViewById(R.id.textView);

        //sets the text for item name and item description from the current item object
        textViewItemName.setText( urls.get(i));
        */

        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.bitmaplayout, viewGroup, false);
        }

        // get the TextView for item name and item description
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        //Set up the request for downloading the image
        ImageRequest imageRequest = new ImageRequest (urls.get(i), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        },0,0, ImageView.ScaleType.CENTER_CROP,null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Some Thing Goes Wrong", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        //Do the request
        MySingleton.getInstance(context).addToRequestQueue(imageRequest);

        // returns the view for the current row
        return convertView;
    }

    public void add(String url){
        urls.add(url);
    }
}
