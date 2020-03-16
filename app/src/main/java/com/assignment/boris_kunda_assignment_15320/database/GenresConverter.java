package com.assignment.boris_kunda_assignment_15320.database;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenresConverter {

    @TypeConverter
    String fromGenres (List<String> iGenreList) {
        return iGenreList.stream().collect(Collectors.joining(","));
    }

    @TypeConverter
    List<String> toGenres (String data) {
        return Arrays.asList(data.split(","));
    }


}
