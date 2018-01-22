package com.santiagoapps.sleepadviser.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.santiagoapps.sleepadviser.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText txtEmail;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){

        //Init Components
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        //Firebase Initialization
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() != null) {
                    startActivity(new Intent(LoginActivity.this, NavigationMain.class));
                } else {

                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if( mAuthListener != null ){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void sign_in(){
        //user inputs
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        //ProgressDialog
        final ProgressDialog mProgress = new ProgressDialog(this);
        mProgress.setMessage("Logging in...");
        mProgress.show();

        //Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgress.dismiss();
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(getApplicationContext(), "Log-in failed!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void onClick_register(View v){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void onClick_skip(View v){
        startActivity(new Intent(this, NavigationMain.class));
    }

    public void onClick_signin(View v){
        sign_in();
    }

}
