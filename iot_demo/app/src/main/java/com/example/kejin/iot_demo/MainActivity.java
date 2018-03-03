package com.example.kejin.iot_demo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("1","I'm in");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);



        MapFragment mapFragment = new MapFragment();
        getFragmentManager().beginTransaction().replace(R.id.content_container, mapFragment).commit();

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_left_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.e("1","I'm out");
        /*
        Fragment exFragment = (Fragment)getFragmentManager().findFragmentById(R.id.content_container);
        Button fakeButton =(Button) exFragment.getView().findViewById(R.id.fake_button_map);

        fakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailFragment detailFragment = new DetailFragment();
                getFragmentManager().beginTransaction().replace(R.id.content_container, detailFragment).commit();
            }
        });
        */

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            MapFragment mapFragment = new MapFragment();
            getFragmentManager().beginTransaction().replace(R.id.content_container, mapFragment).commit();
        } else if (id == R.id.renting) {
            RentingFragment retingFragment = new RentingFragment();
            getFragmentManager().beginTransaction().replace(R.id.content_container, retingFragment).commit();
        } else if (id == R.id.lending) {
            LendingFragment lendingFragment = new LendingFragment();
            getFragmentManager().beginTransaction().replace(R.id.content_container, lendingFragment).commit();
        } else if (id == R.id.notification) {
            NotificationFragment notificationFragment = new NotificationFragment();
            getFragmentManager().beginTransaction().replace(R.id.content_container, notificationFragment).commit();
        } else if (id == R.id.payment) {
            PaymentFragment paymentFragment = new PaymentFragment();
            getFragmentManager().beginTransaction().replace(R.id.content_container, paymentFragment).commit();
        } else if (id == R.id.setting) {
            SettingFragment settingFragment = new SettingFragment();
            getFragmentManager().beginTransaction().replace(R.id.content_container, settingFragment).commit();
        } else if (id == R.id.sign_in) {
            SignInFragment signInFragment = new SignInFragment();
            getFragmentManager().beginTransaction().replace(R.id.content_container, signInFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
