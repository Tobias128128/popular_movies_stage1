package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android.popularmoviesstage1.network_utilities.DatabaseConnection;
import com.example.android.popularmoviesstage1.network_utilities.FetchMovieData;

import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, GridViewAdapter.GridItemClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();
    private Spinner spinner_sorting;

    private RecyclerView recyclerView;
    private GridViewAdapter gridViewAdapter;

    private final static String PARCELABLE_ARRAY_MOVIES = "Movies";
    private final static String PARCELABLE_INT_SPINNER_ITEM_INDEX = "selected spinner item index";
    private final static String PARCELABLE_BOOLEAN_SAVED_INSTANCE = "has saved instance";
    final static String PARCELABLE_MOVIE_SELECTED_ITEM = "selected movie";

    private boolean saved_instance;
    private boolean first_start = true;
    private int last_spinner_item_index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_movie_poster);

        int numberOfColumns = getGridLayoutColumns();
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        gridViewAdapter = new GridViewAdapter(this);
        recyclerView.setAdapter(gridViewAdapter);

        if (savedInstanceState != null) {
            saved_instance = savedInstanceState.getBoolean(PARCELABLE_BOOLEAN_SAVED_INSTANCE);
            last_spinner_item_index = savedInstanceState.getInt(PARCELABLE_INT_SPINNER_ITEM_INDEX);
            Parcelable[] parcelable_array_movies = savedInstanceState.getParcelableArray(PARCELABLE_ARRAY_MOVIES);


            if (parcelable_array_movies != null) {
                Movie[] movies;
                movies = Arrays.copyOf(parcelable_array_movies, parcelable_array_movies.length, Movie[].class);
                gridViewAdapter.setMovieData(movies);
            }


        } else {
            saved_instance = false;
        }


    }

    private int getGridLayoutColumns() {
        int numberOfColumns;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            numberOfColumns = 2;
        } else {
            numberOfColumns = 4;
        }
        return numberOfColumns;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray(PARCELABLE_ARRAY_MOVIES, gridViewAdapter.getMovieData());
        int selected_spinner_item_index = spinner_sorting.getSelectedItemPosition();
        outState.putInt(PARCELABLE_INT_SPINNER_ITEM_INDEX, selected_spinner_item_index);
        outState.putBoolean(PARCELABLE_BOOLEAN_SAVED_INSTANCE, true);
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.spinner_sorting);
        spinner_sorting = (Spinner) menuItem.getActionView();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinner_items));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_sorting.setOnItemSelectedListener(this);
        spinner_sorting.setAdapter(arrayAdapter);
        if (saved_instance) {
            spinner_sorting.setSelection(last_spinner_item_index);
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (!first_start || !saved_instance) {
            String selectedItem = adapterView.getItemAtPosition(i).toString();
            URL url;
            FetchMovieData fetchMovieData = new FetchMovieData(gridViewAdapter, getWindow().getDecorView().getRootView());

            if (selectedItem.equals(getString(R.string.popular))) {
                url = DatabaseConnection.buildUrl(getResources().getResourceEntryName(R.string.popular));
                fetchMovieData.execute(url);
            } else {
                if (selectedItem.equals(getString(R.string.top_rated))) {
                    url = DatabaseConnection.buildUrl(getResources().getResourceEntryName(R.string.top_rated));
                    fetchMovieData.execute(url);
                }
            }
        }
        first_start = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClickListener(int clickedItemIndex) {

        Movie selectedMovie = gridViewAdapter.getClickedMovie(clickedItemIndex);
        Class destinationClass = DetailActivity.class;
        Intent intent = new Intent(this, destinationClass);
        intent.putExtra(PARCELABLE_MOVIE_SELECTED_ITEM, selectedMovie);
        startActivity(intent);
    }
}
