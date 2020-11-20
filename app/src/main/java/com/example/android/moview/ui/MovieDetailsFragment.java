package com.example.android.moview.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moview.BuildConfig;
import com.example.android.moview.R;
import com.example.android.moview.data.FavoriteDbHelper;
import com.example.android.moview.network.ApiService;
import com.example.android.moview.network.response.MovieResponse;
import com.example.android.moview.network.response.ReviewResult;
import com.example.android.moview.network.response.TrailerResult;
import com.example.android.moview.utils.Mapper;
import com.example.android.moview.utils.Movie;
import com.example.android.moview.utils.Review;
import com.example.android.moview.utils.ReviewAdapter;
import com.example.android.moview.utils.Trailer;
import com.example.android.moview.utils.TrailerAdapter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.moview.R.string.error_showing_movie_details;
import static com.example.android.moview.R.string.error_showing_reviews;
import static com.example.android.moview.R.string.error_showing_trailers;

public class MovieDetailsFragment extends Fragment implements TrailerAdapter.ListItemClickListener, ReviewAdapter.ListItemClickListener {

    public static final String ARG_MOVIE = "ARG_MOVIE";
    private final String[] errorType = {""};
    private final String[] errorDesc = {""};
    private Movie movie;
    private View detailsView;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private Toast mToast;
    private FavoriteDbHelper favoriteDbHelper;

    public static MovieDetailsFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        detailsView = inflater.inflate(R.layout.movie_details_fragment, container, false);
        Bundle bundle = this.getArguments();
        configTrailerAdapter();
        configReviewAdapter();

        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        if (bundle != null) {
            movie = (Movie) bundle.getSerializable(ARG_MOVIE);
            movieDetails();
        }
        return detailsView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // Configure Trailer Adapter
    private void configTrailerAdapter() {
        RecyclerView recyclerTrailer = detailsView.findViewById(R.id.rview_trailers);
        trailerAdapter = new TrailerAdapter(this);
        RecyclerView.LayoutManager trailerLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerTrailer.setLayoutManager(trailerLayoutManager);
        recyclerTrailer.setAdapter(trailerAdapter);
    }

    // Configure Review Adapter
    private void configReviewAdapter() {
        RecyclerView recyclerReview = detailsView.findViewById(R.id.rview_reviews);
        reviewAdapter = new ReviewAdapter(this);
        RecyclerView.LayoutManager reviewLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerReview.setLayoutManager(reviewLayoutManager);
        recyclerReview.setAdapter(reviewAdapter);
    }

    // Opens youtube with the Trailer Key
    public static void watchYoutubeVideo(Context context, String key) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    // Calls diferent functions to set up the movie page
    private void movieDetails() {
        // Gets Title and details
        getMovieDet();
        // Gets Movie Details
        getTrailers();
        // Gets Movie Reviews
        getReviews();
        // Checks if movie is on favorite list and add's//delete if the user click on switch
        getFavs();
    }

    // Gets the Movie Details from the API
    private void getMovieDet() {
        ApiService.getInstance()
                .getMovieDetails(movie.getMovieId(), BuildConfig.API_KEY)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            TextView movieTitle = detailsView.findViewById(R.id.txt_movie_title);
                            movieTitle.setText(response.body().getOriginalTitle());
                            TextView movieYear = detailsView.findViewById(R.id.txt_movie_year);
                            movieYear.setText(response.body().getReleaseDate());
                            TextView movieOverview = detailsView.findViewById(R.id.txt_movie_overview);
                            movieOverview.setText(response.body().getOverview());
                            ImageView moviePoster = detailsView.findViewById(R.id.image_movie_poster);
                            Picasso.get()
                                    .load("https://image.tmdb.org/t/p/w342/" + response.body().getPosterPath())
                                    .into(moviePoster);
                            ImageView background = detailsView.findViewById(R.id.image_background);
                            Picasso.get()
                                    .load("https://image.tmdb.org/t/p/w780/" + response.body().getPosterPath())
                                    .into(background);
                            TextView movieTime = detailsView.findViewById(R.id.txt_movie_time);
                            movieTime.setText(String.valueOf(response.body().getRuntime()));
                            TextView movieRank = detailsView.findViewById(R.id.txt_movie_rank);
                            String rank = response.body().getRank() + "/10";
                            movieRank.setText(rank);
                        } else {
                            Toast.makeText(getActivity(), error_showing_movie_details, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), error_showing_movie_details, Toast.LENGTH_SHORT).show();
                        showError(t);
                    }
                });
    }

    // Gets Trailers from the API
    private void getTrailers() {
        ApiService.getInstance()
                .getMovieTrailers(movie.getMovieId(), BuildConfig.API_KEY)
                .enqueue(new Callback<TrailerResult>() {
                    @Override
                    public void onResponse(@NonNull Call<TrailerResult> call, @NonNull Response<TrailerResult> response) {
                        if (response.isSuccessful()) {
                            trailerAdapter.setTrailers(
                                    Mapper.fromResponseToMainTrailer(response.body().getTrailerResults())
                            );
                        } else {
                            Toast.makeText(getActivity(), error_showing_trailers, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrailerResult> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), error_showing_trailers, Toast.LENGTH_SHORT).show();
                        showError(t);
                    }
                });

    }

    // Gets Movie Reviews from the API
    private void getReviews() {
        ApiService.getInstance()
                .getMovieReviews(movie.getMovieId(), BuildConfig.API_KEY)
                .enqueue(new Callback<ReviewResult>() {
                    @Override
                    public void onResponse(@NonNull Call<ReviewResult> call, @NonNull Response<ReviewResult> response) {
                        if (response.isSuccessful()) {
                            reviewAdapter.setReviews(
                                    Mapper.fromResponseToMainReview(response.body().getReviewResults())
                            );
                        } else {
                            Toast.makeText(getActivity(), error_showing_reviews, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ReviewResult> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), error_showing_reviews, Toast.LENGTH_SHORT).show();
                        showError(t);
                    }
                });
    }

    // Gets Favorits from the Database and Add's or Deletes from database
    private void getFavs() {
        Switch favSwitch = detailsView.findViewById(R.id.switch_fav);

        favoriteDbHelper = new FavoriteDbHelper(getActivity());
        List<Movie> favs = favoriteDbHelper.getAllFavorite();
        for (int i = 0; i < favs.size(); i++) {
            if (movie.getMovieId() == favs.get(i).getMovieId()) {
                favSwitch.setChecked(true);
            }
        }
        favSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveFavorite(movie);
                } else {
                    for (int i = 0; i < favs.size(); i++) {
                        if (movie.getMovieId() == favs.get(i).getMovieId()) {
                            favoriteDbHelper.deleteFavorite(movie.getMovieId());
                        }
                    }
                }
            }
        });
    }

    // Click responce when a trailer is clicked
    @Override
    public void onListItemClick(Trailer trailer) {
        watchYoutubeVideo(getActivity(), trailer.getKey());
    }

    private void showError(Throwable t) {
        if (t instanceof IOException) {
            errorType[0] = getString(R.string.Timeout);
            errorDesc[0] = String.valueOf(t.getCause());
            Log.i(errorType[0], errorDesc[0]);
        } else if (t instanceof IllegalStateException) {
            errorType[0] = getString(R.string.conversion_error);
            errorDesc[0] = String.valueOf(t.getCause());
            Log.i(errorType[0], errorDesc[0]);
        } else {
            errorType[0] = getString(R.string.other_error);
            errorDesc[0] = String.valueOf(t.getLocalizedMessage());
            Log.i(errorType[0], errorDesc[0]);
        }
    }

    // Adds Movie on the database
    public void saveFavorite(Movie movie) {
        favoriteDbHelper = new FavoriteDbHelper(getActivity());
        Movie favorite = new Movie();

        favorite.setMovieID(movie.getMovieId());
        favorite.setOriginalTitle(movie.getOriginalTitle());
        favorite.setPosterPath(movie.getPosterPath());
        favorite.setOverview(movie.getOverview());

        favoriteDbHelper.addFavorite(favorite);
    }

    // Click Responce when a Review is clicked
    @Override
    public void onListItemClick(Review review) {

    }

}