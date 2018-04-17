package com.santiagoapps.sleepadviser.fragments;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.santiagoapps.sleepadviser.activities.RegisterActivity;
import com.santiagoapps.sleepadviser.receivers.AlarmNotificationReceiver;
import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.data.model.Session;
import com.santiagoapps.sleepadviser.data.repo.SessionRepo;
import com.santiagoapps.sleepadviser.data.repo.UserRepo;
import com.santiagoapps.sleepadviser.data.model.User;
import com.santiagoapps.sleepadviser.activities.DormieActivity;
import com.santiagoapps.sleepadviser.helpers.DateHelper;
import com.santiagoapps.sleepadviser.receivers.NetworkStateReceiver;

import java.util.Calendar;
import java.util.List;
import java.util.prefs.Preferences;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ian on 11/24/2017.
 */

public class ProfileFragment extends Fragment implements NetworkStateReceiver.NetworkStateReceiverListener{

    private final static String TAG = "Dormie (" + ProfileFragment.class.getSimpleName() + ") ";


    private SessionRepo sessionRepo;
    private Context context;
    private View rootView;

    private DatabaseReference tbl_user;
    private FirebaseUser user;
    private Dialog myDialog;
    private User current_user;

    /* components */
    private TextView tvName, tvUserRecords, tvSessions, tvSleepGoal;
    private LinearLayout llUsers;
    private FloatingActionButton fab;

    private static final int SELECT_PICTURE = 100;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getActivity();

        myDialog = new Dialog(getContext());

        initDatabase();
        setTextView();
        setSleepReminder();

        fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlarmDialog();
            }
        });

        ImageView profileImage = rootView.findViewById(R.id.ProfileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });

        return rootView;
    }

    private void setSleepReminder(){
        AlarmManager manager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = DateHelper.stringToCalendar("10:00 pm");


        int interval = 10000;
        Intent alarmIntent = new Intent(context, AlarmNotificationReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(context, 0, alarmIntent, 0);


        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        //manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


        DateHelper dh = new DateHelper(calendar);
        Toast.makeText(context, dh.dateToString(), Toast.LENGTH_SHORT).show();

        boolean isWorking = (PendingIntent.getBroadcast(getActivity(), 1001, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null);//just changed the flag
        Log.d(TAG, "alarm is " + (isWorking ? "" : "not") + " working...");



        //manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10000, pendingIntent);
        //manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
    }

    private void initDatabase(){
        /* database set-up */
        sessionRepo = new SessionRepo();
        user =  FirebaseAuth.getInstance().getCurrentUser();
        tbl_user = FirebaseDatabase.getInstance().getReference("users");
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
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    ((ImageView) rootView.findViewById(R.id.ProfileImage)).setImageURI(selectedImageUri);
                }
            }
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void setTextView(){
        UserRepo userRepo = new UserRepo();
        SharedPreferences prefs = context.getSharedPreferences("Dormie", Context.MODE_PRIVATE);

        /* components */
        tvName = rootView.findViewById(R.id.tvUserName);
        tvName.setText(prefs.getString("name",""));
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, DormieActivity.class));
            }
        });


        tvSleepGoal = rootView.findViewById(R.id.txtSleepGoal);
        tvSleepGoal.setText(prefs.getString("sleep", ""));


        tvUserRecords = rootView.findViewById(R.id.tvUserRecords);
        tvUserRecords.setText(userRepo.getUserCount()+"");

        tvSessions = rootView.findViewById(R.id.tvSessions);
        tvSessions.setText(sessionRepo.getSessionCount()+ "");

        llUsers = rootView.findViewById(R.id.linearSessions);
        llUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer buffer = new StringBuffer();
                List<Session> sessions = sessionRepo.getAllSession();
                for(int x = 0; x < sessions.size(); x++){
                    buffer.append(sessions.get(x).toString()+ "\n\n");
                }

                showMessage("Data", buffer.toString());
                Log.d(TAG, buffer.toString());
            }
        });



    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void showAlarmDialog(){


        TextView txtClose;
        myDialog.setContentView(R.layout.dialog_prompt);

        Button btnRegister = myDialog.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RegisterActivity.class));
            }

        });

        Button btnSkip = myDialog.findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        txtClose = myDialog.findViewById(R.id.txtClose);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        myDialog.show();
    }


    /** function when internet is turned on */
    @Override
    public void networkAvailable() {
        Toast.makeText(context, "INTERNET IS AVAILABLE BOOYAH!", Toast.LENGTH_SHORT).show();
    }

    /** function when internet is unavailable or turned off */
    @Override
    public void networkUnavailable() {
//        Toast.makeText(this, "Oops disconnected", Toast.LENGTH_SHORT).show();
    }
}
