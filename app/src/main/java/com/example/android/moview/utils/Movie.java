package com.example.android.moview.utils;

import java.io.Serializable;

public class Movie implements Serializable {

    private final int movieId;
    private final String originalTitle;
    private final String posterPath;
    private final float popularity;
    private final String releaseDate;
    private final String overview;
    private final int runtime;
    private final boolean video;
    private final float rank;

    public Movie(int movieId, String originalTitle, String posterPath, float popularity, String releaseDate, String overview, int runtime, boolean video, float rank) {
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.runtime = runtime;
        this.video = video;
        this.rank = rank;
    }

    public float getRank() {
        return rank;
    }

    public int getMovieId() {
        return movieId;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public boolean isVideo() {
        return video;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
