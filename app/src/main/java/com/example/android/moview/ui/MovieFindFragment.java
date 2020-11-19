package com.example.android.moview.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFindFragment extends Fragment implements MovieAdapter.ListItemClickListener {

    private View rootView;
    private MovieAdapter movieSearchAdapter;
    private RecyclerView recyclerMovieSearch;
    private String query;

    public static MovieFindFragment newInstance() {
        Bundle args = new Bundle();
        MovieFindFragment fragment = new MovieFindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.movie_find_fragment, container, false);

        EditText queryText = rootView.findViewById(R.id.editText_movie_name);

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
                            Log.i("MOVIE", response.body().getMovieResults().toString());
                            movieSearchAdapter.setMovies(
                                    Mapper.fromResponseToMainMovie(response.body().getMovieResults())
                            );
                        } else {
                            Toast.makeText(getActivity(), R.string.error_showing_movies, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), R.string.error_showing_movies, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void configAdapter() {
        recyclerMovieSearch = rootView.findViewById(R.id.movie_find_recycler);
        movieSearchAdapter = new MovieAdapter(this);
        RecyclerView.LayoutManager movieLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerMovieSearch.setLayoutManager(movieLayoutManager);
        recyclerMovieSearch.setAdapter(movieSearchAdapter);
    }

    @Override
    public void onListItemClick(Movie movie) {
        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(movie);
        Utils.setFragment(getFragmentManager(), movieDetailsFragment);
    }
}
