package com.assignment.boris_kunda_assignment_15320.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

        mMovieViewModel.displayMovieExistsPopUpLd().observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged (Boolean iBoolean) {
                if (iBoolean) {
                    LinearLayout linearLayout = findViewById(R.id.containerFr);

                    Snackbar.make(linearLayout, R.string.movie_is_in_db_already_pop_up, Snackbar.LENGTH_INDEFINITE)
                            .setAction("ok", new View.OnClickListener() {

                                @Override
                                public void onClick (View v) {
                                    //snackbar action
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.colorAccent))
                            .show();
                }
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
        setStatusBarColor();
    }

    private void setStatusBarColor () {
        // TODO: 3/16/2020 check this code and make activity white like android default white color
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.white));
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

    // TODO: 3/17/2020 make one method to open all fragments
    private void openMoviesListFragment () {
        mFragmentManager.beginTransaction().replace(R.id.containerFr, mMoviesListFragment).commit();
    }

    private void openMovieDetailsFragment (Movie iMovie) {
        mMovieDetailsFragment.setMovie(iMovie);// TODO: 3/17/2020 find better way this crashes some time
        mFragmentManager.beginTransaction().replace(R.id.containerFr, mMovieDetailsFragment).addToBackStack(MoviesListFragment.class.getSimpleName()).commit();
    }

    //todo
    //make one util function for this
    private void setViewModel () {
        mMovieViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MovieViewModel.class);
    }

}


