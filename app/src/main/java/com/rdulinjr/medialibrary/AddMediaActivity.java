package com.rdulinjr.medialibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class AddMediaActivity extends AppCompatActivity {

    private final String TAG = "addactivity";
    private EditText title_editText;
    private EditText year_editText;
    private EditText runtime_editText;
    private EditText genre_editText;
    private EditText actors_editText;
    private String imageUrl = "";
    private Button next;
    private Button prev;
    private ImageView poster;
    private LibraryDBHandler myDB;
    private int position;
    private JSONArray jsonarray;
    private JSONObject jsonobject;

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
        next = (Button) findViewById(R.id.next_button);
        prev = (Button) findViewById(R.id.previous_button);
        poster = (ImageView) findViewById(R.id.poster);
    }

    public void add_SearchClicked(View view) {
        // make sure previous button not shown there is a check latter for next button
        prev.setVisibility(View.GONE);
        position = 0;
        // TMDB api key 5b8e71fb65209d1a8531fdcd216c8cb8
        // get user input title and change spaces to +
        String mediaTitle = title_editText.getText().toString().replaceAll(" ", "%20");
        String searchUrl = "https://api.themoviedb.org/3/search/movie?api_key=5b8e71fb65209d1a8531fdcd216c8cb8&query=" + mediaTitle;
        try {
            // use asynchronous task to get json from OMDB
            String json = new DownloadMediaInfoTask().execute(searchUrl).get();
            // now parse the json
            jsonobject = new JSONObject(json);
            jsonarray = jsonobject.getJSONArray("results");
            // check if the next button should be shown
            if (jsonarray.length() > 1) {
                next.setVisibility(View.VISIBLE);
            } else {
                next.setVisibility(View.GONE);
            }
            showResults();
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
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

    public void add_NextClicked(View view) {
        position++;
        if (position == (jsonarray.length() - 1)) {
            next.setVisibility(View.GONE);
        }
        prev.setVisibility(View.VISIBLE);
        showResults();
    }

    public void add_PrevClicked(View view) {
        position--;
        if (position == 0) {
            prev.setVisibility(View.GONE);
        }
        next.setVisibility(View.VISIBLE);
        showResults();
    }

    public void showResults() {
        try {
            // get movie id
            jsonobject = jsonarray.getJSONObject(position);
            String id = jsonobject.getString("id");
            // get more info on the movie
            String movieUrl = "https://api.themoviedb.org/3/movie/" + id + "?api_key=5b8e71fb65209d1a8531fdcd216c8cb8";
            String json = new DownloadMediaInfoTask().execute(movieUrl).get();
            jsonobject = new JSONObject(json);
            // put the info into the right fields
            title_editText.setText(jsonobject.getString("title"));
            String rlYear = jsonobject.getString("release_date");
            int end = rlYear.indexOf("-");
            year_editText.setText(rlYear.substring(0, end));
            runtime_editText.setText(jsonobject.getString("runtime") + " min");
            imageUrl = jsonobject.getString("poster_path");
            // gettting genres is a little more work
            String genres = "";
            JSONArray genrearray = jsonobject.getJSONArray("genres");
            int i = 0;
            jsonobject = genrearray.getJSONObject(i);
            genres += jsonobject.getString("name");
            if (genrearray.length() > 1) {
                for (i = 1; i < genrearray.length(); i++) {
                    jsonobject = genrearray.getJSONObject(i);
                    genres += ", " + jsonobject.getString("name");
                }
            }
            genre_editText.setText(genres);
            // getting the actors is even harder
            String actors = "";
            movieUrl = "https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=5b8e71fb65209d1a8531fdcd216c8cb8";
            json = new DownloadMediaInfoTask().execute(movieUrl).get();
            jsonobject = new JSONObject(json);
            JSONArray actorsarray = jsonobject.getJSONArray("cast");
            i = 0;
            jsonobject = actorsarray.getJSONObject(i);
            actors += jsonobject.getString("name");
            if (actorsarray.length() > 1) {
                // only show 10 actors if there are more than 10
                for (i = 1; ((i < actorsarray.length()) && (i < 10)); i++) {
                    jsonobject = actorsarray.getJSONObject(i);
                    actors += ", " + jsonobject.getString("name");
                }
            }
            actors_editText.setText(actors);

            if (imageUrl != null) {
                // have to get image asynchronously
                new DownloadImageTask(poster).execute(imageUrl);
                Log.d(TAG, "Get an Image");
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }
}
