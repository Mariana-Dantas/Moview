package com.example.android.moview.network.response;

import com.squareup.moshi.Json;

public class MovieResponse {

    @Json(name = "id")
    private final int movieId;
    @Json(name = "poster_path")
    private final String posterPath;
    @Json(name = "original_title")
    private final String originalTitle;
    @Json(name = "popularity")
    private final float popularity;
    @Json(name = "release_date")
    private final String releaseDate;
    @Json(name = "overview")
    private final String overview;
    @Json(name = "runtime")
    private final int runtime;
    @Json(name = "video")
    private final boolean video;
    @Json(name = "vote_average")
    private final float rank;

    public MovieResponse(int movieId, String posterPath, String originalTitle, float popularity, String releaseDate, String overview, int runtime, boolean video, float rank) {
        this.movieId = movieId;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.runtime = runtime;
        this.video = video;
        this.rank = rank;
    }

    public boolean isVideo() {
        return video;
    }

    public float getRank() {
        return rank;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
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

}
