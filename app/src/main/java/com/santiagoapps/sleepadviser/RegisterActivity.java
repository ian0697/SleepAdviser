package com.santiagoapps.sleepadviser;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button btnEnter;
    private EditText txtName;
    private EditText txtEmail;
    private EditText txtPassword,txtPassword2;
    private TextView txtView_Signin;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init(){
        btnEnter = (Button)findViewById(R.id.btnEnter);

        txtName = (EditText)findViewById(R.id.txtName);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtPassword2 = (EditText)findViewById(R.id.txtPassword2);
        txtView_Signin = (TextView) findViewById(R.id.txtSignin);


        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(RegisterActivity.this, Account.class));
                }
            }
        };

    }

    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void registerUser(){
        final String name = txtName.getText().toString().trim();
        final String email = txtEmail.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();
        final String password2 = txtPassword2.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email address",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password2)){
            Toast.makeText(this,"Please enter confirm password",Toast.LENGTH_LONG).show();
            return;
        }

        if(!password.equalsIgnoreCase(password2)){
            Toast.makeText(this,"Passwords do not match!",Toast.LENGTH_LONG).show();
            return;
        }



        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserInformation user = new UserInformation(name, email, password);
                            saveToDatabase(name,email,password);
                            Toast.makeText(RegisterActivity.this,"ACCOUNT REGISTERED!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this,"Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void saveToDatabase(String name, String email, String password){
        UserInformation userInformation = new UserInformation(name,email,password);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        user.updateProfile(new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(Uri.parse("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQnB5nwQQfxJigSlgJdppnCZ9CEVxjgi78_jhOMPg5Z1PRJgwxUwg"))
                .build()
        );

        database.child(user.getUid()).setValue(userInformation);


    }

    public void onClick_signin(View v){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void onClick_btnEnter(View v){
        registerUser();
    }


}
