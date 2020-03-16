package com.assignment.boris_kunda_assignment_15320.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.assignment.boris_kunda_assignment_15320.R;
import com.assignment.boris_kunda_assignment_15320.viewmodel.MovieViewModel;

public class SplashActivity extends AppCompatActivity {

    // TODO: 3/16/2020  add method to utils which setViewModel

    private Handler mHandler;
    private Intent mMainActivityIntent;
    private final int SPLASH_TIME = 3000;
    private MovieViewModel mMovieViewModel;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mMovieViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MovieViewModel.class);

        Log.e(getClass().getSimpleName(), "onCreate");

        mMovieViewModel.getMovieListLD().observe(this, iMovies -> {
            if (iMovies.size() == 0) {
                Log.e("DB_EMPTY", "onChanged");
                mMovieViewModel.loadMoviesList();
            } else {
                Log.e("DB_FULL", "onChanged");
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
