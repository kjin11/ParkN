package com.example.kejin.iot_demo;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kejin.iot_demo.data_class.DataRecord;
import com.example.kejin.iot_demo.widget.adapters.RecycleviewRecordAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by kejin on 28/03/2018.
 */

public class LendingActivity extends AppCompatActivity {
    private Toolbar toolbar_lending;
    private RecyclerView recyclerView_lending;
    private Button btn_add_sharing;
    private List<DataRecord> mList  = new ArrayList<>();
    private List<DataRecord> mList1 = new ArrayList<>();
    private Toolbar toolbar_sharing;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
//    CountDownLatch latch = new CountDownLatch(1);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //      requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lending);
        //mList = getAvailableLot();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        initView();
    }
    public void initView(){
        btn_add_sharing = (Button) findViewById(R.id.btn_add_lending);
        btn_add_sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendingActivity.this,AddSpot.class);
                startActivity(intent);
            }
        });
        toolbar_sharing = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolbar_sharing);
        toolbar_sharing.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView_lending = (RecyclerView) findViewById(R.id.recycle_view_lending);
        //设置布局，这里我们使用线性布局
        recyclerView_lending.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        mList1 = getAvailableLot();
        Log.d("firebase_shring", "6");
        Log.d("firebase_shring", mList.size()+"");

//        RecycleviewRecordAdapter Myadapter = new RecycleviewRecordAdapter(mList);
//
//        recyclerView_lending.setAdapter(new RecycleviewRecordAdapter(mList));
    }



    private List<DataRecord> getAvailableLot() {
        final List<DataRecord> currentAvailableLot = new ArrayList<>();
        ChildEventListener mChildEventListener;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        //myRef.push().setValue(marker);
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                Object value = dataSnapshot.getValue(Object.class);
//                Log.e("firebase2", "2");
//                DataSnapshot allAvailableLotSnapshot = dataSnapshot.child("Available_Lot");
//                Log.e("firebase2", "3");
//                Iterable<DataSnapshot> availableLotSnapshots = allAvailableLotSnapshot.getChildren();
//                Log.e("firebase2", "4");
//                for (DataSnapshot availableLotSnapshot : availableLotSnapshots) {
//                    Log.e("firebase2", "5");
//                    showData(availableLotSnapshot);
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Log.d("firebase", "1");

                Object value = dataSnapshot.getValue(Object.class);
                //Log.d("firebase", "2");
                String email = currentUser.getEmail().toString();
                email = email.split("\\.")[0];
                DataSnapshot allAvailableLotSnapshot = dataSnapshot.child("Users").child(email).child("Records").child("Sharing");
                //Log.d("firebase_shring", "3");
                Iterable<DataSnapshot> availableLotSnapshots = allAvailableLotSnapshot.getChildren();
                Log.d("firebase_shring", "4");
                for (DataSnapshot availableLotSnapshot : availableLotSnapshots) {
                    Log.d("firebase_shring", "5");
                    //Log.d("firebase_shring", availab);

                    DataRecord c = availableLotSnapshot.getValue(DataRecord.class);
                    currentAvailableLot.add(c);
                    mList.add(c);
                }
                RecycleviewRecordAdapter Myadapter = new RecycleviewRecordAdapter(mList);

                recyclerView_lending.setAdapter(new RecycleviewRecordAdapter(mList));


//                for (DataRecord d : currentAvailableLot) {
//                    Log.e("ForSingleValueEvent:", d.getLocation());
//                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
//                latch.countDown();
            }
        });
//        try {
//            latch.await();
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
        return currentAvailableLot;
    }
}
