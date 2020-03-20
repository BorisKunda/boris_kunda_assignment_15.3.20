package com.assignment.boris_kunda_assignment_15320.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.assignment.boris_kunda_assignment_15320.R;
import com.assignment.boris_kunda_assignment_15320.viewmodel.MovieViewModel;

public class SplashActivity extends AppCompatActivity {

    // TODO: 3/16/2020  add method to utils which setViewModel

    // TODO: 3/17/2020 finish splash screen

    private Handler mHandler;
    private Intent mMainActivityIntent;
    private final int SPLASH_TIME = 666000;
    private MovieViewModel mMovieViewModel;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mMovieViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MovieViewModel.class);

        mMovieViewModel.getMovieListLD().observe(this, iMovies -> {
            if (iMovies.size() == 0) {

                mMovieViewModel.loadMoviesList();
            } else {

            }
        });

        mHandler = new Handler();

        mHandler.postDelayed(() -> {
            mMainActivityIntent = new Intent(SplashActivity.this, MainActivity.class);
            finish();
            startActivity(mMainActivityIntent);
        }, SPLASH_TIME);

    }


}
