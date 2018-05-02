package com.example.kejin.iot_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kejin.iot_demo.data_class.DataRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by kejin on 28/03/2018.
 */

public class AddSpot extends AppCompatActivity implements ViewStub.OnClickListener {
    private Button start_btn;
    private Button end_btn;
    private Button amenity_btn;
    private Button comfirm_btn;
    private Toolbar toolbar;
    private EditText price_textview;
    private EditText location_textview;
    private EditText notes_textview;
    private String start_time;
    private String end_time;
    private String amenity;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_spot);
        start_btn = (Button)findViewById(R.id.addSpot_start_date_btn);
        end_btn = (Button) findViewById(R.id.addSpot_end_date_btn);
        amenity_btn =(Button)findViewById(R.id.addSpot_amenity_btn);
        comfirm_btn =(Button)findViewById(R.id.btn_add_spot);
        toolbar =(Toolbar)findViewById(R.id.toolbar_add_spot);
        price_textview = (EditText)findViewById(R.id.price_add_spot);
        location_textview = (EditText)findViewById(R.id.location_add_spot);
        notes_textview = (EditText) findViewById(R.id.notes_add_spot);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        start_time = "2018 3-5 10:30";
        end_time = "2018 3-5 11:40";
        amenity = "Garage-covered";

        start_btn.setOnClickListener(this);
        end_btn.setOnClickListener(this);
        comfirm_btn.setOnClickListener(this);
        comfirm_btn.setOnClickListener(this);
        amenity_btn.setOnClickListener(this);




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addSpot_start_date_btn://开始时间
                Log.e("aa","in start butn");

                DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(AddSpot.this, new DateChooseWheelViewDialog.DateChooseInterface() {
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                       start_btn.setText(time);
                       start_time = time;
                    }
                });
                startDateChooseDialog.setDateDialogTitle("Start time");
                startDateChooseDialog.showDateChooseDialog();
                break;
            case R.id.addSpot_end_date_btn://结束时间
                Log.e("aa","in end butn");
                DateChooseWheelViewDialog endDateChooseDialog = new DateChooseWheelViewDialog(AddSpot.this,
                        new DateChooseWheelViewDialog.DateChooseInterface() {
                            @Override
                            public void getDateTime(String time, boolean longTimeChecked) {
                                end_btn.setText(time);
                                end_time = time;
                            }
                        });
                //endDateChooseDialog.setTimePickerGone(true);
                endDateChooseDialog.setDateDialogTitle("End time");
                endDateChooseDialog.showDateChooseDialog();
                break;

            case R.id.addSpot_amenity_btn:
                Log.e("aa", "in amenity btn");
                AmentityChooseDialog amentityChooseDialog= new AmentityChooseDialog(AddSpot.this, new AmentityChooseDialog.AmenityChooseInterface() {
                    @Override
                    public void getAmenity(String amenity_add) {
                        amenity_btn.setText(amenity_add);
                         amenity = amenity_add;
                    }
                });
                amentityChooseDialog.showAmenityChooseDialog();
                break;

            case R.id.btn_add_spot:
                int price = Integer.parseInt(price_textview.getText().toString());
                String location = location_textview.getText().toString();
                String notes = notes_textview.getText().toString();
                String email = currentUser.getEmail().toString();
                email = email.split("\\.")[0];
                DataRecord new_record = new DataRecord(location,5,amenity,"",start_time,end_time,price,5, email);
                mDatabase.child("Users").child(email).child("Records").child("Sharing").push().setValue(new_record);
                mDatabase.child("Available_Lot").push().setValue(new_record);
                mDatabase.child("Users").child(email).child("Records").child("Share_History").push().setValue(new_record);

                Toast.makeText(AddSpot.this, "Add Spot Successfully.",
                        Toast.LENGTH_SHORT).show();
//                final List<DataRecord> currentAvailableLot = new ArrayList<DataRecord>();
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("Available_Lot");
//                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // This method is called once with the initial value and again
//                        // whenever data at this location is updated.
//                        Log.e("firebase","1");
//
//                        Log.e("firebase","3");
//                        Iterable<DataSnapshot> availableLotSnapshots = dataSnapshot.getChildren();
//                        Log.e("firebase","4");
//                        for (DataSnapshot availableLotSnapshot : availableLotSnapshots) {
//                            Log.e("firebase","5");
//                            DataRecord c = availableLotSnapshot.getValue(DataRecord.class);
//                            currentAvailableLot.add(c);
//                        }
//                        Toast.makeText(AddSpot.this, "Data loaded",
//                                Toast.LENGTH_SHORT).show();
//
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // ...
//                    }
//                });


                finish();




            default:
                break;
        }
    }
}

