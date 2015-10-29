package com.rdulinjr.medialibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Rj on 10/28/2015.
 */
public class LibraryDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mediaLibrary.db";
    private static final String TABLE_MOVIES = "movies";
    // NOT USED YET    private static final String TABLE_TV = "tv";
    private static final String MOVIES_COLUMN_ID = "_id";
    private static final String MOVIES_COLUMN_TITLE = "Title";
    private static final String MOVIES_COLUMN_YEAR = "year";
    private static final String MOVIES_COLUMN_RUNTIME = "runtime";
    private static final String MOVIES_COLUMN_GENRE = "genre";
    private static final String MOVIES_COLUMN_ACTORS = "actors";
    private static final String MOVIES_COLUMN_POSTER = "poster";

    LibraryDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_MOVIES + " (" + MOVIES_COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + MOVIES_COLUMN_TITLE +
                " TEXT, " + MOVIES_COLUMN_YEAR + " TEXT, " + MOVIES_COLUMN_RUNTIME +
                " TEXT, " + MOVIES_COLUMN_GENRE + " TEXT, " + MOVIES_COLUMN_ACTORS +
                " TEXT, " + MOVIES_COLUMN_POSTER + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_MOVIES;
        db.execSQL(query);
    }

    public boolean insertMovie(String title, String year, String runtime, String genre, String actors, String poster) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIES_COLUMN_TITLE, title);
        contentValues.put(MOVIES_COLUMN_YEAR, year);
        contentValues.put(MOVIES_COLUMN_RUNTIME, runtime);
        contentValues.put(MOVIES_COLUMN_GENRE, genre);
        contentValues.put(MOVIES_COLUMN_ACTORS, actors);
        contentValues.put(MOVIES_COLUMN_POSTER, poster);
        db.insert(TABLE_MOVIES, null, contentValues);
        return true;
    }

    public ArrayList<String> getAllMovies() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_MOVIES, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(MOVIES_COLUMN_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }
}
