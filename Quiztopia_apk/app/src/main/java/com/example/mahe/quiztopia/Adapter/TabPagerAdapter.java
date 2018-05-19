package com.example.mahe.quiztopia.Adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mahe.quiztopia.Explore;
import com.example.mahe.quiztopia.Profile;
import com.example.mahe.quiztopia.Topics;

/**
 * Created by MAHE on 3/1/2018.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    int numtabs;
    public TabPagerAdapter(FragmentManager fm,int n) {
        super(fm);
        this.numtabs = n;
    }

    private String[] tabTitles = new String[]{"Explore", "Topics", "Profile"};

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0: Explore explore= new Explore();
                return explore;
            case 1: Topics topics = new Topics();
                return topics;
            case 2: Profile profile = new Profile();
                return profile;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numtabs;
    }
}
