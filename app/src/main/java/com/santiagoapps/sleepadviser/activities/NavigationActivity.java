package com.santiagoapps.sleepadviser.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santiagoapps.sleepadviser.activities.profile.ProfilingActivity_name;
import com.santiagoapps.sleepadviser.data.repo.SessionRepo;
import com.santiagoapps.sleepadviser.data.repo.UserRepo;
import com.santiagoapps.sleepadviser.fragments.nav.DashboardSection;
import com.santiagoapps.sleepadviser.fragments.nav.VideoSection;
import com.santiagoapps.sleepadviser.data.model.User;
import com.santiagoapps.sleepadviser.fragments.nav.MusicSection;
import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.receivers.NetworkStateReceiver;

/**
 * NavigationActivity
 * ---------------------
 * This class handles the initialization
 * of Navigation fragments
 *   - Profile
 *   - Music Section
 *   - Motion sensor activity
 *   - Statistics
 *
 */

public class NavigationActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{

    private static final String TAG = "Dormie (" + NavigationActivity.class.getSimpleName() + ") ";

    // components
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TextView tvUser;
    private TextView tvEmail;

    // database
    private FirebaseUser user;
    private User current_user;
    private NetworkStateReceiver networkStateReceiver;


    // sharedPreferences data
    private SharedPreferences.Editor editor;
    private SharedPreferences ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main);

        ref = getApplicationContext().getSharedPreferences("Dormie", Context.MODE_PRIVATE);
        editor = ref.edit();

        //initialization
        setNavigation();
        setNavigationHeader();
        setDatabase();
        setNetworkReceiver();
        firstRunOfApp();
    }

    public void firstRunOfApp(){
        //Intent values
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            Bundle bd = intent.getExtras();
            String gender = (String) bd.get("SESSION_GENDER");
            String name = (String) bd.get("SESSION_NAME");
            String occupation = (String) bd.get("SESSION_OCCUPATION");
            int age = (int) bd.get("SESSION_AGE");
            String sleep = (String) bd.get("SESSION_SLEEP_GOAL");

            editor.putString("name", name);
            editor.putString("gender", gender);
            editor.putString("occupation", occupation);
            editor.putInt("age", age);
            editor.putString("sleep", sleep);
            editor.apply();
        }

    }

    public void setNavigationHeader(){
        // header_layout
        View headerView = navigationView.getHeaderView(0);
        tvUser = headerView.findViewById(R.id.nav_name);
        tvEmail = headerView.findViewById(R.id.nav_email);

        // if user is null
        // set the values of textView
        // with current values of sharedPreference
        user =  FirebaseAuth.getInstance().getCurrentUser();
        if( user == null ){
            tvUser.setText(ref.getString("name",null));
        }
        if(ref.contains("email")){
            tvEmail.setText(ref.getString("email",null));
        }
    }

    public void setDatabase(){
        user =  FirebaseAuth.getInstance().getCurrentUser();

        // if user is not null
        // save FireBase email to sharedPreference
        // & set all textView to values of sharedPreference
        if(user!=null){
            editor.putString("email", user.getEmail());
            editor.apply();

            tvEmail.setText(ref.getString("email",null));
            tvUser.setText(ref.getString("name",null));
        }

        DatabaseReference tbl_user = FirebaseDatabase.getInstance().getReference("users");

        // real-time database changes
        tbl_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getUid()).exists()) {
                    current_user = dataSnapshot.child(user.getUid()).getValue(User.class);

                    try {
                        editor.putString("name", current_user.getName());
                        editor.putString("email", user.getEmail());
                        editor.apply();

                        Log.d(TAG, "Info updated - email: " + user.getEmail());
                        tvUser.setText(ref.getString("name",null));
                        tvEmail.setText(ref.getString("email",null));
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setNetworkReceiver(){
        // init receiver for network connection
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

    }

    public void setNavigation(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setFragment(new DashboardSection());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch(id) {
                    case R.id.nav_dashboard:
                        setFragment(new DashboardSection());
                        toolbar.setTitle("Profile");
                        break;

                    case R.id.nav_statistic:
                        startActivity(new Intent(NavigationActivity.this, StatisticsActivity.class));
                        break;

                    case R.id.nav_sleep_aid:
                        setFragment(new MusicSection());
                        toolbar.setTitle("Sleep Aids");
                        break;

                    case R.id.nav_sleep:
                        startActivity(new Intent(NavigationActivity.this,SleepingActivity.class));
                        break;

                    case R.id.nav_item_create:
                        startActivity(new Intent(NavigationActivity.this , CreateRecord.class));
                        break;

                    case R.id.nav_motion_sensor:
//                        startActivity(new Intent(NavigationActivity.this , SleepVideoActivity.class));

                        setFragment(new VideoSection());
                        toolbar.setTitle("Sleep videos");
                        break;


                    case R.id.nav_faq:
                        startActivity(new Intent(NavigationActivity.this , FaqActivity.class));
                        break;

                    case R.id.nav_log_out:
                        finish();

                        //reset account
                        new UserRepo().resetUserTable();
                        new SessionRepo().resetSessionTable();

                        //firebase sign-out
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(NavigationActivity.this, ProfilingActivity_name.class));

                        break;

                    case R.id.nav_change_acct:
                        finish();
                        new UserRepo().resetUserTable();
                        new SessionRepo().resetSessionTable();
                        startActivity(new Intent(NavigationActivity.this , LoginActivity.class));
                        break;
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }


        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void setFragment(Fragment fragment){
        if(fragment!=null){
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.commit();
        }
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);
    }

    @Override
    public void networkAvailable() {
        Log.d(TAG, "Internet is available - NavigationActivity");
    }

    @Override
    public void networkUnavailable() {
    }
}
