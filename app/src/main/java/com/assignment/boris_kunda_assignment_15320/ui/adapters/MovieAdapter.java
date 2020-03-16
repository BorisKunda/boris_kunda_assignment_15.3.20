package com.assignment.boris_kunda_assignment_15320.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.boris_kunda_assignment_15320.R;
import com.assignment.boris_kunda_assignment_15320.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> mMovieList;

    public MovieAdapter () {
        mMovieList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder (@NonNull MovieViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        holder.mMovieTitleTv.setText(movie.getTitle());
        // TODO: 3/16/2020 image
    }

    @Override
    public int getItemCount () {
        return mMovieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView mMovieTitleTv;
        private ImageView mMoviePosterIv;

        MovieViewHolder (@NonNull View itemView) {
            super(itemView);
            mMovieTitleTv = itemView.findViewById(R.id.movie_item_title_tv);
            mMoviePosterIv = itemView.findViewById(R.id.movie_item_poster_iv);
        }

    }

    public void setMovieList (List<Movie> iMovieList) {
        mMovieList = iMovieList;
        notifyDataSetChanged();
    }

}
