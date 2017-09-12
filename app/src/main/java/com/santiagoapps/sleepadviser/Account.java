package com.santiagoapps.sleepadviser;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Account extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference database;
    TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        init();
    }

    private void init(){

        txtName = (TextView)findViewById(R.id.txtName);


        firebaseAuth= FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    Toast.makeText(getApplicationContext(),"WELCOME TO THE PROGRAM",Toast.LENGTH_LONG).show();
                    FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
                    txtName.setText("Welcome, " + user.getDisplayName() + "!");

                } else{
                    Toast.makeText(getApplicationContext(),"Logging-out",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Account.this, RegisterActivity.class));
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    public void onClick_btnLogout(View v){
        firebaseAuth.getInstance().signOut();
    }
}
