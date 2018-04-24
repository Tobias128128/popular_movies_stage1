package com.example.android.popularmoviesstage1.network_utilities;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.popularmoviesstage1.GridViewAdapter;
import com.example.android.popularmoviesstage1.Movie;
import com.example.android.popularmoviesstage1.R;

import java.io.IOException;
import java.net.URL;

public class FetchMovieData extends AsyncTask<URL, Void, Movie[]> {

    private final GridViewAdapter GRIDVIEWADAPTER;
    private final ProgressBar PB_LOADING;
    private final static String TAG = FetchMovieData.class.getSimpleName();

    public FetchMovieData(GridViewAdapter GRIDVIEWADAPTER, View view) {
        this.GRIDVIEWADAPTER = GRIDVIEWADAPTER;
        PB_LOADING = view.findViewById(R.id.pb_loading_indicator);
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        PB_LOADING.setVisibility(View.VISIBLE);
    }

    @Override
    protected Movie[] doInBackground(URL... urls) {

        if (urls.length == 0) {
            return null;
        }

        URL url = urls[0];

        try {
            String jsonMovieDBResponse = DatabaseConnection.getResponseFromHttp(url);
            return ParseMovieData.parseMovieData(jsonMovieDBResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        PB_LOADING.setVisibility(View.INVISIBLE);
        if (movies != null) {
            GRIDVIEWADAPTER.setMovieData(movies);
        }
    }
}
