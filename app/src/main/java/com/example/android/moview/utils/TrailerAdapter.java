package com.example.android.moview.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moview.R;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    static private TrailerAdapter.ListItemClickListener mOnClickListener;
    private List<Trailer> trailers;

    public TrailerAdapter(TrailerAdapter.ListItemClickListener listener) {
        mOnClickListener = listener;
        trailers = new ArrayList<>();
    }

    @NonNull
    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        return new TrailerAdapter.TrailerViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return (trailers != null && trailers.size() > 0) ? trailers.size() : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.TrailerViewHolder holder, int position) {
        holder.bind(trailers.get(position));
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(Trailer trailer);
    }

    static class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView trailerName;
        private Trailer trailer;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.trailerName);
            itemView.setOnClickListener(this);
        }

        public void bind(Trailer trailer) {
            this.trailer = trailer;
            trailerName.setText(trailer.getName());
        }

        @Override
        public void onClick(View view) {
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(trailer);
            }
        }
    }

}
