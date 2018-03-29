package com.example.kejin.iot_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.kejin.iot_demo.data_class.DataRecord;
import com.example.kejin.iot_demo.widget.adapters.RecycleviewRecordAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kejin on 28/03/2018.
 */

public class RentingActivity extends AppCompatActivity {
    private Toolbar toolbar_renting;
    private RecyclerView recyclerView_renting;
    private List<DataRecord> mList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //      requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
        super.onCreate(savedInstanceState);
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
        for (int i = 0; i < 10; i++) {
            DataRecord item = new DataRecord("1739 Sixth Ave"+i, "0.5mi"+i, "Garage-covered"+i, "3h"+i,"Feb 23 10AM"+i,"Feb23 1PM"+i);
            mList.add(item);
        }
        RecycleviewRecordAdapter Myadapter = new RecycleviewRecordAdapter(mList);

        recyclerView_renting.setAdapter(new RecycleviewRecordAdapter(mList));
    }
}
