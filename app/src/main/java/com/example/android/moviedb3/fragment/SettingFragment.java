package com.example.android.moviedb3.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.customPreferences.ExtendedPreferenceFragmentCompat;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;

/**
 * Created by nugroho on 10/09/17.
 */

public class SettingFragment extends ExtendedPreferenceFragmentCompat
{
    ChangedPreferenceSummary changedPreferenceSummary;

    public SettingFragment()
    {
        setFragmentContainerId(R.id.setting_fragment_container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerChangedPreferenceSummary();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterChangedPreferenceSummary();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        if(getArguments() != null)
        {
            if(getArguments().containsKey(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT))
            {
                setPreferencesFromResource(R.xml.app_preferences, rootKey);
            }
        }

        else
        {
            addPreferencesFromResource(R.xml.app_preferences);
        }

        SetInitialPreferenceSummary();
    }

    private void registerChangedPreferenceSummary()
    {
        changedPreferenceSummary = new ChangedPreferenceSummary();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(changedPreferenceSummary);
    }

    private void unregisterChangedPreferenceSummary()
    {
        if(changedPreferenceSummary != null)
        {
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(changedPreferenceSummary);
        }
    }

    private void SetInitialPreferenceSummary()
    {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();
        int preferenceCount = preferenceScreen.getPreferenceCount();

        for(int a = 0; a < preferenceCount; a++)
        {
            Preference preference = preferenceScreen.getPreference(a);

            if(preference instanceof ListPreference)
            {
                String value = sharedPreferences.getString(preference.getKey(), "");
                ListPreference listPreference = (ListPreference) preference;

                int prefIndex = listPreference.findIndexOfValue(value);
                if(prefIndex >= 0)
                {
                    listPreference.setSummary(listPreference.getEntries()[prefIndex]);
                }
            }
        }
    }

    private class ChangedPreferenceSummary implements SharedPreferences.OnSharedPreferenceChangeListener
    {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
        {
            Preference preference = findPreference(key);

            if(preference != null)
            {
                if(preference instanceof ListPreference)
                {
                    String value = sharedPreferences.getString(preference.getKey(), "");
                    ListPreference listPreference = (ListPreference) preference;

                    int prefIndex = listPreference.findIndexOfValue(value);
                    if(prefIndex >= 0)
                    {
                        listPreference.setSummary(listPreference.getEntries()[prefIndex]);
                    }
                }
            }
        }
    }
}
