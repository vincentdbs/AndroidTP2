package com.labs.flickrapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        Log.i("AsyncFlickrJSONData", "TODO");
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

        // returns the view for the current row
        return convertView;
    }

    public void add(String url){
        urls.add(url);
    }
}
