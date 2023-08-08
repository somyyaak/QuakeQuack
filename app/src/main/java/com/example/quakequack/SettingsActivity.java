package com.example.quakequack;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String z = String.valueOf(newValue);
            if(preference instanceof ListPreference){
                ListPreference listPreference=(ListPreference) preference;
                int index=listPreference.findIndexOfValue(z);
                if(index>=0){
                    CharSequence[] label=listPreference.getEntries();
                    preference.setSummary(label[index]);
                }


            }
            else{
                preference.setSummary(z);
            }
            return true;
        }
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            Preference minMagni=findPreference(getString(R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMagni);
            Preference order=findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(order);

        }
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String prefS = sharedPreferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, prefS);
        }
    }



}
