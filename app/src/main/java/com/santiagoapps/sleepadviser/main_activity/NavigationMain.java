package com.santiagoapps.sleepadviser.main_activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.santiagoapps.sleepadviser.CreateRecord;
import com.santiagoapps.sleepadviser.class_library.DatabaseHelper;
import com.santiagoapps.sleepadviser.class_library.User;
import com.santiagoapps.sleepadviser.class_library.dbHelper;
import com.santiagoapps.sleepadviser.nav_section.DashboardSection;
import com.santiagoapps.sleepadviser.nav_section.MusicSection;
import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.nav_section.StatisticSection;

public class NavigationMain extends AppCompatActivity {


    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View headerView;

    private TextView tvUser;
    private TextView tvEmail;

    private DatabaseHelper myDb;
    private DatabaseReference tbl_user;
    private FirebaseUser user;
    private User current_user;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main);

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);

        //header_layout
        headerView = navigationView.getHeaderView(0);
        tvUser = (TextView)headerView.findViewById(R.id.nav_name);
        tvEmail = (TextView)headerView.findViewById(R.id.nav_email);

        //initialization
        setNavigation();
        setFragment(new DashboardSection());
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavigationMain.this, SleepingActivity.class));
            }
        });



        initDatabase();


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
            Intent i = new Intent(this,SettingsActivity.class);
            startActivity(i);

            return true;
        }
        if (id == R.id.log_out){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initDatabase(){
        /* database set-up */
        myDb = new DatabaseHelper(this);
        user =  FirebaseAuth.getInstance().getCurrentUser();
        tbl_user = FirebaseDatabase.getInstance().getReference("Users");
        tbl_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getUid()).exists()) {
                    current_user = dataSnapshot.child(user.getUid()).getValue(User.class);
                    try {
                        tvUser.setText(current_user.getName());
                        tvEmail.setText(current_user.getEmail());
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

    public void setNavigation(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch(id) {
                    case R.id.first_nav_item:
                        setFragment(new DashboardSection());
                        toolbar.setTitle("Dashboard");
                        break;

                    case R.id.second_nav_item:
                        setFragment(new StatisticSection());
                        toolbar.setTitle("Statistics");
                        break;

                    case R.id.third_nav_item:
                        setFragment(new MusicSection());
                        toolbar.setTitle("Sleep Aids");
                        break;

                    case R.id.nav_log_out:
                        finish();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(NavigationMain.this, LoginActivity.class));
                        break;

                    case R.id.nav_item_create:
                        startActivity(new Intent(NavigationMain.this , CreateRecord.class));
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
}
