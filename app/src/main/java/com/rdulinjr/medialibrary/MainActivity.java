package com.rdulinjr.medialibrary;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "test1";
    private SampleFragmentPagerAdapter mAdapter;
    private ViewPager mPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup viewpager and fragment adapter create fragments
        mPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(DisplayCollection.newInstance(1), "Collection");
        mAdapter.addFragment(PageFragment.newInstance(2), "Page 2");
        mAdapter.addFragment(PageFragment.newInstance(3), "Page 3");

        mPager.setAdapter(mAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        if (id == R.id.action_about) {
            // explicit intent
            Intent intent = new Intent(this, AboutActivity.class);
            // sending extra data
            intent.putExtra("Message", "Sent Message");
            startActivity(intent);
            return true;
        }
        if (id == R.id.implicit_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String stringTOShare = "implicit share";
            sharingIntent.putExtra(Intent.EXTRA_TEXT, stringTOShare);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
