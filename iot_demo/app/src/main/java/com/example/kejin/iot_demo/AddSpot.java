package com.example.kejin.iot_demo;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;


/**
 * Created by kejin on 28/03/2018.
 */

public class AddSpot extends AppCompatActivity implements ViewStub.OnClickListener {
    private Button start_btn;
    private Button end_btn;
    private Button amenity_btn;
    private Button comfirm_btn;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_spont);
        start_btn = (Button)findViewById(R.id.addSpot_start_date_btn);
        end_btn = (Button) findViewById(R.id.addSpot_end_date_btn);
        amenity_btn =(Button)findViewById(R.id.addSpot_amenity_btn);
        comfirm_btn =(Button)findViewById(R.id.btn_add_spot);
        toolbar =(Toolbar)findViewById(R.id.toolbar_add_spot);


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
                    public void getAmenity(String amenity) {
                        amenity_btn.setText(amenity);
                    }
                });
                amentityChooseDialog.showAmenityChooseDialog();
                break;

            case R.id.btn_add_spot:
                finish();

            default:
                break;
        }
    }
}
