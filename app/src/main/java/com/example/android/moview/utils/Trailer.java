package com.example.android.moview.utils;

import java.io.Serializable;

public class Trailer implements Serializable {

    private final String trailerId;
    private final String iso_639_1;
    private final String iso_3166_1;
    private final String key;
    private final String name;
    private final String site;
    private final int size;
    private final String type;

    public Trailer(String trailerId, String iso_639_1, String iso_3166_1, String key, String name, String site, int size, String type) {
        this.trailerId = trailerId;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public String getTrailerId() {
        return trailerId;
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
