package com.example.android.moviedb3.fragment;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.customPreferences.ExtendedPreferenceFragmentCompat;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;

/**
 * Created by nugroho on 10/09/17.
 */

public class SettingFragment extends ExtendedPreferenceFragmentCompat
{
    public SettingFragment()
    {
        setFragmentContainerId(R.id.setting_fragment_container);
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
    }
}
