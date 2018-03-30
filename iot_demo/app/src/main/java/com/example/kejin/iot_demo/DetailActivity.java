package com.example.kejin.iot_demo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

/**
 * Created by kejin on 27/03/2018.
 */

public class DetailActivity  extends AppCompatActivity implements OnClickListener{
    private Button start_time_button;
    private Button end_time_button;
    private LayoutInflater mInflater;
    private View scroll_detail;
    private Button book_btn_detail;
    private Toolbar toolbar_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
  //      requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);
        mInflater = LayoutInflater.from(this);
        start_time_button = (Button) this.findViewById(R.id.start_date_btn_detail);
        end_time_button = (Button) this.findViewById(R.id.end_date_btn_detail);
        book_btn_detail = (Button) this.findViewById(R.id.book_btn_detail);
        toolbar_detail = (Toolbar) this.findViewById(R.id.toolbar_detail1);

        start_time_button.setOnClickListener(this);
        end_time_button.setOnClickListener(this);
        book_btn_detail.setOnClickListener(this);

        setSupportActionBar(toolbar_detail);
        toolbar_detail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.start_date_btn_detail://开始时间
                Log.e("aa", "in start butn");

                DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(DetailActivity.this, new DateChooseWheelViewDialog.DateChooseInterface() {
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        start_time_button.setText(time);
                    }
                });
                startDateChooseDialog.setDateDialogTitle("Start time");
                startDateChooseDialog.showDateChooseDialog();
                break;
            case R.id.end_date_btn_detail://结束时间
                Log.e("aa", "in end butn");
                DateChooseWheelViewDialog endDateChooseDialog = new DateChooseWheelViewDialog(DetailActivity.this,
                        new DateChooseWheelViewDialog.DateChooseInterface() {
                            @Override
                            public void getDateTime(String time, boolean longTimeChecked) {
                                end_time_button.setText(time);
                            }
                        });
                //endDateChooseDialog.setTimePickerGone(true);
                endDateChooseDialog.setDateDialogTitle("End time");
                endDateChooseDialog.showDateChooseDialog();
                break;

            case R.id.book_btn_detail:
                // This is used for booking confirmation
                startActivity(new Intent(this, CheckoutActivity.class));
                break;

            default:
                break;
        }

    }
}
