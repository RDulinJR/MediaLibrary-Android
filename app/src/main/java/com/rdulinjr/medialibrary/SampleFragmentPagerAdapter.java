package com.rdulinjr.medialibrary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Rj on 11/15/2015.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private int page_count;
    private String tabTitles[] = new String[]{"Tab1", "Tab2", "Tab3"};
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return DisplayCollection.newInstance(position + 1);
            }
            default: {
                return PageFragment.newInstance(position + 1);
            }
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
