package com.assignment.boris_kunda_assignment_15320.ui.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.assignment.boris_kunda_assignment_15320.R;
import com.assignment.boris_kunda_assignment_15320.ui.fragments.MovieDetailsFragment;
import com.assignment.boris_kunda_assignment_15320.ui.fragments.MoviesListFragment;
import com.assignment.boris_kunda_assignment_15320.ui.fragments.QRFragment;

public class MainActivity extends AppCompatActivity {

    private MoviesListFragment mMoviesListFragment;
    private MovieDetailsFragment mMovieDetailsFragment;
    private QRFragment mQrFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instantiateFragments();

        mFragmentManager = getSupportFragmentManager();


        mFragmentManager.beginTransaction().replace(R.id.containerFr, mMoviesListFragment).addToBackStack(MoviesListFragment.class.getSimpleName()).commit();

    }

    private void instantiateFragments () {
        mMoviesListFragment = new MoviesListFragment();
        mMovieDetailsFragment = new MovieDetailsFragment();
        mQrFragment = new QRFragment();
    }

}
