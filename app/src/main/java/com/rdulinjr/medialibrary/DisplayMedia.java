package com.rdulinjr.medialibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMedia extends AppCompatActivity {

    private final String TAG = "displayactivity";
    private EditText title_editText;
    private EditText year_editText;
    private EditText runtime_editText;
    private EditText genre_editText;
    private EditText actors_editText;
    private Button edit;
    private Button delete;
    private Button ok;
    private String imageUrl = "";
    private LibraryDBHandler myDB;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_media);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title_editText = (EditText) findViewById(R.id.display_title);
        year_editText = (EditText) findViewById(R.id.display_year);
        runtime_editText = (EditText) findViewById(R.id.display_runtime);
        genre_editText = (EditText) findViewById(R.id.display_genre);
        actors_editText = (EditText) findViewById(R.id.display_actors);
        edit = (Button) findViewById(R.id.display_edit_button);
        delete = (Button) findViewById(R.id.display_delete_button);
        myDB = new LibraryDBHandler(this);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");
        Cursor rs = myDB.getData(id);
        rs.moveToFirst();
        title_editText.setText(rs.getString(rs.getColumnIndex(LibraryDBHandler.getMoviesColumnTitle())));

        year_editText.setText(rs.getString(rs.getColumnIndex(LibraryDBHandler.getMoviesColumnYear())));

        runtime_editText.setText(rs.getString(rs.getColumnIndex(LibraryDBHandler.getMoviesColumnRuntime())));

        genre_editText.setText(rs.getString(rs.getColumnIndex(LibraryDBHandler.getMoviesColumnGenre())));

        actors_editText.setText(rs.getString(rs.getColumnIndex(LibraryDBHandler.getMoviesColumnActors())));

        imageUrl = rs.getString(rs.getColumnIndex(LibraryDBHandler.getMoviesColumnPoster()));
        if (!rs.isClosed()) {
            rs.close();
        }

        // have to get image asynchronously
        try {
            new DownloadImageTask((ImageView) findViewById(R.id.display_poster)).execute(imageUrl);
            Log.d(TAG, "Get an Image");
        } catch (Exception ex) {
        }
    }

    public void display_EditClicked(View view) {
        ok = (Button) findViewById(R.id.display_ok_button);
        // dont want the title to be editable because then a search would have to be done for the poster
        year_editText.setFocusableInTouchMode(true);
        runtime_editText.setFocusableInTouchMode(true);
        genre_editText.setFocusableInTouchMode(true);
        actors_editText.setFocusableInTouchMode(true);
        // hide edit and delete buttons show ok button
        edit.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        ok.setVisibility(View.VISIBLE);
    }

    public void display_DeleteClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure");
        builder.setMessage("Are you sure you want to delete this Movie?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int choice) {
                myDB.deleteMovie(id);
                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int choice) {
                // User cancelled the dialog
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void display_OkClicked(View view) {
        if (myDB.updateMovie(id, title_editText.getText().toString(), year_editText.getText().toString(),
                runtime_editText.getText().toString(), genre_editText.getText().toString(),
                actors_editText.getText().toString(), imageUrl)) {
            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
        }
    }
}
