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

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.class_library.DatabaseHelper;


public class DashboardFragment extends Fragment{

    private final static String TAG = "SleepAdviser";

    private DatabaseHelper myDb;
    private Context context;
    private View rootView;
    private Button btnView, btnViewUser;
    private Button btnDelete;
    private EditText etUserId;

    private TextView tvWelcome, tvRecords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        tvWelcome = (TextView)rootView.findViewById(R.id.lblWelcome);
        tvRecords = (TextView)rootView.findViewById(R.id.txtRecords);
        etUserId = (EditText)rootView.findViewById(R.id.txtUserId);

        context = getActivity();
        myDb = new DatabaseHelper(context);

        tvRecords.setText(myDb.getUserCount() + " records found!");

        btnView = (Button)rootView.findViewById(R.id.btnView);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor res = myDb.getAllData();
                if(res.getCount() == 0){
                    Log.d(TAG,"Database is empty!");
                    showMessage("Error", "No entry!");
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
