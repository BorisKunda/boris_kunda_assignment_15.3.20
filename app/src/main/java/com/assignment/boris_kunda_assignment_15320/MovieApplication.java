package com.assignment.boris_kunda_assignment_15320;

import android.app.Application;

import com.assignment.boris_kunda_assignment_15320.repository.MovieRepository;

public class MovieApplication extends Application {

    @Override
    public void onCreate () {
        super.onCreate();
        MovieRepository.getMovieRepository(this);
    }

}
