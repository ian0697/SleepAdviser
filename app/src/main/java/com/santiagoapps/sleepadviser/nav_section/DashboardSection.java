package com.santiagoapps.sleepadviser.nav_section;

import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.fragments.*;


public class DashboardSection extends Fragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private View view;
    private Boolean exit = false;
    TabLayout tabLayout;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sample, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));

        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        setUpTabIcons();

        return view;
    }

    private void setUpTabIcons(){
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(mViewPager);
                tabLayout.getTabAt(0).setIcon(R.drawable.logo_home);
                tabLayout.getTabAt(1).setIcon(R.drawable.logo_information);
                tabLayout.getTabAt(2).setIcon(R.drawable.logo_videos);

//                int tabIconColor = ContextCompat.getColor(getActivity(), R.color.app_color);
//                tabLayout.getTabAt(0).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                tabLayout.getTabAt(1).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                tabLayout.getTabAt(2).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                tabLayout.getTabAt(3).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

            }
        });
    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    ProfileFragment page1 = new ProfileFragment();
                    return page1;
                case 2:
                    MusicSection page4 = new MusicSection();
                    return page4;
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
