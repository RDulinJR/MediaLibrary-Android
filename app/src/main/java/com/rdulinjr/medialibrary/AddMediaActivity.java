package com.rdulinjr.medialibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class AddMediaActivity extends AppCompatActivity {

    private final String TAG = "test1";
    private EditText title_editText;
    private EditText year_editText;
    private EditText runtime_editText;
    private EditText genre_editText;
    private EditText actors_editText;
    private String imageUrl = "";
    private LibraryDBHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_media);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title_editText = (EditText) findViewById(R.id.title_editText);
        year_editText = (EditText) findViewById(R.id.year_editText);
        runtime_editText = (EditText) findViewById(R.id.runtime_editText);
        genre_editText = (EditText) findViewById(R.id.genre_editText);
        actors_editText = (EditText) findViewById(R.id.actors_editText);
        myDB = new LibraryDBHandler(this);
    }

    public void add_SearchClicked(View view) {

        // get user input title and change spaces to +
        String mediaTitle = title_editText.getText().toString();
        mediaTitle = mediaTitle.replace(' ', '+');
        String mediaUrl = "http://www.omdbapi.com/?t=" + mediaTitle + "&y=&plot=short&r=json";
        try {
            // use asynchronous task to get json from OMDB
            String json = new DownloadMediaInfoTask().execute(mediaUrl).get();
            JSONObject jsonobject = new JSONObject(json);
            // put the info into the right fields
            title_editText.setText(jsonobject.getString("Title"));
            year_editText.setText(jsonobject.getString("Year"));
            runtime_editText.setText(jsonobject.getString("Runtime"));
            genre_editText.setText(jsonobject.getString("Genre"));
            actors_editText.setText(jsonobject.getString("Actors"));
            imageUrl = jsonobject.getString("Poster");
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        // have to get image asynchronously
        try{
            new DownloadImageTask((ImageView) findViewById(R.id.poster)).execute(imageUrl);
            Log.d(TAG, "Get an Image");
        }
        catch(Exception ex)
        {
        }
    }

    public void add_AddClicked(View view) {
        // put info in strings for data checking
        String title = title_editText.getText().toString();
        String year = year_editText.getText().toString();
        String runtime = runtime_editText.getText().toString();
        String genre = genre_editText.getText().toString();
        String actors = actors_editText.getText().toString();
        if (myDB.insertMovie(title, year, runtime, genre, actors, imageUrl)) {
            Toast.makeText(this, title + " Successfully added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, title + " not added", Toast.LENGTH_SHORT).show();
        }
        // go back to main activity
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
