package com.example.loftcoin.screens.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.loftcoin.App;
import com.example.loftcoin.R;
import com.example.loftcoin.data.prefs.Prefs;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;


public class WelcomeActivity extends AppCompatActivity {


    public static void start(Context context) {
        Intent starter = new Intent(context, WelcomeActivity.class);
        context.startActivity(starter);
    }

    public static void startInNewTask(Context context) {
        Intent starter = new Intent(context, WelcomeActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.start_btn)
    Button startBtn;

    @BindView(R.id.tabDots)
    TabLayout tabDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        final Prefs prefs = ((App) getApplication()).getPrefs();
        pager.setAdapter(new WelcomePagerAdapter(getSupportFragmentManager()));
        tabDots.setupWithViewPager(pager, true);


    }
}
