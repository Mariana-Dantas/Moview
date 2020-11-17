package com.example.android.moview.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moview.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    static private ListItemClickListener mOnClickListener;
    private List<Movie> movies;

    public MovieAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;
        movies = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return (movies != null && movies.size() > 0) ? movies.size() : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(Movie movie);
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textMovieTitle;
        private final ImageView imageMoviePoster;
        private Movie movie;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textMovieTitle = itemView.findViewById(R.id.text_Title_Movie);
            imageMoviePoster = itemView.findViewById(R.id.image_movie_poster);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            this.movie = movie;
            textMovieTitle.setText(movie.getOriginalTitle());
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w342/" + movie.getPosterPath())
                    .into(imageMoviePoster);
        }

        @Override
        public void onClick(View view) {
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(movie);
            }
        }
    }

}
