package com.rdulinjr.medialibrary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "test1";
    private ListView libraryListView;
    private LibraryDBHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDB = new LibraryDBHandler(this);
        ArrayList library = myDB.getAllMovies();
        ArrayAdapter libraryAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, library);
        libraryListView = (ListView) findViewById(R.id.librarylistview);
        libraryListView.setAdapter(libraryAdapter);
        libraryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Not done yet", Toast.LENGTH_LONG).show();
            }
        });

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
    public void addClicked(View view) {
        Intent addIntent = new Intent(this, AddMediaActivity.class);
        startActivity(addIntent);
    }
}
