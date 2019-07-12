package com.greensquare.give_a_life.Utility;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.greensquare.give_a_life.Tabs.Donor;
import com.greensquare.give_a_life.Tabs.Requeter;

public class TabAdaptor extends FragmentPagerAdapter {
    public TabAdaptor(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Donor();
            case 1:
                return new Requeter();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Donor";
            case 1:
                return "Requester";
            default:
                return "";
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
