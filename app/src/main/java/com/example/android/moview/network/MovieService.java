package com.example.android.moview.network;

import com.example.android.moview.network.response.MovieResponse;
import com.example.android.moview.network.response.MovieResult;
import com.example.android.moview.network.response.ReviewResult;
import com.example.android.moview.network.response.TrailerResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/popular")
    Call<MovieResult> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResult> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieResponse> getMovieDetails(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResult> getMovieTrailers(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResult> getMovieReviews(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResult> getMovieSearch(@Query("query") String query, @Query("api_key") String apiKey);

}
