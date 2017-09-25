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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference tbl_user;
    TextView txtName;
    User _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        init();
    }

    private void init(){

        txtName = (TextView)findViewById(R.id.txtName);

        firebaseAuth= FirebaseAuth.getInstance();
        user =  FirebaseAuth.getInstance().getCurrentUser();

        tbl_user = FirebaseDatabase.getInstance().getReference("User");

        tbl_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 if(dataSnapshot.child(user.getUid()).exists()){
                     _user = dataSnapshot.child(user.getUid()).getValue(User.class);
                     txtName.setText(String.format("Welcome %s" , _user.getName()));
                 }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        txtName.setText(String.format("Welcome %s" , _user.getName()));


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    //txtName.setText(String.format("Welcome %s" , _user.getName()));
                } else{
                    Toast.makeText(getApplicationContext(),"Logging-out",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Account.this, LoginActivity.class));
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
