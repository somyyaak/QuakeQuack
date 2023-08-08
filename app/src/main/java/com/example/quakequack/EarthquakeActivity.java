package com.example.quakequack;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {
    private ArrayList<Earthquake> earthquake;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static TextView emptyView;
    private static final String JSon="https://earthquake.usgs.gov/fdsnws/event/1/query";
    private EarthquakeAdapter adapter;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id == R.id.action_settings){
            Intent settingIntent=new Intent(this,SettingsActivity.class);
            startActivity(settingIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        emptyView=(TextView)findViewById(R.id.empty);
        earthquakeListView.setEmptyView(emptyView);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        adapter=new EarthquakeAdapter(this,new ArrayList<Earthquake>());
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake quake=adapter.getItem(position);
                Uri uri= Uri.parse(quake.getMuri());
                Intent i=new Intent(Intent.ACTION_VIEW,uri);
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivity(i);
                }
            }
        });
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
            getLoaderManager().initLoader(1,null, this);
        else {
            View loadingIndicator = findViewById(R.id.progress_indicator);
            loadingIndicator.setVisibility(View.GONE);
            emptyView.setText(R.string.internet);
        }


    }


    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String minMagni=sharedPreferences.getString(getString(R.string.settings_min_magnitude_key),getString(R.string.default_mini));
        String orderby=sharedPreferences.getString(getString(R.string.settings_order_by_key),getString(R.string.settings_order_by_default));

        Uri baseuri=Uri.parse(JSon);
        Uri.Builder builder=baseuri.buildUpon();
        builder.appendQueryParameter("format","geojson");
        builder.appendQueryParameter("limit","100");
        builder.appendQueryParameter("minmag",minMagni);
        builder.appendQueryParameter("orderby",orderby);

        return new EarthquakeLoader(this,builder.toString());

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {
        View indicatorView=(ProgressBar)findViewById(R.id.progress_indicator);
        indicatorView.setVisibility(View.GONE);
        emptyView.setText(R.string.empty);
        adapter.clear();
        if(data != null   ) {
            if(!data.isEmpty()) {
                adapter.addAll(data);
                adapter.notifyDataSetChanged();
            }
        }
    }







    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        adapter.clear();
        adapter.notifyDataSetChanged();
    }


}
