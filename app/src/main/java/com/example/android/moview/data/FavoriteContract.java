package com.example.android.moview.data;

import android.provider.BaseColumns;

public class FavoriteContract {

    public static final class FavoriteEntry implements BaseColumns {

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_ORIGINAL_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_OVERVIEW = "overview";

    }
}
