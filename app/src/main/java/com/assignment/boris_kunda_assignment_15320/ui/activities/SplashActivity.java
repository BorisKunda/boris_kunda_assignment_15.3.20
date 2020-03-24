package com.assignment.boris_kunda_assignment_15320.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.assignment.boris_kunda_assignment_15320.R;
import com.assignment.boris_kunda_assignment_15320.viewmodel.MovieViewModel;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_COUNT_FINISH = 5000;
    private final int SPLASH_COUNT_INTERVAL = 1000;
    private boolean mIsLoadFinished = false;
    private MovieViewModel mMovieViewModel;
    private int mTemp;
    private AlertDialog mAlertDialog;
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setViewModel();
        subscribeToViewModel();
        buildDialog();
        setCountDown();
        mCountDownTimer.start();
        getSupportActionBar().hide();
        setStatusBarColor();

    }

    private void setViewModel () {
        mMovieViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MovieViewModel.class);
    }

    private void subscribeToViewModel () {
        mMovieViewModel.getMovieListLD().observe(this, iMovies -> {
            if (iMovies.size() == 0) {
                mMovieViewModel.loadMoviesList();
            } else {
                mIsLoadFinished = true;
            }
        });
    }

    private void setCountDown () {
        mCountDownTimer = new CountDownTimer(SPLASH_COUNT_FINISH, SPLASH_COUNT_INTERVAL) {

            public void onFinish () {
                runOnUiThread(() -> {
                    mAlertDialog.show();
                    cancel();
                });
            }

            public void onTick (long millisUntilFinished) {
                mTemp++;

                if (mIsLoadFinished && mTemp > 3) {
                    runOnUiThread(() -> {
                        Intent mMainActivityIntent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(mMainActivityIntent);
                        cancel();
                        finish();
                    });
                }

            }
        };
    }

    private void buildDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        // Add the buttons
        builder.setPositiveButton("RETRY", (dialog, id) -> {
            mMovieViewModel.loadMoviesList();
            mCountDownTimer.start();
        });
        builder.setNegativeButton("CLOSE", (dialog, id) -> {
            finish();
        });

        mAlertDialog = builder.create();
        mAlertDialog.setIcon(R.drawable.error);
        mAlertDialog.setTitle("Error");
        mAlertDialog.setMessage("Something wrong please try again");
    }

    private void setStatusBarColor () {
        // TODO: 3/16/2020 check this code and make activity white like android default white color
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.grey));
    }

}
