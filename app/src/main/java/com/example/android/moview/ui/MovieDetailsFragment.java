package com.example.android.moview.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moview.BuildConfig;
import com.example.android.moview.R;
import com.example.android.moview.network.ApiService;
import com.example.android.moview.network.response.ReviewResult;
import com.example.android.moview.network.response.TrailerResult;
import com.example.android.moview.utils.Mapper;
import com.example.android.moview.utils.Movie;
import com.example.android.moview.utils.Review;
import com.example.android.moview.utils.ReviewAdapter;
import com.example.android.moview.utils.Trailer;
import com.example.android.moview.utils.TrailerAdapter;
import com.example.android.moview.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsFragment extends Fragment implements TrailerAdapter.ListItemClickListener, ReviewAdapter.ListItemClickListener {

    public static final String ARG_MOVIE = "ARG_MOVIE";
    private final String[] errorType = {""};
    private final String[] errorDesc = {""};
    private Movie movie;
    private View detailsView;
    private RecyclerView recyclerTrailer;
    private TrailerAdapter trailerAdapter;
    private RecyclerView recyclerReview;
    private ReviewAdapter reviewAdapter;
    private Toast mToast;

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

        if (bundle != null) {
            movie = (Movie) bundle.getSerializable(ARG_MOVIE);
            MovieDetails(movie);
        }
        return detailsView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configTrailerAdapter();
    }

    private void MovieDetails(Movie movie) {
        TextView movieTitle = detailsView.findViewById(R.id.txt_movie_title);
        movieTitle.setText(movie.getOriginalTitle());
        TextView movieYear = detailsView.findViewById(R.id.txt_movie_year);
        movieYear.setText(movie.getReleaseDate());
        TextView movieOverview = detailsView.findViewById(R.id.txt_movie_overview);
        movieOverview.setText(movie.getOverview());
        ImageView moviePoster = detailsView.findViewById(R.id.image_movie_poster);
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w342/" + movie.getPosterPath())
                .into(moviePoster);
        ImageView background = detailsView.findViewById(R.id.image_background);
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w780/" + movie.getPosterPath())
                .into(background);
        TextView movieTime = detailsView.findViewById(R.id.txt_movie_time);
        movieTime.setText(String.valueOf(movie.getRuntime()));
        TextView movieRank = detailsView.findViewById(R.id.txt_movie_rank);
        movieRank.setText(movie.getRank() + "/10");

        getTrailers();
        getReviews();

    }

    private void getTrailers() {
        //Log.i("MOVIE ID:", String.valueOf(movie.getMovieId()));
        //Log.i("MOVIE NAME:", movie.getOriginalTitle());

        ApiService.getInstance()
                .getMovieTrailers(movie.getMovieId(), BuildConfig.API_KEY)
                .enqueue(new Callback<TrailerResult>() {
                    @Override
                    public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                        if (response.isSuccessful()) {
                            trailerAdapter.setTrailers(
                                    Mapper.fromResponseToMainTrailer(response.body().getTrailerResults())
                            );
                        } else {
                            Toast.makeText(getActivity(), "Error showing Trailers1", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TrailerResult> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error showing Trailers2", Toast.LENGTH_SHORT).show();
                        if (t instanceof IOException) {
                            errorType[0] = "Timeout";
                            errorDesc[0] = String.valueOf(t.getCause());
                            Log.i(errorType[0], errorDesc[0]);
                        } else if (t instanceof IllegalStateException) {
                            errorType[0] = "ConversionError";
                            errorDesc[0] = String.valueOf(t.getCause());
                            Log.i(errorType[0], errorDesc[0]);
                        } else {
                            errorType[0] = "Other Error";
                            errorDesc[0] = String.valueOf(t.getLocalizedMessage());
                            Log.i(errorType[0], errorDesc[0]);
                        }
                    }
                });

    }

    private void configTrailerAdapter() {
        recyclerTrailer = detailsView.findViewById(R.id.rview_trailers);
        trailerAdapter = new TrailerAdapter(this);
        RecyclerView.LayoutManager trailerLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerTrailer.setLayoutManager(trailerLayoutManager);
        recyclerTrailer.setAdapter(trailerAdapter);
    }

    private void getReviews() {
        Log.i("MOVIE ID:", String.valueOf(movie.getMovieId()));
        Log.i("MOVIE NAME:", movie.getOriginalTitle());

        ApiService.getInstance()
                .getMovieReviews(movie.getMovieId(), BuildConfig.API_KEY)
                .enqueue(new Callback<ReviewResult>() {
                    @Override
                    public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                        if (response.isSuccessful()) {
                            reviewAdapter.setReviews(
                                    Mapper.fromResponseToMainReview(response.body().getReviewResults())
                            );
                        } else {
                            Toast.makeText(getActivity(), "Error showing reviews1", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewResult> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error showing reviews2", Toast.LENGTH_SHORT).show();
                        if (t instanceof IOException) {
                            errorType[0] = "Timeout";
                            errorDesc[0] = String.valueOf(t.getCause());
                            Log.i(errorType[0], errorDesc[0]);
                        } else if (t instanceof IllegalStateException) {
                            errorType[0] = "ConversionError";
                            errorDesc[0] = String.valueOf(t.getCause());
                            Log.i(errorType[0], errorDesc[0]);
                        } else {
                            errorType[0] = "Other Error";
                            errorDesc[0] = String.valueOf(t.getLocalizedMessage());
                            Log.i(errorType[0], errorDesc[0]);
                        }
                    }
                });

    }


    private void configReviewAdapter() {
        recyclerReview = detailsView.findViewById(R.id.rview_reviews);
        reviewAdapter = new ReviewAdapter(this);
        RecyclerView.LayoutManager reviewLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerReview.setLayoutManager(reviewLayoutManager);
        recyclerReview.setAdapter(reviewAdapter);
    }

    @Override
    public void onListItemClick(Trailer trailer) {
        TrailerYouFragment trailerYouFragment = TrailerYouFragment.newInstance(trailer);
        Utils.setFragment(getFragmentManager(), trailerYouFragment);

        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + trailer.getName() + " clicked.";
        mToast = Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_LONG);
        mToast.show();
    }

    @Override
    public void onListItemClick(Review review) {
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + review.getAuthor() + " clicked.";
        mToast = Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_LONG);

        mToast.show();
    }
}