package com.example.kejin.iot_demo;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private LinearLayout mGallery;
    private int[] mImgIds;
    private LayoutInflater mInflater;
    private Button mStartDateButton;
    private Button mEndDateButton;
    private Button mRecurButton;
    private Button mAmenityButton;
    private RadioGroup gRecurGroup;
    private RadioGroup gAmenityGroup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInflater = LayoutInflater.from(this);
        initData();
        initView();

        Button toolButton = (Button) findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        View bottomSheet = (View) findViewById(R.id.bottom_sheet);
        bottomSheet.setBackgroundColor(Color.WHITE);
        mStartDateButton = (Button) this.findViewById(R.id.start_date_btn);
        mEndDateButton = (Button) this.findViewById(R.id.end_date_btn);
        mRecurButton = (Button) this.findViewById(R.id.recur_btn);
        mAmenityButton = (Button) this.findViewById(R.id.amenity_btn);
        mStartDateButton.setOnClickListener(this);
        mEndDateButton.setOnClickListener(this);
        mRecurButton.setOnClickListener(this);
        mAmenityButton.setOnClickListener(this);
        toolButton.setOnClickListener(this);
//        setContentView(R.layout.activity_main);
        Button toolbar = (Button) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        MapFragment mapFragment = new MapFragment();
//        getFragmentManager().beginTransaction().replace(R.id.content_container, mapFragment).commit();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_container, mapFragment).commit();
//        MapFragment mapFragment = new MapFragment();
//        getFragmentManager().beginTransaction().replace(R.id.content_container, mapFragment).commit();




        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_left_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.e("1","I'm out");


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
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

            MapFragment mapFragment =  new MapFragment();
            FragmentManager manager = getSupportFragmentManager();
//                    getFragmentManager().beginTransaction().replace(R.id.content_container, mapFragment).commit();
            manager.beginTransaction().replace(R.id.content_container, mapFragment).commit();
        } else if (id == R.id.renting) {
            Intent intent = new Intent(MainActivity.this, RentingActivity.class);
            startActivity(intent);
        } else if (id == R.id.lending) {
            Intent intent = new Intent(MainActivity.this, LendingActivity.class);
            startActivity(intent);

        } else if (id == R.id.notification) {
            NotificationFragment notificationFragment = new NotificationFragment();
            getFragmentManager().beginTransaction().replace(R.id.content_container, notificationFragment).commit();
        } else if (id == R.id.payment) {
            //PaymentFragment paymentFragment = new PaymentFragment();
            //getFragmentManager().beginTransaction().replace(R.id.content_container, paymentFragment).commit();
            startActivity(new Intent(this, CheckoutActivity.class));
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




    private void initData()
    {
        mImgIds = new int[] { R.drawable.icon_car_selected, R.drawable.icon_car_selected, R.drawable.icon_car_selected,
                R.drawable.icon_car_selected, R.drawable.icon_car_selected, R.drawable.icon_car_selected, R.drawable.icon_car_selected,
                R.drawable.icon_car_selected, R.drawable.icon_car_selected };
    }

    private void initView()
    {
        mGallery = (LinearLayout) findViewById(R.id.id_gallery);

        for (int i = 0; i < mImgIds.length; i++)
        {

            View view = mInflater.inflate(R.layout.activity_index_gallery_item,
                    mGallery, false);
            ImageView img = (ImageView) view
                    .findViewById(R.id.id_index_gallery_item_image);
            img.setImageResource(mImgIds[i]);
            TextView txt = (TextView) view
                    .findViewById(R.id.id_index_gallery_item_text);
            txt.setText("0.5mi");
            mGallery.addView(view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar:
                Log.e("aa","in toolbar butn");
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.closeDrawer((int)Gravity.LEFT);
                } else{
                    drawer.openDrawer((int)Gravity.LEFT);
                }
            break;
            case R.id.start_date_btn://开始时间
                Log.e("aa","in start butn");

                DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(MainActivity.this, new DateChooseWheelViewDialog.DateChooseInterface() {
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        mStartDateButton.setText(time);
                    }
                });
                startDateChooseDialog.setDateDialogTitle("Start time");
                startDateChooseDialog.showDateChooseDialog();
                break;
            case R.id.end_date_btn://结束时间
                Log.e("aa","in end butn");
                DateChooseWheelViewDialog endDateChooseDialog = new DateChooseWheelViewDialog(MainActivity.this,
                        new DateChooseWheelViewDialog.DateChooseInterface() {
                            @Override
                            public void getDateTime(String time, boolean longTimeChecked) {
                                mEndDateButton.setText(time);
                            }
                        });
                //endDateChooseDialog.setTimePickerGone(true);
                endDateChooseDialog.setDateDialogTitle("End time");
                endDateChooseDialog.showDateChooseDialog();
                break;

            case R.id.recur_btn:
                Log.e("aa", "in recur btn");
                RecurChooseDialog recurChooseDialog = new RecurChooseDialog(MainActivity.this, new RecurChooseDialog.RecurChooseInterface() {

                    @Override
                    public void getRecur(String recur) {
                        mRecurButton.setText(recur);
                    }
                });
                recurChooseDialog.showRecurChooseDialog();
                break;

            case R.id.amenity_btn:
                Log.e("aa", "in amenity btn");
                AmentityChooseDialog amentityChooseDialog= new AmentityChooseDialog(MainActivity.this, new AmentityChooseDialog.AmenityChooseInterface() {
                    @Override
                    public void getAmenity(String amenity) {
                        mAmenityButton.setText(amenity);
                    }
                });
                amentityChooseDialog.showAmenityChooseDialog();
            default:
                break;
        }

    }

}
