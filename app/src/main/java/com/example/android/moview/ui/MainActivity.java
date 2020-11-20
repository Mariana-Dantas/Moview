package com.example.android.moview.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.android.moview.R;
import com.example.android.moview.utils.Utils;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    public MovieListFragment movieListFragment;
    public MovieFindFragment movieFindFragment;
    public int itemPosition = 1;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            movieListFragment = MovieListFragment.newInstance(itemPosition);
            Utils.setFragment(getSupportFragmentManager(), movieListFragment);
        }

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationViewListener();

    }

    // Click responce when item from menu is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_movies_rating:
                itemPosition = 1;
                movieListFragment = MovieListFragment.newInstance(itemPosition);
                Utils.setFragment(getSupportFragmentManager(), movieListFragment);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.action_movies_popularity:
                itemPosition = 2;
                movieListFragment = MovieListFragment.newInstance(itemPosition);
                Utils.setFragment(getSupportFragmentManager(), movieListFragment);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.action_movies_fav:

                itemPosition = 3;
                movieListFragment = MovieListFragment.newInstance(itemPosition);
                Utils.setFragment(getSupportFragmentManager(), movieListFragment);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.find_menu:

                itemPosition = 4;
                movieFindFragment = MovieFindFragment.newInstance();
                Utils.setFragment(getSupportFragmentManager(), movieFindFragment);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.nav_bar);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}