package com.assignment.boris_kunda_assignment_15320.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.assignment.boris_kunda_assignment_15320.AppUtils;
import com.assignment.boris_kunda_assignment_15320.R;
import com.assignment.boris_kunda_assignment_15320.model.Movie;
import com.assignment.boris_kunda_assignment_15320.ui.fragments.MovieDetailsFragment;
import com.assignment.boris_kunda_assignment_15320.ui.fragments.MoviesListFragment;
import com.assignment.boris_kunda_assignment_15320.viewmodel.MovieViewModel;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements MoviesListFragment.OnListItemClickedListener, MoviesListFragment.OnFABClickedListener {

    private MoviesListFragment mMoviesListFragment;
    private MovieDetailsFragment mMovieDetailsFragment;
    private FragmentManager mFragmentManager;
    MovieViewModel mMovieViewModel;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUi();
        setFragments();
        setViewModel();

        //default fragment
        openMoviesListFragment();

        mMovieViewModel.displayMovieExistsPopUpLd().observe(this, iBoolean -> {
            if (iBoolean) {
                LinearLayout linearLayout = findViewById(R.id.containerFr);

                Snackbar.make(linearLayout, R.string.movie_is_in_db_already_pop_up, Snackbar.LENGTH_INDEFINITE)
                        .setAction("ok", v -> {
                        })
                        .setActionTextColor(getResources().getColor(R.color.colorAccent))
                        .show();
            }
        });


    }

    private void setFragments () {
        instantiateFragments();
        mFragmentManager = getSupportFragmentManager();
    }

    private void instantiateFragments () {
        mMoviesListFragment = new MoviesListFragment();
        mMovieDetailsFragment = new MovieDetailsFragment();
    }

    private void setUi () {
        getSupportActionBar().hide();
        AppUtils.setStatusBarColor(getWindow(), getResources());
    }


    @Override
    public void onListItemClickedListener (Movie iMovie) {
        openMovieDetailsFragment(iMovie);
    }

    @Override
    public void onFABClicked () {
        Intent intent = new Intent(MainActivity.this, QRActivity.class);
        startActivity(intent);
    }

    private void openMoviesListFragment () {
        mFragmentManager.beginTransaction().replace(R.id.containerFr, mMoviesListFragment).commit();
    }

    private void openMovieDetailsFragment (Movie iMovie) {
        mMovieDetailsFragment.setMovie(iMovie);
        mFragmentManager.beginTransaction().replace(R.id.containerFr, mMovieDetailsFragment).addToBackStack(MoviesListFragment.class.getSimpleName()).commit();
    }

    private void setViewModel () {
        mMovieViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MovieViewModel.class);
    }


}


