package com.example.android.moview.network.response;

import com.squareup.moshi.Json;

import java.util.List;

public class ReviewResult {

    @Json(name = "results")
    private final List<ReviewResponse> reviewResults;

    public ReviewResult(List<ReviewResponse> reviewResults) {
        this.reviewResults = reviewResults;
    }

    public List<ReviewResponse> getReviewResults() {
        return reviewResults;
    }

}

