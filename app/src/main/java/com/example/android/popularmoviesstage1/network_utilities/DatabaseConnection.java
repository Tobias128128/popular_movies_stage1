package com.example.android.popularmoviesstage1.network_utilities;

import android.net.Uri;

import com.example.android.popularmoviesstage1.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class DatabaseConnection {

    private final static String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie";

    private final static String PARAM_LANGUAGE = "language";
    private final static String PARAM_API_KEY = "api_key";

    private final static String LANGUAGE_EN_US = "en_US";
    private final static String API_KEY = BuildConfig.API_KEY;

    public static URL buildUrl(String sortBy) {
        Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendPath(sortBy)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE_EN_US)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttp(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
