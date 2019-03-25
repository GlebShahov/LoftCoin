package com.example.loftcoin;

import android.app.Application;

import com.example.loftcoin.data.api.Api;
import com.example.loftcoin.data.api.ApiInitializer;
import com.example.loftcoin.data.db.Database;
import com.example.loftcoin.data.db.DatabaseInitializer;
import com.example.loftcoin.data.prefs.Prefs;
import com.example.loftcoin.data.prefs.PrefsImpl;

import timber.log.Timber;

public class App extends Application {

    private Prefs prefs;
    private Api api;
    private Database database;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        prefs = new PrefsImpl(this);
        api = new ApiInitializer().init();
        database = new DatabaseInitializer().init(this);
    }

    public Api getApi(){
        return api;
    }

    public Prefs getPrefs() {
        return prefs;
    }

    public Database getDatabase() {
        return database;
    }
}
