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

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    static private ReviewAdapter.ListItemClickListener mOnClickListener;
    private List<Review> reviews;

    public ReviewAdapter(ReviewAdapter.ListItemClickListener listener) {
        mOnClickListener = listener;
        reviews = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return (reviews != null && reviews.size() > 0) ? reviews.size() : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        holder.bind(reviews.get(position));
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(Review review);
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView reviewAuthor;
        private final TextView reviewContent;
        private Review review;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewAuthor = itemView.findViewById(R.id.review_author);
            reviewContent = itemView.findViewById(R.id.review_content);

            itemView.setOnClickListener(this);
        }

        public void bind(Review review) {
            this.review = review;
            reviewAuthor.setText(review.getAuthor());
            reviewContent.setText(review.getContent());
        }

        @Override
        public void onClick(View view) {
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(review);
            }
        }
    }

}

