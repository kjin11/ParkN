package com.example.kejin.iot_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kejin.iot_demo.data_class.DataRecord;
import com.example.kejin.iot_demo.widget.adapters.RecycleviewRecordAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by kejin on 28/03/2018.
 */

public class RentingActivity extends AppCompatActivity {
    private Toolbar toolbar_renting;
    private RecyclerView recyclerView_renting;
    private List<DataRecord> mList = new ArrayList<>();
    private List<DataRecord> mList1 = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //      requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        setContentView(R.layout.renting);
        initView();
    }
    public void initView(){
        toolbar_renting = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolbar_renting);
        toolbar_renting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView_renting = (RecyclerView) findViewById(R.id.recycle_view_renting);
        //设置布局，这里我们使用线性布局
        recyclerView_renting.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        mList1 = getAvailableLot();
//        RecycleviewRecordAdapter Myadapter = new RecycleviewRecordAdapter(mList);
//
//        recyclerView_renting.setAdapter(new RecycleviewRecordAdapter(mList));
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
        CountDownLatch latch = new CountDownLatch(1);
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
                DataSnapshot allAvailableLotSnapshot = dataSnapshot.child("Users").child(email).child("Records").child("Renting");
                //Log.d("firebase", "3");
                Iterable<DataSnapshot> availableLotSnapshots = allAvailableLotSnapshot.getChildren();
                //Log.d("firebase", "4");
                for (DataSnapshot availableLotSnapshot : availableLotSnapshots) {
                    Log.d("firebase", "5");
                    DataRecord c = availableLotSnapshot.getValue(DataRecord.class);
                    System.out.print(c);
                    currentAvailableLot.add(c);
                    mList.add(c);

                    //mCurrentAvailableLot.add(c);
                }
                RecycleviewRecordAdapter Myadapter = new RecycleviewRecordAdapter(mList);

                recyclerView_renting.setAdapter(new RecycleviewRecordAdapter(mList));

//                for (DataRecord d : currentAvailableLot) {
//                    Log.e("ForSingleValueEvent:", d.getLocation());
//                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
        return currentAvailableLot;
    }
}
