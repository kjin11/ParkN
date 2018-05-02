package com.example.kejin.iot_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kejin.iot_demo.data_class.DataRecord;
import com.example.kejin.iot_demo.widget.adapters.RecycleviewRecordAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kejin on 28/03/2018.
 */

public class LendingActivity extends AppCompatActivity {
    private Toolbar toolbar_lending;
    private RecyclerView recyclerView_lending;
    private Button btn_add_sharing;
    private List<DataRecord> mList = new ArrayList<>();
    private Toolbar toolbar_sharing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //      requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lending);
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
        for (int i = 0; i < 10; i++) {
            DataRecord item = new DataRecord("1739 Sixth Ave"+i, 5, "Garage-covered"+i, "3h"+i,"Feb 23 10AM"+i,"Feb23 1PM"+i,0, 0,"jinke2017@gmail.com");
            mList.add(item);
        }
        RecycleviewRecordAdapter Myadapter = new RecycleviewRecordAdapter(mList);

        recyclerView_lending.setAdapter(new RecycleviewRecordAdapter(mList));
    }
}
