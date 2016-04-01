package com.rdulinjr.medialibrary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rj on 11/15/2015.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private int page_count;
    private List<Fragment> fragments;
    private List<String> fragmentTitles;
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragmentTitles = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    // adds a fragmet to the list
    public void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        fragmentTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return fragmentTitles.get(position);
    }
}
