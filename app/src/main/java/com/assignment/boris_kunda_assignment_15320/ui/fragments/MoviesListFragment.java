package com.assignment.boris_kunda_assignment_15320.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.boris_kunda_assignment_15320.R;
import com.assignment.boris_kunda_assignment_15320.ui.adapters.MovieAdapter;
import com.assignment.boris_kunda_assignment_15320.viewmodel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesListFragment extends Fragment {

    private MovieViewModel mMovieViewModel;
    private MovieAdapter mMovieAdapter;
    private Context mContext;

    public MoviesListFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_movies_list, container, false);

        setRV(v);

        mMovieViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MovieViewModel.class);

        mMovieViewModel.getMovieListLD().observe(this, iMovies -> mMovieAdapter.setMovieList(iMovies));

        // Inflate the layout for this fragment
        return v;
    }

    private void setRV (View v) {
        RecyclerView recyclerView = v.findViewById(R.id.movies_rv);
        mMovieAdapter = new MovieAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mMovieAdapter);
    }

    // TODO: 3/16/2020 add context from attach


}
