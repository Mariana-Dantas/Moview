package com.example.android.moview.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.example.android.moview.R;
import com.example.android.moview.utils.Trailer;

public class TrailerYouFragment extends Fragment {

    public static final String ARG_TRAILER = "ARG_TRAILER";
    WebView youtubeView;
    private Trailer trailer;
    private View detailsView;

    public static TrailerYouFragment newInstance(Trailer trailer) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRAILER, trailer);
        TrailerYouFragment fragment = new TrailerYouFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        detailsView = inflater.inflate(R.layout.trailer_youtube_fragment, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            trailer = (Trailer) bundle.getSerializable(ARG_TRAILER);
        }

        if (trailer.getSite() == "YouTube") {
            String path = "https://youtu.be/" + trailer.getKey();

            youtubeView = detailsView.findViewById(R.id.youtube_vid);
            youtubeView.getSettings().setJavaScriptEnabled(true);
            youtubeView.setWebChromeClient(new WebChromeClient());
            youtubeView.loadUrl(path);
        }

        return detailsView;
    }

}
