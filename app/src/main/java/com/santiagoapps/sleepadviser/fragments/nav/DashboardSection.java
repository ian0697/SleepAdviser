package com.santiagoapps.sleepadviser.fragments.nav;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.santiagoapps.sleepadviser.activities.MusicActivity;
import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.fragments.SleepHistoryFragment;
import com.santiagoapps.sleepadviser.fragments.*;


public class DashboardSection extends Fragment {

    private ViewPager mViewPager;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sample, container, false);

        toolbar = container.findViewById(R.id.toolbar);

        mViewPager = view.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));

        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_profile:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.action_sessions:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.action_settings:
                        mViewPager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }

                return false;
            }
        });


        return view;
    }

//    private void setUpTabIcons(){
//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                tabLayout.setupWithViewPager(mViewPager);
//                tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
//                tabLayout.getTabAt(1).setIcon(R.drawable.ic_info);
//                tabLayout.getTabAt(2).setIcon(R.drawable.ic_video);
//
//            }
//        });
//    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return new ProfileFragment();
                case 1:
                    return new SleepHistoryFragment();
                case 2:
                    return new MusicActivity();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }
}
