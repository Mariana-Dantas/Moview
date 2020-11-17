package com.example.android.moview.network.response;

import com.squareup.moshi.Json;

public class TrailerResponse {

    @Json(name = "id")
    private final String trailerID;
    @Json(name = "iso_639_1")
    private final String iso_639_1;
    @Json(name = "iso_3166_1")
    private final String iso_3166_1;
    @Json(name = "key")
    private final String key;
    @Json(name = "name")
    private final String name;
    @Json(name = "site")
    private final String site;
    @Json(name = "size")
    private final int size;
    @Json(name = "type")
    private final String type;

    public TrailerResponse(String trailerID, String iso_639_1, String iso_3166_1, String key, String name, String site, int size, String type) {
        this.trailerID = trailerID;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public String getTrailerID() {
        return trailerID;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
