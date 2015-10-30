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
        // create tables
        String query = "CREATE TABLE " + TABLE_MOVIES + " (" + MOVIES_COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + MOVIES_COLUMN_TITLE +
                " TEXT, " + MOVIES_COLUMN_YEAR + " TEXT, " + MOVIES_COLUMN_RUNTIME +
                " TEXT, " + MOVIES_COLUMN_GENRE + " TEXT, " + MOVIES_COLUMN_ACTORS +
                " TEXT, " + MOVIES_COLUMN_POSTER + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop the tables
        String query = "DROP TABLE IF EXISTS " + TABLE_MOVIES;
        db.execSQL(query);
        // create new ones
        onCreate(db);
    }

    public boolean insertMovie(String title, String year, String runtime, String genre, String actors, String poster) {
        // get writable database put info in contentvalues then add to database
        // need error checking
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

    public boolean updateMovie(int id, String title, String year, String runtime, String genre, String actors, String poster) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIES_COLUMN_TITLE, title);
        contentValues.put(MOVIES_COLUMN_YEAR, year);
        contentValues.put(MOVIES_COLUMN_RUNTIME, runtime);
        contentValues.put(MOVIES_COLUMN_GENRE, genre);
        contentValues.put(MOVIES_COLUMN_ACTORS, actors);
        contentValues.put(MOVIES_COLUMN_POSTER, poster);
        db.update(TABLE_MOVIES, contentValues, MOVIES_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MOVIES, MOVIES_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getAllMovies() {
        ArrayList<String> array_list = new ArrayList<String>();

        // get readable database get all records
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_MOVIES, null);
        res.moveToFirst();

        //put titles in arraylist
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(MOVIES_COLUMN_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_MOVIES + " WHERE " + MOVIES_COLUMN_ID + "= " + id + ";";
        Cursor rs = db.rawQuery(query, null);
        return rs;
    }

    public static String getMoviesColumnActors() {
        return MOVIES_COLUMN_ACTORS;
    }

    public static String getMoviesColumnGenre() {
        return MOVIES_COLUMN_GENRE;
    }

    public static String getMoviesColumnId() {
        return MOVIES_COLUMN_ID;
    }

    public static String getMoviesColumnPoster() {
        return MOVIES_COLUMN_POSTER;
    }

    public static String getMoviesColumnRuntime() {
        return MOVIES_COLUMN_RUNTIME;
    }

    public static String getMoviesColumnTitle() {
        return MOVIES_COLUMN_TITLE;
    }

    public static String getMoviesColumnYear() {
        return MOVIES_COLUMN_YEAR;
    }
}
