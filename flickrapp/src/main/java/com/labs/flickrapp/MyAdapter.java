package com.labs.flickrapp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Vector;

public class MyAdapter extends BaseAdapter {
    private Vector<String> urls;

    public MyAdapter() {
        urls = new Vector<>();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    private void add(String url){
        urls.add(url);
    }
}
