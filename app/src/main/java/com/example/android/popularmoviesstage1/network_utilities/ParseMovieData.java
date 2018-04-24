package com.example.android.popularmoviesstage1.network_utilities;

import com.example.android.popularmoviesstage1.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ParseMovieData {

    private final static String TAG = ParseMovieData.class.getSimpleName();

    private final static String JSON_RESULTS = "results";
    private final static String JSON_MOVIE_POSTER = "poster_path";
    private final static String JSON_TITLE = "title";
    private final static String JSON_RELEASE_DATE = "release_date";
    private final static String JSON_PLOT_SYNOPSIS = "overview";
    private final static String JSON_VOTE_AVERAGE = "vote_average";

    public static Movie[] parseMovieData(String json) {

        Movie[] movieList;
        JSONObject jsonObject;
        JSONArray movieResults;

        try {
            jsonObject = new JSONObject(json);
            movieResults = jsonObject.getJSONArray(JSON_RESULTS);
            movieList = new Movie[movieResults.length()];

            JSONObject jsonObjectResult;
            String movie_poster;
            String title;
            String release_date;
            String plot_synopsis;
            double vote_average;
            for (int i = 0; i < movieResults.length(); i++) {
                jsonObjectResult = movieResults.getJSONObject(i);
                movie_poster = jsonObjectResult.getString(JSON_MOVIE_POSTER);
                title = jsonObjectResult.getString(JSON_TITLE);
                release_date = jsonObjectResult.getString(JSON_RELEASE_DATE);
                plot_synopsis = jsonObjectResult.getString(JSON_PLOT_SYNOPSIS);
                vote_average = jsonObjectResult.getInt(JSON_VOTE_AVERAGE);
                movieList[i] = new Movie(movie_poster, title, release_date, plot_synopsis, vote_average);
            }

            return movieList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
