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
import android.view.Menu;
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
import com.santiagoapps.sleepadviser.data.repo.SessionRepo;
import com.santiagoapps.sleepadviser.data.repo.UserRepo;
import com.santiagoapps.sleepadviser.helpers.DBHelper;
import com.santiagoapps.sleepadviser.data.model.User;
import com.santiagoapps.sleepadviser.fragments.ProfileFragment;
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

    private static final String TAG = "SleepAdviser";

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

    // for
    Intent sleepIntent;

    // sharedPreferences data
    private String gender, occupation, name, sleep, msg;
    private int age;
    private SharedPreferences.Editor editor;
    private SharedPreferences ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        //header_layout
        View headerView = navigationView.getHeaderView(0);
        tvUser = headerView.findViewById(R.id.nav_name);
        tvEmail = headerView.findViewById(R.id.nav_email);

        ref = getApplicationContext().getSharedPreferences("Dormie", Context.MODE_PRIVATE);
        editor = ref.edit();

        //initialization
        setNavigation();
        setFragment(new ProfileFragment());
        initDatabase();

        //Intent values
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            Bundle bd = intent.getExtras();
            gender = (String) bd.get("SESSION_GENDER");
            name = (String) bd.get("SESSION_NAME");
            occupation = (String) bd.get("SESSION_OCCUPATION");
            age = (int) bd.get("SESSION_AGE");
            sleep = (String) bd.get("SESSION_SLEEP_GOAL");

            editor.putString("name", name);
            editor.putString("gender", gender);
            editor.putString("occupation", occupation);
            editor.putInt("age", age);
            editor.putString("sleep", sleep);
            editor.apply();

        }

        user =  FirebaseAuth.getInstance().getCurrentUser();

        if( user == null ){
            tvUser.setText(ref.getString("name",null));
        }

        if(ref.contains("email")){
            tvEmail.setText(ref.getString("email",null));
        }

        // init receiver for network connection
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            return true;
        }
        if (id == R.id.log_out){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);
    }

    /** database set-up */
    public void initDatabase(){
        DBHelper myDb = new DBHelper(this);
        user =  FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            editor.putString("email", user.getEmail());
            editor.apply();

            tvEmail.setText(ref.getString("email",null));
            tvUser.setText(ref.getString("name",null));
        }


        DatabaseReference tbl_user = FirebaseDatabase.getInstance().getReference("users");
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

    /** handles navigation fragments and activities */
    public void setNavigation(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch(id) {
                    case R.id.nav_dashboard:
                        setFragment(new ProfileFragment());
                        toolbar.setTitle("Profile");
                        break;

                    case R.id.nav_statistic:
//                        setFragment(new StatisticSection());
//                        toolbar.setTitle("Statistics");

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
                        startActivity(new Intent(NavigationActivity.this , MotionSensorActivity.class));
                        break;

                    case R.id.nav_faq:
                        startActivity(new Intent(NavigationActivity.this , FaqActivity.class));
                        break;

                    case R.id.nav_log_out:
                        finish();

                        //reset account
                        UserRepo userRepo = new UserRepo();
                        userRepo.resetUserTable();
                        SessionRepo sessionRepo = new SessionRepo();
                        sessionRepo.resetSessionTable();

                        //firebase sign-out
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(NavigationActivity.this, LoginActivity.class));

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

    /** function when internet is turned on */
    @Override
    public void networkAvailable() {
        Log.d(TAG, "Internet is available - NavigationActivity");
    }

    /** function when internet is unavailable or turned off */
    @Override
    public void networkUnavailable() {
    }
}
