package com.assignment.boris_kunda_assignment_15320.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.assignment.boris_kunda_assignment_15320.database.MovieDao;
import com.assignment.boris_kunda_assignment_15320.database.MovieDatabase;
import com.assignment.boris_kunda_assignment_15320.model.Movie;
import com.assignment.boris_kunda_assignment_15320.network.MovieApi;
import com.assignment.boris_kunda_assignment_15320.viewmodel.SingleLiveEvent;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MovieRepository {

    private static MovieRepository mMovieRepository = null;
    private MovieApi mCountryApi;
    private MovieDao mDao;
    private ExecutorService mExecutorService;
    private SingleLiveEvent<Boolean> mDisplayMovieAlreadyExistsPopUpMd;


    private MovieRepository (Application iApplication) {
        mCountryApi = MovieApi.getMovieApi(iApplication);
        MovieDatabase movieDatabase = MovieDatabase.getMovieDatabase(iApplication);
        mDao = movieDatabase.getMovieDao();
        mExecutorService = Executors.newSingleThreadExecutor();
        mDisplayMovieAlreadyExistsPopUpMd = new SingleLiveEvent<>();
    }

    public static MovieRepository getMovieRepository (Application iApplication) {

        if (mMovieRepository == null) {
            mMovieRepository = new MovieRepository(iApplication);
        }

        return mMovieRepository;
    }

    public LiveData<List<Movie>> getMoviesListLD () {
        return mDao.getMoviesList();
    }

    public int getNumberOfRowsWithThisKey (String iMovieTitle) {
        return mDao.getNumberOfRowsWithThisKey(iMovieTitle);
    }

    public void loadMoviesListFromApi () {

        mCountryApi.requestMovieList((isSuccess, response) -> {

            if (isSuccess) {
                Gson gson = new Gson();
                Movie[] movies = gson.fromJson(String.valueOf(response), Movie[].class);
                mExecutorService.execute(() -> mDao.insertAllMovies(Arrays.asList(movies)));
            }

        });
    }

    public void updateDbOrDisplayPopUp (Movie iMovie) {
        if ((mMovieRepository.getNumberOfRowsWithThisKey(iMovie.getTitle())) != 0) {
            mDisplayMovieAlreadyExistsPopUpMd.postValue(true);
        } else {
            mDao.insertMovie(iMovie);
        }
    }

    public SingleLiveEvent<Boolean> displayMovieAlreadyExistsPopUpMd () {
        return mDisplayMovieAlreadyExistsPopUpMd;
    }

}
