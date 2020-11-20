package com.example.android.moview.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moview.BuildConfig;
import com.example.android.moview.R;
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

public class MovieFindFragment extends Fragment implements MovieAdapter.ListItemClickListener {

    private View rootView;
    private MovieAdapter movieSearchAdapter;
    private RecyclerView recyclerMovieSearch;
    private String query;
    private final String[] errorType = {""};
    private final String[] errorDesc = {""};

    public static MovieFindFragment newInstance() {
        Bundle args = new Bundle();
        MovieFindFragment fragment = new MovieFindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.movie_find_fragment, container, false);
        super.onCreate(savedInstanceState);

        EditText queryText = rootView.findViewById(R.id.editText_movie_name);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.find_movie);

        ImageButton find = rootView.findViewById(R.id.button_send);
        find.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                query = queryText.getText().toString();
                findMovie(query);
            }
        });

        return rootView;
    }

    public void findMovie(String query) {
        configAdapter();
        ApiService.getInstance()
                .getMovieSearch(query, BuildConfig.API_KEY)
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getMovieResults().size() > 0) {
                                movieSearchAdapter.setMovies(
                                        Mapper.fromResponseToMainMovie(response.body().getMovieResults())
                                );
                            } else {
                                Toast.makeText(getActivity(), R.string.no_movies_were_found, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), R.string.error_showing_movies, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), R.string.error_showing_movies, Toast.LENGTH_SHORT).show();
                        showError(t);
                    }
                });
    }

    private void configAdapter() {
        recyclerMovieSearch = rootView.findViewById(R.id.movie_find_recycler);
        movieSearchAdapter = new MovieAdapter(this);

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
        recyclerMovieSearch.setLayoutManager(movieLayoutManager);
        recyclerMovieSearch.setAdapter(movieSearchAdapter);
    }

    @Override
    public void onListItemClick(Movie movie) {
        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(movie);
        Utils.setFragment(getFragmentManager(), movieDetailsFragment);
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

}
