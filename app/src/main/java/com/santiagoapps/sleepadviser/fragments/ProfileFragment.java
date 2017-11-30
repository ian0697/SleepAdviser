package com.santiagoapps.sleepadviser.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.List;

/**
 * Created by Ian on 11/24/2017.
 */

public class ProfileFragment extends Fragment {

    private final static String TAG = "SleepAdviser";

    private DatabaseHelper myDb;
    private Context context;
    private View rootView;

    private DatabaseReference tbl_user;
    private FirebaseUser user;
    private User current_user;

    /* components */
    private TextView tvName, tvUserRecords;
    private LinearLayout llUsers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_dashboard, container, false);
        context = getActivity();

        /* components */
        tvName = rootView.findViewById(R.id.tvUserName);
        tvUserRecords = rootView.findViewById(R.id.tvUserRecords);
        llUsers = rootView.findViewById(R.id.linearUser);
        llUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer buffer = new StringBuffer();
                List<User> allUsers = myDb.getAllUsers();
                for(int x = 0; x< allUsers.size(); x++){
                    buffer.append(allUsers.get(x).toString()+ "\n");
                }

                showMessage("Data", buffer.toString());
                Log.d(TAG, buffer.toString());
            }
        });


        /* database set-up */
        myDb = new DatabaseHelper(context);
        user =  FirebaseAuth.getInstance().getCurrentUser();
        tbl_user = FirebaseDatabase.getInstance().getReference("Users");
        tbl_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getUid()).exists()) {
                    current_user = dataSnapshot.child(user.getUid()).getValue(User.class);
                    try {
                        tvName.setText(current_user.getName());
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
        tvUserRecords.setText(myDb.getUserCount()+"");


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
