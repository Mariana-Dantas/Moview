package com.example.android.moview.network.response;

import com.squareup.moshi.Json;

public class ReviewResponse {

    @Json(name = "id")
    private final String reviewId;
    @Json(name = "author")
    private final String author;
    @Json(name = "content")
    private final String content;
    @Json(name = "url")
    private final String url;

    public ReviewResponse(String reviewId, String author, String content, String url) {
        this.reviewId = reviewId;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
