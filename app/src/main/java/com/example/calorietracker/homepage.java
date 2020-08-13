package com.example.calorietracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

public class homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView QQ;
    String qwe;
    private long mExitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.inflateHeaderView(R.layout.nav_header_homepage).findViewById(R.id.tex);
        QQ = view.findViewById(R.id.tex);
        SharedPreferences myhealth = getSharedPreferences("fname",MODE_PRIVATE);
        qwe = myhealth.getString("firstNA",null);
        QQ.setText(qwe);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_frame, new homepagefrag() ).commit();
      //  getSupportActionBar().setTitle("Navigation Drawer");

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment nextFragment = null;
        switch (id) {
            case R.id.nav_home:
                nextFragment = new homepagefrag();
                break;
            case R.id.nav_MDD:
                nextFragment = new my_daily_diet();
                break;
            case R.id.nav_Steps:
                nextFragment = new steps();
                break;
            case R.id.nav_CalTracker:
                nextFragment = new cal_tracker();
                break;
            case R.id.nav_Report:
                nextFragment = new report();
                break;
            case R.id.nav_bar_Report:
                nextFragment = new report_bar();
                break;
            case R.id.nav_Map:
                nextFragment = new Map();
                break;
        }
        FragmentManager manager1 = getSupportFragmentManager();
        manager1.beginTransaction().addToBackStack(null).replace(R.id.content_frame, nextFragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {                      //>2000 mistake

                Toast.makeText(this, "PRESS twice to restart", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
