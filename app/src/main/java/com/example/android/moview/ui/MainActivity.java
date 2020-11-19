package com.example.android.moview.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.android.moview.R;
import com.example.android.moview.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieListFragment movieListFragment = MovieListFragment.newInstance();
        Utils.setFragment(getSupportFragmentManager(), movieListFragment);
    }


}