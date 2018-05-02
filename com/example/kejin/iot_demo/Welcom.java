package com.example.kejin.iot_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by kejin on 25/02/2018.
 */

public class Welcom extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                Log.e("Welcome","Welcome finished");
                startActivity(new Intent(getBaseContext(),LoginActivity.class));
                return false;
            }
        }).sendEmptyMessageDelayed(0,1000);
    }
}
