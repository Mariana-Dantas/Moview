package com.example.android.moview.utils;

import java.io.Serializable;

public class Movie implements Serializable {

    private int movieId;
    private String originalTitle;
    private String posterPath;
    private float popularity;
    private String releaseDate;
    private String overview;
    private int runtime;
    private boolean video;
    private float rank;

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

    public Movie() {
        //this.movieId = 0;
        //this.originalTitle = "";
        //this.posterPath = "";
        //this.overview = "";
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

    public void setMovieID(int id) {
        this.movieId = id;
    }

    public void setOriginalTitle(String title) {
        this.originalTitle = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

}
