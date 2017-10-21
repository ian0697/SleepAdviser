package com.santiagoapps.sleepadviser;

import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiagoapps.sleepadviser.fragments.*;


public class StatisticFragment extends Fragment {

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
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(mViewPager);
            }
        });

        return view;
    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        final String title[]={"Daily","Weekly","Monthly"};

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Page1Fragment page1 = new Page1Fragment();
            switch (position){
                case 0:
                    return page1;
                case 1:
                    return page1;
                case 2:
                    return page1;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}
