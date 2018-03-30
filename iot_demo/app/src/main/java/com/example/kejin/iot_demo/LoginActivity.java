package com.example.kejin.iot_demo;

/**
 * Created by kejin on 25/02/2018.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;


public class LoginActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button loginbtn = (Button)findViewById(R.id.signinBtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}