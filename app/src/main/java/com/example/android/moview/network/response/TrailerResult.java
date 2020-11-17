package com.example.android.moview.network.response;

import com.squareup.moshi.Json;

import java.util.List;

public class TrailerResult {

    @Json(name = "results")
    private final List<TrailerResponse> trailerResults;

    public TrailerResult(List<TrailerResponse> trailerResults) {
        this.trailerResults = trailerResults;
    }

    public List<TrailerResponse> getTrailerResults() {
        return trailerResults;
    }

}
