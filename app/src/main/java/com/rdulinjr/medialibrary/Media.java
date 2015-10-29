package com.rdulinjr.medialibrary;

/**
 * Created by Rj on 10/28/2015.
 */
public class Media {

    private int _id;
    private String _title;
    private String _year;
    private String _runtime;
    private String _genre;
    private String _actors;

    public Media() {
        
    }

    public String get_actors() {
        return _actors;
    }

    public void set_actors(String _actors) {
        this._actors = _actors;
    }

    public String get_genre() {
        return _genre;
    }

    public void set_genre(String _genre) {
        this._genre = _genre;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_runtime() {
        return _runtime;
    }

    public void set_runtime(String _runtime) {
        this._runtime = _runtime;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_year() {
        return _year;
    }

    public void set_year(String _year) {
        this._year = _year;
    }
}
