package com.example.loftcoin.screens.launch;

import android.os.Bundle;

import com.example.loftcoin.App;
import com.example.loftcoin.data.prefs.Prefs;
import com.example.loftcoin.screens.start.StartActivity;
import com.example.loftcoin.screens.welcome.WelcomeActivity;

import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Prefs prefs = ((App) getApplication()).getPrefs();

        if (prefs.isFirstLaunch()) {
            WelcomeActivity.start(this);
        } else {
            StartActivity.start(this);
        }
        finish();
    }
}
