    package com.santiagoapps.sleepadviser.fragments;

/**
 * Created by Ian on 10/2/2017.
 */


import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.class_library.DatabaseHelper;
import com.santiagoapps.sleepadviser.class_library.User;


public class DashboardFragment extends Fragment{

    private final static String TAG = "SleepAdviser";

    private DatabaseHelper myDb;
    private Context context;
    private View rootView;
    private Button btnView, btnViewUser;
    private Button btnDelete;
    private EditText etUserId;

    private DatabaseReference tbl_user;
    private FirebaseUser user;
    private User current_user;

    private TextView tvWelcome, tvRecords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        context = getActivity();

        //components
        tvWelcome = rootView.findViewById(R.id.lblWelcome);
        tvRecords = rootView.findViewById(R.id.txtRecords);
        etUserId = rootView.findViewById(R.id.txtUserId);

        //Database
        myDb = new DatabaseHelper(context);
        user =  FirebaseAuth.getInstance().getCurrentUser();
        tbl_user = FirebaseDatabase.getInstance().getReference("Users");
        tbl_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getUid()).exists()) {
                    current_user = dataSnapshot.child(user.getUid()).getValue(User.class);
                    try {
                        tvWelcome.setText(String.format("Welcome %s", current_user.getName()));
                    }
                    catch (Exception e){
                        Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tvRecords.setText(myDb.getUserCount() + " records found!");

        btnView = (Button)rootView.findViewById(R.id.btnView);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor res = myDb.getAllData();
                if(res.getCount() == 0){
                    Log.d(TAG,"Database is empty!");
                    showMessage("Error", "No entry!");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Email: " + res.getString(2) + "\n");
                    buffer.append("Password: " + res.getString(3) + "\n");
                    buffer.append("Date Created: " + res.getString(4) + "\n\n");
                }

                showMessage("Data", buffer.toString());
                Log.d(TAG, buffer.toString());
            }
        });

        btnViewUser = (Button)rootView.findViewById(R.id.btnViewUser);
        btnViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long userid = Long.parseLong(etUserId.getText().toString());
                Log.d(TAG,myDb.getUser(userid).toString());
                showMessage("USER TYPED" , "Name: " + myDb.getUser(userid).getName());

            }
        });

        btnDelete = (Button) rootView.findViewById(R.id.btnDeleteAll);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: DELETE DATABASE RECORDS
                myDb.resetUserTable();
                tvRecords.setText(myDb.getUserCount() + " records found!");
                showMessage("Record Deletion" , "ALL RECORDS DELETED");

            }
        });



        return rootView;
    }


    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
