package com.assignment.boris_kunda_assignment_15320.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.assignment.boris_kunda_assignment_15320.R;
import com.assignment.boris_kunda_assignment_15320.database.GenresConverter;
import com.assignment.boris_kunda_assignment_15320.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment {

    private Movie mMovie;
    private ImageView mPosterIv;
    private TextView mTitleTv, mRatingTv, mReleaseYearTv, mGenresTv;

    public MovieDetailsFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_movie_details, container, false);

        initUi(v);
        setUi(mMovie);

        return v;
    }

    private void initUi (View iView) {
        mPosterIv = iView.findViewById(R.id.movie_details_poster_iv);
        mTitleTv = iView.findViewById(R.id.movie_details_title_tv);
        mRatingTv = iView.findViewById(R.id.movie_details_rating_tv);
        mReleaseYearTv = iView.findViewById(R.id.movie_details_release_year_tv);
        mGenresTv = iView.findViewById(R.id.movie_details_genres_tv);
    }

    private void setUi (Movie iMovie) {
        mTitleTv.setText(iMovie.getTitle());
        Picasso.get().load(iMovie.getImageUrl()).placeholder(R.drawable.movie_place_holder).into(mPosterIv);
        mRatingTv.setText(iMovie.getRating());
        mReleaseYearTv.setText(iMovie.getReleaseYear());
        mGenresTv.setText(concatenateGenres(iMovie.getGenreList()));

    }

    private String concatenateGenres (List<String> iGenresStrings) {
        return new GenresConverter().fromGenres(iGenresStrings);
    }

    public void setMovie (Movie iMovie) {
        mMovie = iMovie;
    }

}
