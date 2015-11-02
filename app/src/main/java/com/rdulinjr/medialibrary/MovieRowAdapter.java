package com.rdulinjr.medialibrary;

import android.content.Context;
import android.os.Bundle;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Rj on 11/1/2015.
 */
public class MovieRowAdapter extends CursorAdapter {

    private ImageView poster;
    private TextView title;
    private TextView year;

    public MovieRowAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.movie_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        poster = (ImageView) view.findViewById(R.id.movierow_poster);
        title = (TextView) view.findViewById(R.id.movierow_title);
        year = (TextView) view.findViewById(R.id.movierow_year);

        // probalbly should catch an exception
        new DownloadImageTask(poster).execute(cursor.getString(cursor.getColumnIndex(LibraryDBHandler.getMoviesColumnPoster())));
        title.setText(cursor.getString(cursor.getColumnIndex(LibraryDBHandler.getMoviesColumnTitle())));
        year.setText("(" + cursor.getString(cursor.getColumnIndex(LibraryDBHandler.getMoviesColumnYear())) + ")");
    }
}
