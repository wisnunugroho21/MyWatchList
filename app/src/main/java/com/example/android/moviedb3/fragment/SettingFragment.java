package com.example.android.moviedb3.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.activity.TVMovieListActivity;
import com.example.android.moviedb3.customPreferences.ExtendedPreferenceFragmentCompat;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;
import com.example.android.moviedb3.services.GetMovieListProgressService;
import com.example.android.moviedb3.services.GetMovieListRepeatingService;
import com.example.android.moviedb3.services.JobSchedulerUtils;
import com.example.android.moviedb3.services.PeriodicNetworkJobScheduler;
import com.example.android.moviedb3.sharedPreferences.DefaultBooleanStatePreference;
import com.example.android.moviedb3.sharedPreferences.DefaultStringStatePreference;
import com.example.android.moviedb3.sharedPreferences.PreferencesUtils;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;

/**
 * Created by nugroho on 10/09/17.
 */

public class SettingFragment extends ExtendedPreferenceFragmentCompat
{
    ChangedPreferenceSummary changedPreferenceSummary;
    SettingChangedListener settingChangedListener;

    public SettingFragment()
    {
        setFragmentContainerId(R.id.setting_fragment_container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerSettingChangedListener();
        registerChangedPreferenceSummary();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterSettingChangedListener();
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

            if(preference instanceof PreferenceCategory)
            {
                PreferenceCategory preferenceCategory = (PreferenceCategory) preference;
                int preferenceCountCategory = preferenceCategory.getPreferenceCount();

                for(int b = 0; b < preferenceCountCategory; b++)
                {
                    Preference preferenceChildCategory = preferenceCategory.getPreference(b);

                    if(preferenceChildCategory instanceof ListPreference)
                    {
                        String value = sharedPreferences.getString(preferenceChildCategory.getKey(), "");
                        ListPreference listPreference = (ListPreference) preferenceChildCategory;

                        int prefIndex = listPreference.findIndexOfValue(value);
                        if(prefIndex >= 0)
                        {
                            listPreference.setSummary(listPreference.getEntries()[prefIndex]);
                        }
                    }
                }
            }

            else if(preference instanceof ListPreference)
            {
                String value = sharedPreferences.getString(preference.getKey(), "");
                ListPreference listPreference = (ListPreference) preference;

                int prefIndex = listPreference.findIndexOfValue(value);
                if(prefIndex >= 0)
                {
                    listPreference.setSummary(listPreference.getEntries()[prefIndex]);
                }
            }

            if(preference instanceof SwitchPreference)
            {
                if(preference.getKey().equals(getString(R.string.period_update_key)))
                {
                    Preference updatePeriodCategoryPreferences = getPreferenceScreen().findPreference(getString(R.string.period_update_category_key));

                    if(updatePeriodCategoryPreferences instanceof PreferenceCategory)
                    {
                        boolean isPeriodUpdateTurnOn = PreferencesUtils.GetData(new DefaultBooleanStatePreference(getContext()), preference.getKey(), false);
                        updatePeriodCategoryPreferences.setEnabled(isPeriodUpdateTurnOn);
                    }
                }
            }
        }
    }

    private void startGetDataService()
    {
        Intent intent = new Intent(getContext(), GetMovieListProgressService.class);
        getContext().startService(intent);
    }

    private void registerSettingChangedListener()
    {
        settingChangedListener = new SettingChangedListener();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.registerOnSharedPreferenceChangeListener(settingChangedListener);
    }

    private void unregisterSettingChangedListener()
    {
        if(settingChangedListener != null)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(settingChangedListener);
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

            if(key.equals(getString(R.string.period_update_key)))
            {
                Preference updatePeriodCategoryPreferences = getPreferenceScreen().findPreference(getString(R.string.period_update_category_key));

                if(updatePeriodCategoryPreferences instanceof PreferenceCategory)
                {
                    boolean isPeriodUpdateTurnOn = PreferencesUtils.GetData(new DefaultBooleanStatePreference(getContext()), key, false);
                    updatePeriodCategoryPreferences.setEnabled(isPeriodUpdateTurnOn);
                }
            }

        }
    }

    private class SettingChangedListener implements SharedPreferences.OnSharedPreferenceChangeListener
    {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
        {
            Context context = getContext();

            if(key.equals(getString(R.string.content_language_key)) || key.equals(getString(R.string.region_key)))
            {
                startGetDataService();
            }

            else if(key.equals(getString(R.string.period_update_key)) || key.equals(getString(R.string.update_period_key)) || key.equals(getString(R.string.only_on_wifi_key)))
            {
                boolean isPeriodUpdateTurnOn = PreferencesUtils.GetData(new DefaultBooleanStatePreference(context), getString(R.string.period_update_key), false);

                if(isPeriodUpdateTurnOn)
                {
                    SetPeriodUpdateAsyncTask setPeriodUpdateAsyncTask = new SetPeriodUpdateAsyncTask();
                    setPeriodUpdateAsyncTask.execute();
                }

                else
                {
                    UnSetPeriodUpdateAsyncTask unSetPeriodUpdateAsyncTask = new UnSetPeriodUpdateAsyncTask();
                    unSetPeriodUpdateAsyncTask.execute();
                }
            }
        }
    }

    private class SetPeriodUpdateAsyncTask extends AsyncTask<Void, Void, Boolean>
    {
        Context context;

        @Override
        protected Boolean doInBackground(Void... params)
        {
            context = getContext();

            String updatePeriodString = PreferencesUtils.GetData(new DefaultStringStatePreference(context), getString(R.string.update_period_key), getString(R.string.update_period_values_12_hours));
            boolean isWifiOnly = PreferencesUtils.GetData(new DefaultBooleanStatePreference(context), getString(R.string.only_on_wifi_key), false);

            int updatePeriodSecond = 0;
            if(updatePeriodString.equals(getString(R.string.update_period_label_4_hours)))
            {
                updatePeriodSecond = 14400;
            }

            else if(updatePeriodString.equals(getString(R.string.update_period_label_6_hours)))
            {
                updatePeriodSecond = 21600;
            }

            else if(updatePeriodString.equals(getString(R.string.update_period_label_8_hours)))
            {
                updatePeriodSecond = 28800;
            }

            else if(updatePeriodString.equals(getString(R.string.update_period_values_12_hours)))
            {
                updatePeriodSecond = 43200;
            }

            return JobSchedulerUtils.doJobScheduling(new PeriodicNetworkJobScheduler<>(context, updatePeriodSecond, isWifiOnly, MovieDBKeyEntry.JobSchedulerID.PERIODIC_NETWORK_JOB_KEY, GetMovieListRepeatingService.class))
                    == FirebaseJobDispatcher.SCHEDULE_RESULT_SUCCESS;
        }

        @Override
        protected void onPostExecute(Boolean bool)
        {
            if(bool)
            {
                Toast.makeText(context, R.string.success_update_period_toas_message, Toast.LENGTH_SHORT).show();
            }

            else
            {
                Toast.makeText(context, R.string.fail_update_period_toas_message, Toast.LENGTH_SHORT).show();

                PreferencesUtils.SetData(new DefaultBooleanStatePreference(context),false, getString(R.string.period_update_key));
                PreferencesUtils.SetData(new DefaultStringStatePreference(context), getString(R.string.update_period_key), getString(R.string.update_period_values_12_hours));
                PreferencesUtils.SetData(new DefaultBooleanStatePreference(context), false, getString(R.string.only_on_wifi_key));
                PreferencesUtils.SetData(new DefaultStringStatePreference(context), getString(R.string.type_notification_key), getString(R.string.normal_led_notification_value));
            }
        }

    }

    private class UnSetPeriodUpdateAsyncTask extends AsyncTask<Void, Void, Boolean>
    {
        Context context;

        @Override
        protected Boolean doInBackground(Void... params)
        {
            context = getContext();
            return JobSchedulerUtils.cancelJobScheduling(new PeriodicNetworkJobScheduler<>(context, MovieDBKeyEntry.JobSchedulerID.PERIODIC_NETWORK_JOB_KEY)) == FirebaseJobDispatcher.SCHEDULE_RESULT_SUCCESS;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if(aBoolean)
            {
                Toast.makeText(context, R.string.success_cancel_update_period_toas_message, Toast.LENGTH_SHORT).show();
            }

            else
            {
                Toast.makeText(context, R.string.fail_cancel_update_period_toas_message, Toast.LENGTH_SHORT).show();
                PreferencesUtils.SetData(new DefaultBooleanStatePreference(context),true, getString(R.string.period_update_key));
            }
        }
    }
}
