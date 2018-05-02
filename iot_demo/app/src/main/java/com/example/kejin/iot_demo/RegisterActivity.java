package com.example.kejin.iot_demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import static android.content.ContentValues.TAG;

/**
 * Created by kejin on 03/04/2018.
 */

public class RegisterActivity extends Activity{
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        final EditText emailText = (EditText) findViewById(R.id.email_register);
        final EditText passworkText = (EditText) findViewById(R.id.password_register);
        Button confirm_button = (Button) findViewById(R.id.confirm_register);
        TextView term_of_service = (TextView)findViewById(R.id.term_of_service);
        TextView privacy_policy  = (TextView)findViewById(R.id.privacy_policy);
        final CheckBox checkbox = (CheckBox)findViewById(R.id.checkbox1);

        mAuth = FirebaseAuth.getInstance();
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = (String) emailText.getText().toString();
                String password = (String) passworkText.getText().toString();
                if(email==null||email.length()==0||password==null||password.length()==0) {
                    Toast.makeText(RegisterActivity.this, "Registration failed. length can't be zero",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                    startActivity(intent);


                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                }
                                else if(checkbox.isChecked() == false) {
                                    Toast.makeText(RegisterActivity.this, "You mush comfirm the policy!",
                                            Toast.LENGTH_SHORT).show();

                                } else{
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Registration failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });



            }
        });

        term_of_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNormalDialog1();
            }
        });

        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNormalDialog2();

            }
        });



    }
    private void updateUI(FirebaseUser user) {
        if(user ==null) {
            Toast.makeText(RegisterActivity.this, "Registration failed. length can't be zero",
                    Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    private void showNormalDialog1(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(RegisterActivity.this);

        normalDialog.setTitle("Terms of Use");
        normalDialog.setMessage("By confirming this policy, you agree not to use this app when you drive faster than 30 MPH.");
        normalDialog.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        // 显示
        normalDialog.show();

    }

    private void showNormalDialog2() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(RegisterActivity.this);

        normalDialog.setTitle("Privacy Policy");
        normalDialog.setMessage("By confirming this policy, you agree us to keep necessary privacy information.");
        normalDialog.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        // 显示
        normalDialog.show();
    }



}
