package com.example.android.moviedb3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataObtainedListener;
import com.example.android.moviedb3.eventHandler.OnFragmentOptionItemClick;
import com.example.android.moviedb3.fragment.SettingFragment;
import com.example.android.moviedb3.fragmentShifter.DefaultFragmentShifter;
import com.example.android.moviedb3.fragmentShifter.FragmentShifter;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Setting");

        SettingFragment settingFragment = new SettingFragment();
        settingFragment.setFragmentTitleObtainedListener(new GetFragmentTitle());

        FragmentShifter.InitializeFragment(new DefaultFragmentShifter(getSupportFragmentManager(), R.id.setting_fragment_container, settingFragment));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home :
                getSupportActionBar().setTitle("Setting");
                onBackPressed();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    private class GetFragmentTitle implements OnDataObtainedListener<String>
    {
        @Override
        public void onDataObtained(String s)
        {
            getSupportActionBar().setTitle(s);
        }
    }
}
