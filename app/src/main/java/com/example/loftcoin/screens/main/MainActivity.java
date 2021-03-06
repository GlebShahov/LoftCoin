package com.example.loftcoin.screens.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.loftcoin.R;
import com.example.loftcoin.screens.converter.ConverterFragment;
import com.example.loftcoin.screens.rate.RateFragment;
import com.example.loftcoin.screens.wallets.WalletsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        bottomNavigation.setOnNavigationItemSelectedListener(navigationListener);
        bottomNavigation.setOnNavigationItemReselectedListener(menuItem -> {

        });
        if (savedInstanceState == null) {
            bottomNavigation.setSelectedItemId(R.id.menu_item_rate);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener = menuItem -> {
        switch (menuItem.getItemId()) {
            case R.id.menu_item_accounts:
                showWalletsFragment();
                return true;

            case R.id.menu_item_rate:
                showRateFragment();
                return true;

            case R.id.menu_item_converter:
                showConverterFragment();
                return true;
        }
        return false;
    };

    private void showWalletsFragment() {
        WalletsFragment fragment = new WalletsFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void showRateFragment() {
        RateFragment fragment = new RateFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void showConverterFragment() {
        ConverterFragment fragment = new ConverterFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
