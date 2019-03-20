package com.example.loftcoin;

import android.app.Application;

import com.example.loftcoin.data.api.Api;
import com.example.loftcoin.data.api.ApiInitializer;
import com.example.loftcoin.data.prefs.Prefs;
import com.example.loftcoin.data.prefs.PrefsImpl;

import timber.log.Timber;

public class App extends Application {

    private Prefs prefs;
    private Api api;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        prefs = new PrefsImpl(this);
        api = new ApiInitializer().init();
    }

    public Api getApi(){
        return api;
    }

    public Prefs getPrefs() {
        return prefs;
    }

}
