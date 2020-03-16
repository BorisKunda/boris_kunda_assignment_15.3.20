package com.assignment.boris_kunda_assignment_15320.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.assignment.boris_kunda_assignment_15320.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAllMovies (List<Movie> moviesList);

    @Query ("SELECT * from movies_table ORDER BY releaseYear DESC")
    LiveData<List<Movie>> getMoviesList ();

}
