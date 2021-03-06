package com.example.android.moview.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.android.moview.network.response.MovieResult;
import com.example.android.moview.utils.Mapper;
import com.example.android.moview.utils.Movie;
import com.example.android.moview.utils.MovieAdapter;
import com.example.android.moview.utils.Utils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.moview.R.string.error_best_ranked_movies;

public class MovieListFragment extends Fragment implements MovieAdapter.ListItemClickListener {

    private RecyclerView recyclerMovie;
    private MovieAdapter movieAdapter;
    private View rootView;
    private Toast mToast;
    private FavoriteDbHelper favoriteDbHelper;
    public static final String ARG_ITEM_POSITION = "1";
    private int itemPosition = 1;
    private final String[] errorType = {""};
    private final String[] errorDesc = {""};

    public static MovieListFragment newInstance(int position) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.movie_list_fragment, container, false);
        configAdapter();
        favoriteDbHelper = new FavoriteDbHelper(getActivity());
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        Bundle bundle = this.getArguments();

        super.onCreate(savedInstanceState);

        if (bundle != null) {
            itemPosition = (int) bundle.getSerializable(ARG_ITEM_POSITION);
            if (itemPosition == 1) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.rating);
                getBestRankedMovies();
            } else if (itemPosition == 2) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.popularity);
                getPopularMovies();
            } else if (itemPosition == 3) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.favorits);
                getFavMovies();
            }
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // Configs List Adapter
    private void configAdapter() {
        recyclerMovie = rootView.findViewById(R.id.recycler_movie);
        movieAdapter = new MovieAdapter(this);

        RecyclerView.LayoutManager movieLayoutManager;
        //checkOrientation
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            movieLayoutManager = new GridLayoutManager(getContext(), 4);
            // In landscape
        } else {
            movieLayoutManager = new GridLayoutManager(getContext(), 2);
            // In portrait
        }

        //RecyclerView.LayoutManager movieLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerMovie.setLayoutManager(movieLayoutManager);
        recyclerMovie.setAdapter(movieAdapter);
    }

    // gets most popular Movies from API
    public void getPopularMovies() {
        ApiService.getInstance()
                .getPopularMovies(BuildConfig.API_KEY)
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                        if (response.isSuccessful()) {
                            movieAdapter.setMovies(
                                    Mapper.fromResponseToMainMovie(response.body().getMovieResults())
                            );
                        } else {
                            Toast.makeText(getActivity(), R.string.error_pop_movies, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                        showError(t);
                    }
                });

    }

    // gets Best ranked movies from API
    public void getBestRankedMovies() {
        ApiService.getInstance()
                .getTopRatedMovies(BuildConfig.API_KEY)
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                        if (response.isSuccessful()) {
                            movieAdapter.setMovies(
                                    Mapper.fromResponseToMainMovie(response.body().getMovieResults())
                            );
                        } else {
                            Toast.makeText(getActivity(), error_best_ranked_movies, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), R.string.error_showind_movie_list, Toast.LENGTH_SHORT).show();
                        showError(t);
                    }
                });
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

    // Inflates menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // Gets Favorit Movies from the database
    public void getFavMovies() {
        movieAdapter.setMovies(
                favoriteDbHelper.getAllFavorite()
        );
    }

    // Click responce when a movie is clicked
    @Override
    public void onListItemClick(Movie movie) {
        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(movie);
        Utils.setFragment(getFragmentManager(), movieDetailsFragment);
    }

}
