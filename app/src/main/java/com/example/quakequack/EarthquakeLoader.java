package com.example.quakequack;

import android.content.AsyncTaskLoader;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    private static String JSon;
    public EarthquakeLoader(@NonNull Context context, String url) {
        super(context);
        JSon=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Earthquake> loadInBackground() {
        if(JSon == null){
            return null;
        }
        ArrayList<Earthquake> earthquake=QueryUtils.fetchEarthquakeData(JSon);
        return earthquake;
    }
}

