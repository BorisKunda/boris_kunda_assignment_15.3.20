package com.assignment.boris_kunda_assignment_15320.ui.activities;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.assignment.boris_kunda_assignment_15320.R;
import com.assignment.boris_kunda_assignment_15320.model.Movie;
import com.assignment.boris_kunda_assignment_15320.ui.fragments.MovieDetailsFragment;
import com.assignment.boris_kunda_assignment_15320.ui.fragments.MoviesListFragment;
import com.assignment.boris_kunda_assignment_15320.ui.fragments.QRFragment;

public class MainActivity extends AppCompatActivity implements MoviesListFragment.OnListItemClickedListener {

    private MoviesListFragment mMoviesListFragment;
    private MovieDetailsFragment mMovieDetailsFragment;
    private QRFragment mQrFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUi();
        setFragments();

        //default fragment
        openMoviesListFragment();


    }

    private void setFragments () {
        instantiateFragments();
        mFragmentManager = getSupportFragmentManager();
    }

    private void instantiateFragments () {
        mMoviesListFragment = new MoviesListFragment();
        mMovieDetailsFragment = new MovieDetailsFragment();
        mQrFragment = new QRFragment();
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

    // TODO: 3/17/2020 make one method to open all fragments
    private void openMoviesListFragment () {
        mFragmentManager.beginTransaction().replace(R.id.containerFr, mMoviesListFragment).addToBackStack(MoviesListFragment.class.getSimpleName()).commit();
    }

    private void openMovieDetailsFragment (Movie iMovie) {
        mMovieDetailsFragment.setMovie(iMovie);
        mFragmentManager.beginTransaction().replace(R.id.containerFr, mMovieDetailsFragment).addToBackStack(MoviesListFragment.class.getSimpleName()).commit();
    }

    private void openQRFragment () {
        mFragmentManager.beginTransaction().replace(R.id.containerFr, mMovieDetailsFragment).addToBackStack(MoviesListFragment.class.getSimpleName()).commit();
    }

}
