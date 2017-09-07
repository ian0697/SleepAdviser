package com.santiagoapps.sleepadviser;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginSleepAdviser extends AppCompatActivity {

    private Button btnEnter;
    private EditText txtEmail;
    private EditText txtPassword;
    private TextView txtView_Signin;
    private ProgressDialog progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sleep_adviser);
        init();
    }

    public void init(){
        firebaseAuth = FirebaseAuth.getInstance();
        btnEnter = (Button)findViewById(R.id.btnEnter);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtView_Signin = (TextView) findViewById(R.id.txtSignin);

    }

    private void registerUser(){
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email address",Toast.LENGTH_LONG).show();
            return;

        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password address",Toast.LENGTH_LONG).show();
            return;
        }


        progressBar.setMessage("Registering user...");
        progressBar.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginSleepAdviser.this,"ACCOUNT REGISTERED!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginSleepAdviser.this,"Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void openAnotherActivity(){

    }


    public void onClick_signin(View v){
        registerUser();
    }

    public void onClick_btnEnter(View v){
        openAnotherActivity();
    }


}
