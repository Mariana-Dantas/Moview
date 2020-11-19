package com.example.android.moview.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
        //getPopularMovies();
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            itemPosition = (int) bundle.getSerializable(ARG_ITEM_POSITION);
            if (itemPosition == 1) {
                getBestRankedMovies();
            } else if (itemPosition == 2) {
                getPopularMovies();
            } else if (itemPosition == 3) {
                getFavMovies();
            }
        }
        //setHasOptionsMenu(true);
        return rootView;
    }


    // Configs List Adapter
    private void configAdapter() {
        recyclerMovie = rootView.findViewById(R.id.recycler_movie);
        movieAdapter = new MovieAdapter(this);
        RecyclerView.LayoutManager movieLayoutManager = new GridLayoutManager(getActivity(), 2);
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
                        showError();
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
                        showError();
                    }
                });
    }

    private void showError() {
        Toast.makeText(getActivity(), R.string.error_showind_movie_list, Toast.LENGTH_SHORT).show();
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
