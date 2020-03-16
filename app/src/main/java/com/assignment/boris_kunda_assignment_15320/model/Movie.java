package com.assignment.boris_kunda_assignment_15320.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.assignment.boris_kunda_assignment_15320.database.GenresConverter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity (tableName = "movies_table")
public class Movie {

    @PrimaryKey
    @NonNull
    private String title;
    @SerializedName ("image")

    private String imageUrl;

    private String rating;

    private String releaseYear;

    @TypeConverters ({ GenresConverter.class })
    @SerializedName ("genre")
    private List<String> genreList;

    //getters + setters
    @NonNull
    public String getTitle () {
        return title;
    }


    public void setTitle (@NonNull String iTitle) {
        title = iTitle;
    }

    public String getImageUrl () {
        return imageUrl;
    }

    public void setImageUrl (String iImageUrl) {
        imageUrl = iImageUrl;
    }

    public String getRating () {
        return rating;
    }

    public void setRating (String iRating) {
        rating = iRating;
    }

    public String getReleaseYear () {
        return releaseYear;
    }

    public void setReleaseYear (String iReleaseYear) {
        releaseYear = iReleaseYear;
    }

    public List<String> getGenreList () {
        return genreList;
    }

    public void setGenreList (List<String> iGenreList) {
        genreList = iGenreList;
    }


}
