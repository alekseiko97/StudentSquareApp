package com.fhict.studentsquareapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPageAdapter extends FragmentStatePagerAdapter {

    String[] tabArray = new String[]{"Announcements", "Requests"};

    public TabPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabArray[position];
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new AnnouncementDeskFragment();

            case 1:
                return new RequestDeskFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        return tabArray.length;
    }
}
