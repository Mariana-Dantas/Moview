package com.example.android.moview.network.response;

import com.squareup.moshi.Json;

import java.util.List;

public class MovieResult {

    @Json(name = "results")
    private final List<MovieResponse> movieResults;

    public MovieResult(List<MovieResponse> movieResults) {
        this.movieResults = movieResults;
    }

    public List<MovieResponse> getMovieResults() {
        return movieResults;
    }
}
