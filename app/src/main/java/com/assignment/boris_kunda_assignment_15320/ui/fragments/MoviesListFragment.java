package com.assignment.boris_kunda_assignment_15320.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.boris_kunda_assignment_15320.R;
import com.assignment.boris_kunda_assignment_15320.model.Movie;
import com.assignment.boris_kunda_assignment_15320.ui.adapters.MovieAdapter;
import com.assignment.boris_kunda_assignment_15320.viewmodel.MovieViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesListFragment extends Fragment implements MovieAdapter.OnMovieClickListener {

    private MovieViewModel mMovieViewModel;
    private MovieAdapter mMovieAdapter;
    private OnListItemClickedListener mOnListItemClickedListener;
    private FloatingActionButton mFloatingActionButton;
    private OnFABClickedListener mOnFABClickedListener;


    public MoviesListFragment () {
        // Required empty public constructor
    }

    public interface OnListItemClickedListener {

        void onListItemClickedListener (Movie iMovie);

    }

    public interface OnFABClickedListener {

        void onFABClicked ();

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_movies_list, container, false);

        setUi(v);
        setViewModel();

        mMovieViewModel.getMovieListLD().observe(this, iMovies -> mMovieAdapter.setMovieList(iMovies));

        return v;
    }

    private void setUi (View v) {
        setRV(v);
        setFab(v);
    }

    private void setFab (View v) {
        mFloatingActionButton = v.findViewById(R.id.movies_fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View v) {
                mOnFABClickedListener.onFABClicked();
            }
        });
    }

    private void setRV (View v) {
        RecyclerView recyclerView = v.findViewById(R.id.movies_rv);
        mMovieAdapter = new MovieAdapter(this, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mMovieAdapter);

    }

    @Override
    public void onMovieClick (Movie iMovie) {
        mOnListItemClickedListener.onListItemClickedListener(iMovie);
    }

    @Override
    public void onAttach (@NonNull Context context) {
        super.onAttach(context);
        mOnListItemClickedListener = (OnListItemClickedListener) context;
        mOnFABClickedListener = (OnFABClickedListener) context;
    }

    private void setViewModel () {
        mMovieViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MovieViewModel.class);
    }

}
