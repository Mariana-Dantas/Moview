package com.example.android.moview.utils;

import com.example.android.moview.network.response.MovieResponse;
import com.example.android.moview.network.response.ReviewResponse;
import com.example.android.moview.network.response.TrailerResponse;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static List<Movie> fromResponseToMainMovie(List<MovieResponse> movieListResponse) {
        List<Movie> movieList = new ArrayList<>();
        for (MovieResponse movieResponse : movieListResponse) {
            final Movie movie = new Movie(
                    movieResponse.getMovieId(),
                    movieResponse.getOriginalTitle(),
                    movieResponse.getPosterPath(),
                    movieResponse.getPopularity(),
                    movieResponse.getReleaseDate(),
                    movieResponse.getOverview(),
                    movieResponse.getRuntime(),
                    movieResponse.isVideo(),
                    movieResponse.getRank());
            movieList.add(movie);
        }
        return movieList;
    }

    public static List<Trailer> fromResponseToMainTrailer(List<TrailerResponse> trailerListResponse) {
        List<Trailer> trailerList = new ArrayList<>();
        for (TrailerResponse trailerResponse : trailerListResponse) {
            final Trailer trailer = new Trailer(
                    trailerResponse.getTrailerID(),
                    trailerResponse.getIso_639_1(),
                    trailerResponse.getIso_3166_1(),
                    trailerResponse.getKey(),
                    trailerResponse.getName(),
                    trailerResponse.getSite(),
                    trailerResponse.getSize(),
                    trailerResponse.getType());
            trailerList.add(trailer);
        }
        return trailerList;
    }

    public static List<Review> fromResponseToMainReview(List<ReviewResponse> reviewListResponse) {
        List<Review> reviewList = new ArrayList<>();
        for (ReviewResponse reviewResponse : reviewListResponse) {
            final Review review = new Review(
                    reviewResponse.getReviewId(),
                    reviewResponse.getAuthor(),
                    reviewResponse.getAuthor(),
                    reviewResponse.getUrl());
            reviewList.add(review);
        }
        return reviewList;
    }
}
