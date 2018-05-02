package com.example.kejin.iot_demo;

/**
 * Created by kejin on 25/02/2018.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;


public class LoginActivity extends Activity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("aa","in login");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button loginbtn = (Button)findViewById(R.id.signinBtn);
        Button registerbtn = (Button)findViewById(R.id.registerBtn);
        final EditText emailAccount = (EditText)findViewById(R.id.accountEt);
        final EditText passwordAccount = (EditText)findViewById(R.id.pwdEt);
        mAuth = FirebaseAuth.getInstance();
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailAccount.getText().toString();
                String password = passwordAccount.getText().toString();
                if(email==null||email.length()==0||password==null||password.length()==0) {
                    Toast.makeText(LoginActivity.this, "Authentication failed. length can't be zero",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void updateUI(FirebaseUser user) {
        if(user ==null)
            Toast.makeText(LoginActivity.this, "Authentication failed. length can't be zero",
                    Toast.LENGTH_SHORT).show();
        else{
            Intent intent= new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }





}