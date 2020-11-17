package com.example.android.moview.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moview.R;
import com.example.android.moview.network.ApiService;
import com.example.android.moview.network.response.MovieResult;
import com.example.android.moview.utils.Mapper;
import com.example.android.moview.utils.Movie;
import com.example.android.moview.utils.MovieAdapter;
import com.example.android.moview.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListFragment extends Fragment implements MovieAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEMS = 100;
    private RecyclerView recyclerMovie;
    private MovieAdapter movieAdapter;
    private View rootView;
    private Toast mToast;

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.movie_list_fragment, container, false);
        configAdapter();
        getPopularMovies();
        setHasOptionsMenu(true);
        return rootView;
    }

    private void configAdapter() {
        recyclerMovie = rootView.findViewById(R.id.recycler_movie);
        movieAdapter = new MovieAdapter(this);
        RecyclerView.LayoutManager movieLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerMovie.setLayoutManager(movieLayoutManager);
        recyclerMovie.setAdapter(movieAdapter);
    }

    private void getPopularMovies() {
        ApiService.getInstance()
                .getPopularMovies(getString(R.string.tmdb_api_key))
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        if (response.isSuccessful()) {
                            movieAdapter.setMovies(
                                    Mapper.fromResponseToMainMovie(response.body().getMovieResults())
                            );
                        } else {
                            Toast.makeText(getActivity(), "Error showing Popular Movies", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {
                        showError();
                    }
                });

    }

    private void getBestRankedMovies() {
        ApiService.getInstance()
                .getTopRatedMovies(getString(R.string.tmdb_api_key))
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        if (response.isSuccessful()) {
                            movieAdapter.setMovies(
                                    Mapper.fromResponseToMainMovie(response.body().getMovieResults())
                            );
                        } else {
                            Toast.makeText(getActivity(), "Error showing Best Ranked Movies", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {
                        showError();
                    }
                });
    }

    private void showError() {
        Toast.makeText(getActivity(), "Error showing Movie List", Toast.LENGTH_SHORT).show();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_movies_rating:
                getBestRankedMovies();
                return true;
            case R.id.action_movies_popularity:
                getPopularMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(Movie movie) {

        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(movie);
        Utils.setFragment(getFragmentManager(), movieDetailsFragment);

        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + movie.getOriginalTitle() + " clicked.";
        mToast = Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_LONG);

        mToast.show();
    }

}
