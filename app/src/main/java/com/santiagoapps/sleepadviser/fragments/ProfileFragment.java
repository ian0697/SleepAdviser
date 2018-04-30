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
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santiagoapps.sleepadviser.activities.RegisterActivity;
import com.santiagoapps.sleepadviser.activities.SleepingActivity;
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
    private Button btnSync;
    private LinearLayout llUsers;
    private FloatingActionButton fab;

    private SharedPreferences ref;
    private static final int SELECT_PICTURE = 100;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getActivity();

        myDialog = new Dialog(getContext());
        ref = context.getApplicationContext().getSharedPreferences("Dormie", Context.MODE_PRIVATE);

        initDatabase();
        setTextView();
        setSleepReminder(DateHelper.stringTimeToCalendar(ref.getString("sleep",null)));

        btnSync = rootView.findViewById(R.id.btnSync);
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterDialog();
            }
        });

        fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SleepingActivity.class));
            }
        });

        TextView txtChange = rootView.findViewById(R.id.txtChange);
        txtChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
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

    private void setSleepReminder(Calendar cal){
        AlarmManager manager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = cal;

        if(ref.contains("sleep")){
            String strTime = ref.getString("sleep",null);
            Log.d(TAG, DateHelper.dateToStandardString(calendar));
        }

        Intent alarmIntent = new Intent(context, AlarmNotificationReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

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
                SessionRepo s = new SessionRepo();
                s.resetSessionTable();
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

    /** dialog for registering user */
    public void showRegisterDialog(){
        TextView txtClose;
        myDialog.setContentView(R.layout.dialog_prompt);

        Button btnRegister = myDialog.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences ref = context.getApplicationContext().getSharedPreferences("Dormie", Context.MODE_PRIVATE);;
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                intent.putExtra("SESSION_SLEEP_GOAL", ref.getString("sleep", ""));
                intent.putExtra("SESSION_AGE", ref.getInt("age", 0));
                intent.putExtra("SESSION_GENDER", ref.getString("gender", ""));
                intent.putExtra("SESSION_NAME",ref.getString("name", ""));
                intent.putExtra("SESSION_OCCUPATION", ref.getString("occupation", ""));
                startActivity(intent);
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

    /** dialog for edit sleeping goal */
    public void showEditDialog(){
        TextView txtClose;
        myDialog.setContentView(R.layout.dialog_edit_goal);


        Button btnEnter = myDialog.findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePicker timePicker = myDialog.findViewById(R.id.timePicker);
                int hr = timePicker.getHour();
                int min = timePicker.getMinute();

                SharedPreferences.Editor editor = ref.edit();
                editor.putString("sleep", DateHelper.getTimeFormat(hr,min));
                editor.apply();
                myDialog.dismiss();
                setTextView();
                setSleepReminder(DateHelper.stringTimeToCalendar(ref.getString("sleep",null)));

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

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void networkAvailable() {
        Toast.makeText(context.getApplicationContext(), "INTERNET IS AVAILABLE BOOYAH!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Internet is available - ProfileFragment");
    }

    @Override
    public void networkUnavailable() {

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
}
