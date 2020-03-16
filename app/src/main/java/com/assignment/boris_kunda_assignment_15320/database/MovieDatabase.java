package com.assignment.boris_kunda_assignment_15320.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.assignment.boris_kunda_assignment_15320.model.Movie;

@Database (entities = { Movie.class }, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase mMovieDatabase = null;
    private static final String DATABASE_NAME = "MovieDatabase";


    public static MovieDatabase getMovieDatabase (Application iApplication) {

        if (mMovieDatabase == null) {

            mMovieDatabase = Room.databaseBuilder(iApplication, MovieDatabase.class, DATABASE_NAME).build();

        }

        return mMovieDatabase;
    }

    public abstract MovieDao getMovieDao ();

}
