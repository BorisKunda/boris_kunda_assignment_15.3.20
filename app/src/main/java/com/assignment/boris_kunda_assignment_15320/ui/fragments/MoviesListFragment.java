package com.assignment.boris_kunda_assignment_15320.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assignment.boris_kunda_assignment_15320.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesListFragment extends Fragment {

    public MoviesListFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false);
    }

}
